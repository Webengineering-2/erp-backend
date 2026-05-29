package de.dhbw.erpbackend.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Base class for servlets that require an authenticated session. Any request
 * without a logged-in session is redirected to "/" before the concrete servlet
 * sees it.
 */
public abstract class ProtectedServlet extends BaseServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionHelper.isLoggedIn(req)) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        super.service(req, resp);
    }
}
