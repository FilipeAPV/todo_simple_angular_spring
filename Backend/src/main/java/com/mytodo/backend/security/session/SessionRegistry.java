package com.mytodo.backend.security.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

@Component
public class SessionRegistry {
    private static final HashMap<String, String> SESSIONS = new HashMap<>();

    Logger logger = LoggerFactory.getLogger(SessionRegistry.class);

    public String registerSession(final String username) {
        if (username == null) {
            throw new RuntimeException("Username needs to be provided");
        }

        final String sessionId = generateSessionId();

        logger.info("Saved in SESSION: " + username + " : " + sessionId);

        SESSIONS.put(sessionId, username);
        return sessionId;
    }

    public String getUsernameForSession(final String sessionId) {

        if (SESSIONS.get(sessionId) != null) {
            logger.info("Username found in SESSIONS: " + SESSIONS.get(sessionId));
        } else {
            logger.warn("No username found in SESSIONS for sessionId: " + sessionId);
        }

        return SESSIONS.get(sessionId);
    }

    private String generateSessionId() {
        return new String(
                Base64.getEncoder().encode(
                        UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)
                )
        );
    }

    public HashMap<String, String> getSessions() {
        return SESSIONS;
    }
}
