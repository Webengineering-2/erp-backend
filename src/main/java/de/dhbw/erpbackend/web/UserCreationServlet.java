package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.service.OnboardingService;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/create-user")
public class UserCreationServlet extends ProtectedServlet {

    @Inject
    OnboardingService onboardingService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String passwordRepeat = req.getParameter("passwordRepeat");

        onboardingService.register(username, password, passwordRepeat);
        resp.sendRedirect(req.getContextPath() + "/overview.jsp");
    }
}
