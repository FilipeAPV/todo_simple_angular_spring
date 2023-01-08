package com.mytodo.backend.user;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserModel> list() {
        return userRepository.findAll();
    }

    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@RequestBody UserModel user) {

        boolean isSaved = userService.saveUser(user);

        return ResponseEntity.ok("Success from backend");
    }

    @GetMapping("/verifyIfEmailExists")
    public ResponseEntity<Boolean> verifyIfEmailExists(@RequestParam(name = "email") String email) {
        boolean isUserUnique = userService.checkIfUserIsUnique(email);
        return new ResponseEntity<>(isUserUnique, HttpStatus.OK);
    }

}
