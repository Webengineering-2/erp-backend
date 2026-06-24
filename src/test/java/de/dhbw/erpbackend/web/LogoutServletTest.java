package de.dhbw.erpbackend.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogoutServletTest {

    @Mock HttpServletRequest req;
    @Mock HttpServletResponse resp;
    @Mock HttpSession session;

    @Test
    void invalidatesSessionAndRedirectsToRoot() throws Exception {
        when(req.getContextPath()).thenReturn("");
        when(req.getSession(false)).thenReturn(session);

        new LogoutServlet().doGet(req, resp);

        verify(session).invalidate();
        verify(resp).sendRedirect("/");
    }
}
