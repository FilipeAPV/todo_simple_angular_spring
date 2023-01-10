package com.mytodo.backend.security;

import com.mytodo.backend.user.UserModel;
import com.mytodo.backend.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentUserService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(CurrentUserService.class);

    private final UserRepository userRepository;

    @Autowired
    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserModel> user = Optional.ofNullable(userRepository.findByEmail(username));

        if (user.isPresent()) {
            logger.info("Username found in the DB: " + username);
            return new CurrentUser(user.get());
        }

        logger.warn("Failed to find user with username in DB: " + username);
        throw new UsernameNotFoundException("Failed to find user with username: " + username);
    }
}
