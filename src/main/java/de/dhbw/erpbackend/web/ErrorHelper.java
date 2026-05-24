package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.service.UserFacingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public final class ErrorHelper {

    public static final String ERROR_MESSAGE_ATTR = "errorMessage";
    private static final String ERROR_VIEW = "/WEB-INF/views/error.jsp";

    private ErrorHelper() {}

    public static void showError(HttpServletRequest req, HttpServletResponse resp, UserFacingException ex)
            throws ServletException, IOException {
        req.setAttribute(ERROR_MESSAGE_ATTR, ex.getMessage());
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        req.getRequestDispatcher(ERROR_VIEW).forward(req, resp);
    }
}
