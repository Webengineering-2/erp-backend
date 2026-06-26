package de.dhbw.erpbackend.web;

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
class UserCreationServletTest {

    @Mock HttpServletRequest req;
    @Mock HttpServletResponse resp;
    @Mock HttpSession session;
    @Mock OnboardingService onboardingService;

    UserCreationServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new UserCreationServlet();
        servlet.onboardingService = onboardingService;
    }

    @Test
    void unauthenticatedRequestRedirectsToRootAndDoesNotRegister() throws Exception {
        when(req.getContextPath()).thenReturn("");
        when(req.getSession(false)).thenReturn(null); // ProtectedServlet guard: not logged in

        servlet.service(req, resp);

        verify(resp).sendRedirect("/");
        verifyNoInteractions(onboardingService);
    }

    @Test
    void authenticatedPostCreatesUserAndRedirectsToOverview() throws Exception {
        when(req.getContextPath()).thenReturn("");
        when(req.getParameter("username")).thenReturn("newuser");
        when(req.getParameter("password")).thenReturn("pw");
        when(req.getParameter("passwordRepeat")).thenReturn("pw");

        servlet.doPost(req, resp);

        verify(onboardingService).register("newuser", "pw", "pw");
        verify(resp).sendRedirect("/overview.jsp");
    }
}
