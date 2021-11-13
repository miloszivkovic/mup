package com.queuecompanion.mup.persistence.repositories;

import com.queuecompanion.mup.persistence.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO: add index to EmailAddress
public interface IUserDao extends JpaRepository<User, String> {
    User findByUsername(String username);

    User findByEmailAddress(String emailAddress);

    User findByUsernameOrEmailAddress(String username, String emailAddress);
}
