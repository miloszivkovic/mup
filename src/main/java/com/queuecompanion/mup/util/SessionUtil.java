package com.queuecompanion.mup.util;

import javax.servlet.http.Cookie;
import java.util.UUID;

public class SessionUtil {
    private SessionUtil() {
    }

    public static Cookie createFromSessionId(String sessionId) {
        Cookie cookie = new Cookie("session-id", UUID.randomUUID().toString());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }
}
