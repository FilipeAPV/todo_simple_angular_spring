package com.mytodo.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Query("SELECT COUNT(e) FROM UserModel e WHERE e.email = ?1")
    long checkIfEmailAlreadyExistsInDb(String email);

    UserModel findByEmail(String username);
}
