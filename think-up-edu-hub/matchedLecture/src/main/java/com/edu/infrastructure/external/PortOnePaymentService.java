package com.edu.infrastructure.external;

import com.edu.component.CommonComponent;
import com.edu.domain.payment.dto.AccessTokenResponse;
import com.edu.domain.payment.dto.PaymentResponse;
import com.edu.domain.payment.service.ExternalPaymentService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PortOnePaymentService implements ExternalPaymentService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Value("${portOne.base-url}")
    private String baseUrl;
    @Value("${portOne.rest-api-key}")
    private String restApiKey;
    @Value("${portOne.rest-api-secret}")
    private String restApiSecret;
    private final RedisTemplate<String,String> redisTemplate;
    private final WebClient webClient;
    private final String accessTokenResponseUrl = "/users/getToken";
    private final String verificationUrl = "/payments/{imp_uid}";

    @Override
    public AccessTokenResponse getAccessToken() {

        Gson gson = new Gson();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("imp_key", restApiKey);
        params.add("imp_secret", restApiSecret);

        HttpEntity<?> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(baseUrl + accessTokenResponseUrl, request, String.class);

        AccessTokenResponse portOneAccessToken = new AccessTokenResponse();

        if(response.getStatusCode().value() == 200){
            savePortOneAccessToken(response.getBody());
            portOneAccessToken = gson.fromJson(response.getBody(),AccessTokenResponse.class);
        }

        if(response.getStatusCode().value() == 401){
            log.error("요청 key: {}, 요청 secret key: {}",restApiKey,restApiSecret);
            throw new IllegalStateException("imp_key, imp_secret 인증에 실패했습니다.");
        }

        if(response.getStatusCode().is4xxClientError()){
            log.error("요청 key: {}, 요청 secret key: {} error_code:{}"
                    ,restApiKey,restApiSecret,response.getStatusCode().value());
            throw new IllegalStateException("요청을 보낼 때 알 수 없는 에러가 발생하였습니다.");
        }

        if(response.getStatusCode().is5xxServerError()){
            log.error("요청 key: {}, 요청 secret key: {}, error_code:{}"
                    ,restApiKey,restApiSecret,response.getStatusCode().value());
            throw new IllegalStateException("port one 서버에서 알 수 없는 에러가 발생하였습니다.");
        }

        return portOneAccessToken;
    }

    private void savePortOneAccessToken(String responseData) {
        ValueOperations<String, String> valueOptions = redisTemplate.opsForValue();
        valueOptions.set("portOne:accessToken",responseData);
    }

    @Override
    public PaymentResponse sendVerifyPaymentRequest(String impUid) {

        AccessTokenResponse portOneAccessToken = checkIsAccessTokenExpired();

        String request = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(verificationUrl)
                        .build(impUid))
                .header("Authorization", "Bearer " + portOneAccessToken.getResponse().getAccessToken())
                .exchangeToMono(response -> {

                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(String.class);
                    }

                    if (response.statusCode().value() == 401) {
                        log.info("에러 반환 코드: {}", response.statusCode().value());
                        throw new IllegalStateException("Token이 전달되지 않았거나 유효하지 않습니다. 토큰 재발급 필요");
                    }

                    if (response.statusCode().value() == 404) {
                        log.info("에러 반환 코드: {}", response.statusCode().value());
                        throw new IllegalStateException("유효하지 않은 imp_uid입니다. 다시 확인하세요. imp_uid: " + impUid);
                    } else {
                        log.error("예상치 못한 에러 발생, 에러 반환 코드: {}",response.statusCode().value());
                        return response.createException()
                                .flatMap(Mono::error);
                    }
                })
                .block();

        Gson gson = new Gson();
        PaymentResponse paymentResponse = gson.fromJson(request, PaymentResponse.class);
        return paymentResponse;
    }

    private String getPortOneAccessToken() {
        ValueOperations<String, String> valueOptions = redisTemplate.opsForValue();
        return valueOptions.get("portOne:accessToken");
    }

    private AccessTokenResponse checkIsAccessTokenExpired() {

        Gson gson = new Gson();
        LocalDateTime now = LocalDateTime.now();

        String responseData = getPortOneAccessToken();

        AccessTokenResponse portOneAccessToken = gson.fromJson(responseData,AccessTokenResponse.class);
        long expiredAtByTimestamp = portOneAccessToken.getResponse().getExpiredAt();
        LocalDateTime expiredAt = CommonComponent.convertTimestampToLocalDateTime(expiredAtByTimestamp);

        if(now.isAfter(expiredAt)){
            AccessTokenResponse newAccessToken = getAccessToken();
            return newAccessToken;
        } else{
            return portOneAccessToken;
        }
    }
}
