package com.mytodo.backend.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    private final UserService userService;

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

}
