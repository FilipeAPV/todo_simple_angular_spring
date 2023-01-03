package com.mytodo.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<UserModel> list() {
        return userRepository.findAll();
    }

}
