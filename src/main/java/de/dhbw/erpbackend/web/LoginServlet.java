package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.domain.User;
import de.dhbw.erpbackend.service.LoginService;
import de.dhbw.erpbackend.service.UserFacingException;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String VIEW = "/WEB-INF/views/login.jsp";

    @Inject
    LoginService loginService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (SessionHelper.isLoggedIn(req)) {
            resp.sendRedirect(req.getContextPath() + "/overview");
            return;
        }
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (SessionHelper.isLoggedIn(req)) {
            resp.sendRedirect(req.getContextPath() + "/overview");
            return;
        }
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            User user = loginService.authenticate(username, password);
            SessionHelper.login(req, user.getUsername());
            resp.sendRedirect(req.getContextPath() + "/overview");
        } catch (UserFacingException ex) {
            ErrorHelper.showError(req, resp, ex);
        }
    }
}
