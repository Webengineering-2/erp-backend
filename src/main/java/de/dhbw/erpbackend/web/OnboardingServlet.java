package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.domain.LogType;
import de.dhbw.erpbackend.domain.User;
import de.dhbw.erpbackend.service.OnboardingService;
import de.dhbw.erpbackend.service.LogService;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/onboarding")
public class OnboardingServlet extends BaseServlet {

    @Inject
    OnboardingService onboardingService;

    @Inject
    LogService logService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (SessionHelper.isLoggedIn(req)) {
            resp.sendRedirect(req.getContextPath() + "/overview.jsp");
            return;
        }
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String passwordRepeat = req.getParameter("passwordRepeat");

        User user = onboardingService.register(username, password, passwordRepeat);
        SessionHelper.login(req, user.getUsername());
        logService.log(user.getUsername(), LogType.USER_REGISTERED,
                "Erster Benutzer '" + user.getUsername() + "' wurde registriert.");
        resp.sendRedirect(req.getContextPath() + "/overview.jsp");
    }
}
