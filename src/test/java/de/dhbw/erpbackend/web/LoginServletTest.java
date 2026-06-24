package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.domain.User;
import de.dhbw.erpbackend.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServletTest {

    @Mock HttpServletRequest req;
    @Mock HttpServletResponse resp;
    @Mock HttpSession session;
    @Mock LoginService loginService;

    LoginServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new LoginServlet();
        servlet.loginService = loginService;
    }

    @Test
    void alreadyLoggedInRedirectsWithoutAuthenticating() throws Exception {
        when(req.getContextPath()).thenReturn("");
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("alice");

        servlet.doPost(req, resp);

        verify(resp).sendRedirect("/overview.jsp");
        verifyNoInteractions(loginService);
    }

    @Test
    void validCredentialsLogInAndRedirect() throws Exception {
        when(req.getContextPath()).thenReturn("");
        when(req.getSession(false)).thenReturn(null);          // not logged in
        when(req.getParameter("username")).thenReturn("bob");
        when(req.getParameter("password")).thenReturn("secret");
        User user = new User();
        user.setUsername("bob");
        when(loginService.authenticate("bob", "secret")).thenReturn(user);
        when(req.getSession(true)).thenReturn(session);

        servlet.doPost(req, resp);

        verify(session).setAttribute("username", "bob");
        verify(resp).sendRedirect("/overview.jsp");
    }

    @Test
    void authenticationFailurePropagates() {
        when(req.getSession(false)).thenReturn(null);
        when(req.getParameter("username")).thenReturn("bob");
        when(req.getParameter("password")).thenReturn("wrong");
        when(loginService.authenticate("bob", "wrong"))
                .thenThrow(new de.dhbw.erpbackend.service.UserFacingException("Benutzername oder Passwort ist falsch."));

        // BaseServlet.service() (not exercised here) maps this to the error page;
        // doPost itself must let it bubble up.
        org.junit.jupiter.api.Assertions.assertThrows(
                de.dhbw.erpbackend.service.UserFacingException.class,
                () -> servlet.doPost(req, resp));
        verify(session, never()).setAttribute(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.any());
    }
}
