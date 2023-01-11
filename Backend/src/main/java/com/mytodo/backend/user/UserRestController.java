package com.mytodo.backend.user;

import com.mytodo.backend.role.RoleModel;
import com.mytodo.backend.security.session.SessionRegistry;
import com.mytodo.backend.task.TaskDTO;
import com.mytodo.backend.task.TaskModel;
import com.mytodo.backend.task.TaskRepository;
import com.mytodo.backend.user.dto.ResponseDTO;
import com.mytodo.backend.user.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SessionRegistry sessionRegistry;

    Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<BindingResult> registerUser(@RequestBody UserModel user,
                                                BindingResult bindingResult) {

        boolean isSaved = userService.saveUser(user);

        if (isSaved && !bindingResult.hasErrors()) {
            return ResponseEntity.ok().build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/verifyIfEmailExists")
    public ResponseEntity<Boolean> verifyIfEmailExists(@RequestParam(name = "email") String email) {
        boolean isUserUnique = userService.checkIfUserIsUnique(email);
        return new ResponseEntity<>(isUserUnique, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody UserDTO userDto) {

        logger.info("userDto: " + userDto.getUsername() + " : " + userDto.getPassword());

        UserModel user = userRepository.findByEmail(userDto.getUsername());

        // AuthenticationManager is responsible for authenticating users and returning an Authentication
        // object that contains the user's credentials and authorities
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword())
        );

        final String sessionId = sessionRegistry.registerSession(userDto.getUsername());

        ResponseDTO response = new ResponseDTO();

        response.setSessionId(sessionId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public List<String> getListItems() {
        return List.of("1", "2", "3");
    }

    @GetMapping("/users")
    public void getList(HttpSession httpSession) {


        System.out.println("Logged In Users");
        List<String> loggedInUsers = new ArrayList<>();
        Enumeration attributeNames = httpSession.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            loggedInUsers.add(attributeNames.nextElement().toString());
        }

        loggedInUsers.forEach(user -> System.out.println(user));

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            System.out.println("Username: " + username);
        } else {
            String username = principal.toString();
            System.out.println("Username: " + username);
        }
    }

   @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response
                                       ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null){
            if (auth.getPrincipal() instanceof UserDetails) {
                String username = ((UserDetails)auth.getPrincipal()).getUsername();
                System.out.println("Logging out Username: " + username);
            } else {
                String username = auth.getPrincipal().toString();
                System.out.println("Username: " + username);
            }

            new SecurityContextLogoutHandler().logout(request, response, auth);
            request.getSession().invalidate();
            SecurityContextHolder.clearContext();
            sessionRegistry.removeSession(request.getHeader(HttpHeaders.AUTHORIZATION));
        }

        return ResponseEntity.ok().build();
    }


}
