package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.domain.User;
import de.dhbw.erpbackend.service.LoginService;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {

    @Inject
    LoginService loginService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (SessionHelper.isLoggedIn(req)) {
            resp.sendRedirect(req.getContextPath() + "/overview.jsp");
            return;
        }
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = loginService.authenticate(username, password);
        SessionHelper.login(req, user.getUsername());
        resp.sendRedirect(req.getContextPath() + "/overview.jsp");
    }
}
