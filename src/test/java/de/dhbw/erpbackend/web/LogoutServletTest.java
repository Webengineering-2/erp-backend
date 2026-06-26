package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.domain.LogType;
import de.dhbw.erpbackend.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogoutServletTest {

    @Mock HttpServletRequest req;
    @Mock HttpServletResponse resp;
    @Mock HttpSession session;
    @Mock LogService logService;

    @Test
    void invalidatesSessionLogsAndRedirectsToRoot() throws Exception {
        when(req.getContextPath()).thenReturn("");
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("alice");

        LogoutServlet servlet = new LogoutServlet();
        servlet.logService = logService;
        servlet.doGet(req, resp);

        verify(session).invalidate();
        verify(logService).log(eq("alice"), eq(LogType.USER_LOGOUT), anyString());
        verify(resp).sendRedirect("/");
    }
}
