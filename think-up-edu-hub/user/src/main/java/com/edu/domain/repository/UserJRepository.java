package com.edu.domain.repository;

import com.edu.domain.entity.User;
import com.edu.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJRepository extends JpaRepository<User,Long>, UserRepository {

}
