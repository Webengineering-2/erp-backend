package de.dhbw.erpbackend.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionHelperTest {

    @Mock HttpServletRequest req;
    @Mock HttpSession session;

    @Test
    void isLoggedInFalseWhenNoSession() {
        when(req.getSession(false)).thenReturn(null);
        assertFalse(SessionHelper.isLoggedIn(req));
    }

    @Test
    void isLoggedInTrueWithUsername() {
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("alice");
        assertTrue(SessionHelper.isLoggedIn(req));
    }

    @Test
    void isLoggedInFalseWhenUsernameBlank() {
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("");
        assertFalse(SessionHelper.isLoggedIn(req));
    }

    @Test
    void isLoggedInFalseWhenAttributeNotString() {
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn(42);
        assertFalse(SessionHelper.isLoggedIn(req));
    }

    @Test
    void currentUsernameNullWhenNoSession() {
        when(req.getSession(false)).thenReturn(null);
        assertNull(SessionHelper.currentUsername(req));
    }

    @Test
    void currentUsernameReturnsValue() {
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("alice");
        assertEquals("alice", SessionHelper.currentUsername(req));
    }

    @Test
    void currentUsernameNullWhenNotString() {
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn(42);
        assertNull(SessionHelper.currentUsername(req));
    }

    @Test
    void loginCreatesSessionAndStoresUsername() {
        when(req.getSession(true)).thenReturn(session);
        SessionHelper.login(req, "bob");
        verify(session).setAttribute("username", "bob");
    }

    @Test
    void logoutInvalidatesExistingSession() {
        when(req.getSession(false)).thenReturn(session);
        SessionHelper.logout(req);
        verify(session).invalidate();
    }

    @Test
    void logoutNoSessionDoesNothing() {
        when(req.getSession(false)).thenReturn(null);
        SessionHelper.logout(req);
        verify(session, never()).invalidate();
    }
}
