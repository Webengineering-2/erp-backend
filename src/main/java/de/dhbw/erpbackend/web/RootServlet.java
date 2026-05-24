package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {""})
public class RootServlet extends HttpServlet {

    @Inject
    UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ctx = req.getContextPath();
        if (SessionHelper.isLoggedIn(req)) {
            resp.sendRedirect(ctx + "/overview");
            return;
        }
        if (!userService.anyUserExists()) {
            resp.sendRedirect(ctx + "/onboarding");
            return;
        }
        resp.sendRedirect(ctx + "/login");
    }
}
