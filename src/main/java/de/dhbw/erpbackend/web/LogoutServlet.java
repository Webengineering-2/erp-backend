package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.domain.LogType;
import de.dhbw.erpbackend.service.LogService;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends BaseServlet {

    @Inject
    LogService logService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Capture the actor before the session is invalidated.
        String username = SessionHelper.currentUsername(req);
        SessionHelper.logout(req);
        if (username != null) {
            logService.log(username, LogType.USER_LOGOUT,
                    "Benutzer '" + username + "' hat sich abgemeldet.");
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
