package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RootServletTest {

    @Mock HttpServletRequest req;
    @Mock HttpServletResponse resp;
    @Mock HttpSession session;
    @Mock UserRepository userRepository;

    RootServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new RootServlet();
        servlet.userRepository = userRepository;
        when(req.getContextPath()).thenReturn("");
    }

    @Test
    void loggedInRedirectsToOverview() throws Exception {
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("alice");

        servlet.doGet(req, resp);

        verify(resp).sendRedirect("/overview.jsp");
    }

    @Test
    void noUsersRedirectsToOnboarding() throws Exception {
        when(req.getSession(false)).thenReturn(null);
        when(userRepository.count()).thenReturn(0L);

        servlet.doGet(req, resp);

        verify(resp).sendRedirect("/onboarding.jsp");
    }

    @Test
    void usersExistRedirectsToLogin() throws Exception {
        when(req.getSession(false)).thenReturn(null);
        when(userRepository.count()).thenReturn(3L);

        servlet.doGet(req, resp);

        verify(resp).sendRedirect("/login.jsp");
    }
}
