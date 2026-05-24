package de.dhbw.erpbackend.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/overview")
public class OverviewServlet extends HttpServlet {

    private static final String VIEW = "/WEB-INF/views/overview.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionHelper.isLoggedIn(req)) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        req.setAttribute("username", SessionHelper.currentUsername(req));
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }
}
