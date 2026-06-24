package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.domain.User;
import de.dhbw.erpbackend.service.OnboardingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OnboardingServletTest {

    @Mock HttpServletRequest req;
    @Mock HttpServletResponse resp;
    @Mock HttpSession session;
    @Mock OnboardingService onboardingService;

    OnboardingServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new OnboardingServlet();
        servlet.onboardingService = onboardingService;
    }

    @Test
    void alreadyLoggedInRedirectsWithoutRegistering() throws Exception {
        when(req.getContextPath()).thenReturn("");
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("alice");

        servlet.doPost(req, resp);

        verify(resp).sendRedirect("/overview.jsp");
        verifyNoInteractions(onboardingService);
    }

    @Test
    void registersFirstUserLogsInAndRedirects() throws Exception {
        when(req.getContextPath()).thenReturn("");
        when(req.getSession(false)).thenReturn(null);
        when(req.getParameter("username")).thenReturn("admin");
        when(req.getParameter("password")).thenReturn("pw");
        when(req.getParameter("passwordRepeat")).thenReturn("pw");
        User user = new User();
        user.setUsername("admin");
        when(onboardingService.register("admin", "pw", "pw")).thenReturn(user);
        when(req.getSession(true)).thenReturn(session);

        servlet.doPost(req, resp);

        verify(session).setAttribute("username", "admin");
        verify(resp).sendRedirect("/overview.jsp");
    }
}
