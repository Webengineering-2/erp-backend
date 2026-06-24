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
class ErrorHelperTest {

    @Mock HttpServletRequest req;
    @Mock HttpServletResponse resp;
    @Mock RequestDispatcher dispatcher;

    @Test
    void showErrorSetsMessageStatusAndForwardsToErrorView() throws Exception {
        when(req.getRequestDispatcher("/WEB-INF/views/error.jsp")).thenReturn(dispatcher);

        ErrorHelper.showError(req, resp, new UserFacingException("Etwas ist schiefgelaufen."));

        verify(req).setAttribute("errorMessage", "Etwas ist schiefgelaufen.");
        verify(resp).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(dispatcher).forward(req, resp);
    }
}
