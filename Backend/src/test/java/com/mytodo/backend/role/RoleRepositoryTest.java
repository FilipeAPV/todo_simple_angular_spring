package com.mytodo.backend.role;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    RoleModel admin;
    RoleModel user;

    @BeforeEach()
    void setup() {
        admin = new RoleModel("admin");
        user = new RoleModel("user");
    }

    @Test
    void save() {
        roleRepository.save(admin);
        roleRepository.save(user);

        long totalNumberOfRoles = roleRepository.count();
        Assertions.assertEquals(2, totalNumberOfRoles);
    }
}
