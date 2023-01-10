package com.mytodo.backend.security.configuration;

import com.mytodo.backend.security.CurrentUserService;
import com.mytodo.backend.security.session.SessionFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final CurrentUserService currentUserService;
    private final SessionFilter sessionFilter;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfiguration(CurrentUserService currentUserService, SessionFilter sessionFilter, PasswordEncoder passwordEncoder) {
        this.currentUserService = currentUserService;
        this.sessionFilter = sessionFilter;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http.exceptionHandling().authenticationEntryPoint(
                ((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                            authException.getMessage());
                })
        ).and();

        http.authorizeHttpRequests()
                .requestMatchers("/api/login").permitAll()
                .requestMatchers("/api/users").permitAll()
                .requestMatchers("/api/registerUser").permitAll()
                .requestMatchers("/api/verifyIfEmailExists").permitAll()
                .anyRequest().authenticated();


        http.addFilterBefore(
                        sessionFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/verifyIfEmailExists").permitAll()
                .requestMatchers("/registerUser").permitAll()
                .requestMatchers("/").permitAll()
*//*                .requestMatchers("/applogout").permitAll()
                .requestMatchers("/login").permitAll()*//*
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/users").permitAll()
                .and()
                .logout().logoutUrl("/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutSuccessHandler(
                        (httpServletRequest, httpServletResponse, authentication) -> {
                            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                            System.out.println(httpServletRequest.getHeaderNames().toString());
                            httpServletResponse.setHeader("Access-Control-Allow-Origin" , "testing");
                        })
                .and()
                .httpBasic();
        return httpSecurity.build();

    } */

}
