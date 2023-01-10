package com.mytodo.backend.user;

import com.mytodo.backend.role.RoleModel;
import com.mytodo.backend.security.session.SessionRegistry;
import com.mytodo.backend.user.dto.ResponseDTO;
import com.mytodo.backend.user.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/users")
    public List<UserModel> list() {
        return userRepository.findAll();
    }

    @PostMapping("/api/registerUser")
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

    @GetMapping("/api/verifyIfEmailExists")
    public ResponseEntity<Boolean> verifyIfEmailExists(@RequestParam(name = "email") String email) {
        boolean isUserUnique = userService.checkIfUserIsUnique(email);
        return new ResponseEntity<>(isUserUnique, HttpStatus.OK);
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<RoleModel> roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        // Since we don't have the prefix "ROLE_" inside our DB, it needs to be appended here.

        /*roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName())));*/
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"));

        return grantedAuthorities;
    }

    @PostMapping("/api/login")
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

    @GetMapping("/api/list")
    public List<String> getListItems() {
        return List.of("1", "2", "3");
    }

    @GetMapping("/api/users")
    public void getList(HttpSession httpSession) {
        System.out.println("Authenticated Users");
        System.out.println(sessionRegistry.getSessions());

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

/*    @GetMapping("/login")
    public ResponseEntity<BindingResult> login(@RequestBody UserCredentialsModel credentials) {

        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());

        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();

        sc.setAuthentication(auth);

        logger.info(auth.getName());
        logger.info(auth.getPrincipal().toString());
        logger.info(auth.getAuthorities().toString());

        return ResponseEntity.ok().build();
    }*/

   @GetMapping("/api/logout")
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
        }

        return ResponseEntity.ok().build();
    }

}
