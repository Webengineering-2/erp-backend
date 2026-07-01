package de.dhbw.erpbackend.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiAuthFilterTest {

    @Mock HttpServletRequest req;
    @Mock HttpServletResponse resp;
    @Mock HttpSession session;
    @Mock FilterChain chain;

    final ApiAuthFilter filter = new ApiAuthFilter();

    @Test
    void unauthenticatedRequestIsRejectedWith401AndNotForwarded() throws Exception {
        when(req.getSession(false)).thenReturn(null); // no session => not logged in

        filter.doFilter(req, resp, chain);

        verify(resp).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        verifyNoInteractions(chain); // request must not reach the resource
    }

    @Test
    void sessionWithoutUsernameIsRejected() throws Exception {
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn(null);

        filter.doFilter(req, resp, chain);

        verify(resp).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        verifyNoInteractions(chain);
    }

    @Test
    void authenticatedRequestPassesThrough() throws Exception {
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("alice");

        filter.doFilter(req, resp, chain);

        verify(chain).doFilter(req, resp);
        verify(resp, never()).sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
