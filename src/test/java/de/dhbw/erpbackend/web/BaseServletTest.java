package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.service.UserFacingException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BaseServletTest {

    @Mock HttpServletRequest req;
    @Mock HttpServletResponse resp;
    @Mock RequestDispatcher dispatcher;

    /** Throws a UserFacingException from doGet so we can observe BaseServlet's handling. */
    static class ThrowingServlet extends BaseServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
            throw new UserFacingException("Geschäftsfehler.");
        }
    }

    @Test
    void userFacingExceptionIsRoutedToErrorView() throws Exception {
        when(req.getMethod()).thenReturn("GET");
        when(req.getRequestDispatcher("/WEB-INF/views/error.jsp")).thenReturn(dispatcher);

        new ThrowingServlet().service(req, resp);

        // BaseServlet.service() caught the exception and delegated to ErrorHelper.
        verify(req).setAttribute("errorMessage", "Geschäftsfehler.");
        verify(resp).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(dispatcher).forward(req, resp);
    }
}
