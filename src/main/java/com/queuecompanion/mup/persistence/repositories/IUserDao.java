package com.queuecompanion.mup.persistence.repositories;

import com.queuecompanion.mup.persistence.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository<User, String> {
    User findByUsername(String username);

    User findByEmail(String email);
}
