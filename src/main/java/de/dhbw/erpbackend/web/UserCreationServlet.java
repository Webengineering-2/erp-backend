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

@WebServlet("/create-user")
public class UserCreationServlet extends ProtectedServlet {

    @Inject
    OnboardingService onboardingService;

    @Inject
    LogService logService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String passwordRepeat = req.getParameter("passwordRepeat");

        User created = onboardingService.register(username, password, passwordRepeat);
        // ProtectedServlet guarantees an authenticated session: the actor is the admin.
        logService.log(SessionHelper.currentUsername(req), LogType.USER_CREATED,
                "Benutzer '" + created.getUsername() + "' wurde erstellt.");
        resp.sendRedirect(req.getContextPath() + "/overview.jsp");
    }
}
