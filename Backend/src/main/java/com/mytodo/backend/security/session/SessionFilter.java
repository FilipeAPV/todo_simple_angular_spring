package com.mytodo.backend.security.session;

import com.mytodo.backend.security.CurrentUser;
import com.mytodo.backend.security.CurrentUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class SessionFilter extends OncePerRequestFilter {

    private final SessionRegistry sessionRegistry;
    private final CurrentUserService currentUserService;

    @Autowired
    public SessionFilter(SessionRegistry sessionRegistry, CurrentUserService currentUserService) {
        this.sessionRegistry = sessionRegistry;
        this.currentUserService = currentUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String sessionId = request.getHeader(HttpHeaders.AUTHORIZATION);

        logger.info("sessionId = request.getHeader(HttpHeaders.AUTHORIZATION): " + sessionId );

        if (sessionId == null || sessionId.length() == 0) {
            filterChain.doFilter(request, response);
            return;
        }

        final String username = sessionRegistry.getUsernameForSession(sessionId);

        if (username == null) {
            filterChain.doFilter(request, response);
            return;
        }

        CurrentUser currentUser = currentUserService.loadUserByUsername(username);

        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                currentUser,
                null,
                currentUser.getAuthorities()
        );

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    private boolean isInvalid(String sessionId) {
        try {
            UUID uuid = UUID.fromString(sessionId);
            logger.info("sessionId VALID : " + sessionId);
            return false;
        } catch (IllegalArgumentException e) {
            logger.info("sessionId INVALID : " + sessionId);
            return true;
        }
    }
}
