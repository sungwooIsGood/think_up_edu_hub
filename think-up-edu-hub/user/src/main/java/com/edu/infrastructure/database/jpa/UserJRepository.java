package com.edu.infrastructure.database.jpa;

import com.edu.domain.entity.User;
import com.edu.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJRepository extends JpaRepository<User,Long>, UserRepository {

}
