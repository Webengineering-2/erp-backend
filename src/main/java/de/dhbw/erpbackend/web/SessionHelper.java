package de.dhbw.erpbackend.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public final class SessionHelper {

    public static final String USERNAME_ATTR = "username";

    private SessionHelper() {}

    public static boolean isLoggedIn(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null && session.getAttribute(USERNAME_ATTR) instanceof String s && !s.isEmpty();
    }

    public static String currentUsername(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) return null;
        Object val = session.getAttribute(USERNAME_ATTR);
        return val instanceof String s ? s : null;
    }

    public static void login(HttpServletRequest req, String username) {
        HttpSession session = req.getSession(true);
        session.setAttribute(USERNAME_ATTR, username);
    }

    public static void logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
    }
}
