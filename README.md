# 과외 수업 매칭 서비스

## 소개

이 프로젝트는 **선생님**과 **학생** 간의 **과외 수업** 매칭을 위한 서비스입니다. 학습자와 교육자가 웹을 통해 연결되고, 다양한 주제와 과목에서 과외 수업을 찾고 제공할 수 있습니다.

### 도메인

- 과외 수업 매칭 서비스

### 하위 도메인

이 서비스는 다음과 같은 주요 애그리거트를 포함합니다:

- **유저**
  - **학생**: 서비스를 이용하는 학습자.
  - **선생님**: 과외 수업을 제공하는 교육자.
- **과외 수업**: 공개 모집되는 수업.
- **참가 신청**: 학생이 수업에 참여를 신청하는 프로세스.
  - **결제**: 수업에 참가하기 위한 결제 프로세스.
- **매칭 된 과외 수업**: 학생과 선생님 간의 매칭된 수업.

### 요구사항

- 선생님이든 학생이든 **회원가입**을 진행해야 과외를 신청하고 받을 수 있다.
- 더 이상 해당 사이트를 이용 안 할 이용자들은 **회원을 탈퇴**할 수 있어야 한다.
- **학생은** 과외를 신청하려면 **로그인**을 진행해야 한다.
- 모든 페이지에 진입할 때마다 **로그인 여부**를 체크한다.
- **선생님**과 **학생**이 서로 웹 페이지를 통해 **과외 수업**이 **매칭**될 수 있다.
- **선생님**은 진행하고 싶은 **과외 수업**을 모집할 수 있다.
- **학생**은 필요한 **과외 수업**에 대해 **참가 신청**을 할 수 있다.
- **과외 수업**은 **선착순**으로 진행 된다.
- **매칭 이후 3일 이내** **결제**를 진행해야 한다.
- 학생은 마음에 들지 않는 수업을 **결제 취소 할 수 있다.**
    - **단, 과외가 시작되지 전에만 취소가 가능하다.**
- 결제가 됐는지 하루에 한번씩 이메일을 보낸다.

요구사항들을 애그리거트 단위로 다시 재정의 해보았을 때 다음과 같습니다.

### 애그리거트

- **유저**
    - 선생님이든 학생이든 **회원가입**을 진행해야 과외를 신청하고 받을 수 있다.
    - 더 이상 해당 사이트를 이용 안 할 이용자들은 **회원을 탈퇴**할 수 있어야 한다.
    - **학생은** 과외를 신청하려면 **로그인**을 진행해야 한다.
    - 모든 페이지에 진입할 때마다 **로그인 여부**를 체크한다.
- **과외 과목**
    - **선생님**은 진행하고 싶은 **과외 수업**을 모집할 수 있다.
- **매칭 된 과외 수업**
    - **선생님**과 **학생**이 서로 웹 페이지를 통해 **과외 수업**이 **매칭**될 수 있다.
    - **학생**은 필요한 **과외 수업**에 대해 **참가 신청**을 할 수 있다.
    - **과외 수업**은 **선착순**으로 진행 된다.
- **결제**
    - **매칭 이후 3일 이내** **결제**를 진행해야 한다.
    - 학생은 마음에 들지 않는 수업을 **결제 취소 할 수 있다.**
    - 결제가 됐는지 하루에 한번씩 이메일을 보낸다.

### 바운더리 컨텍스트

- think-up-edu-hub 프로젝트

### 설계 모듈

![module.jpg]([https://prod-files-secure.s3.us-west-2.amazonaws.com/c4208ea1-f20c-48bd-b05a-8f485cb16b9b/38528b62-6250-488c-ba49-fef84a17ac71/module.jpg](https://www.notion.so/cf7726421e45480182d4f6b97dd0181d?pvs=4#c2acfcc8b80d423ab6c210fcdb85a9d7)https://www.notion.so/cf7726421e45480182d4f6b97dd0181d?pvs=4#c2acfcc8b80d423ab6c210fcdb85a9d7)
