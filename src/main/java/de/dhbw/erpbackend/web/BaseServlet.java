package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.service.UserFacingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Base for application servlets. Catches {@link UserFacingException} thrown
 * from {@code doGet}/{@code doPost} and renders it via {@link ErrorHelper}.
 */
public abstract class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            super.service(req, resp);
        } catch (UserFacingException ex) {
            ErrorHelper.showError(req, resp, ex);
        }
    }
}
