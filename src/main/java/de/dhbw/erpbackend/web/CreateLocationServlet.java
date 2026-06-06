package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.domain.Location;
import de.dhbw.erpbackend.service.CreationService;
import de.dhbw.erpbackend.web.BaseServlet;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/create-location")
public class CreateLocationServlet extends BaseServlet {

    @Inject
    private CreationService creationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, ServletException {

        req.getRequestDispatcher("/WEB-INF/views/createlocation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        Location location = new Location();
        location.setName(req.getParameter("name"));
        location.setDescription(req.getParameter("description"));

        creationService.saveLocation(location);

        resp.setContentType("text/html");
        resp.getWriter().write("""
            <script>
                window.opener.location.reload();
                window.close();
            </script>
            """);
    }
}