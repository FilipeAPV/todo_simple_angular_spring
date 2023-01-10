package com.mytodo.backend.user;

import com.mytodo.backend.role.RoleModel;
import com.mytodo.backend.role.RoleRepository;
import jakarta.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Validator validator;

    private final PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       Validator validator,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean saveUser(UserModel user) {

        RoleModel roleByDefault = roleRepository.findById(2L).get();
        user.getRoleModel().add(roleByDefault);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<ConstraintViolation<UserModel>> violations = validator.validate(user);

        if (!checkIfUserIsUnique(user.getEmail())) {
            logger.error("Email already exists in DB: " + user);
            return false;
        }

        if (!violations.isEmpty()) {
            logger.error("The following Violations where found when verifying the user: " + user);
            violations.stream().forEach(violation -> logger.error(violation.toString()));
            return false;
        }

        UserModel savedUser = userRepository.save(user);
        if (savedUser.getUserId() > 0) {
            logger.info("Successfully saved: " + user);
            return true;
        }
        return false;
    }

    public boolean checkIfUserIsUnique(String email) {
        long numberOfUsersWithSameEmail = userRepository.checkIfEmailAlreadyExistsInDb(email);
        logger.info("Verification for: " + email + " . There are " + numberOfUsersWithSameEmail + " repetitions in the DB");

        return (numberOfUsersWithSameEmail == 0) ? true : false;
    }

}
