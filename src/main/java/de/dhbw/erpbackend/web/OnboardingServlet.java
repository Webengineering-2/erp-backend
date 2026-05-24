package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.domain.User;
import de.dhbw.erpbackend.service.OnboardingService;
import de.dhbw.erpbackend.service.UserFacingException;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/onboarding")
public class OnboardingServlet extends HttpServlet {

    private static final String VIEW = "/WEB-INF/views/onboarding.jsp";

    @Inject
    OnboardingService onboardingService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String passwordRepeat = req.getParameter("passwordRepeat");

        try {
            User user = onboardingService.register(username, password, passwordRepeat);
            SessionHelper.login(req, user.getUsername());
            resp.sendRedirect(req.getContextPath() + "/overview");
        } catch (UserFacingException ex) {
            ErrorHelper.showError(req, resp, ex);
        }
    }
}
