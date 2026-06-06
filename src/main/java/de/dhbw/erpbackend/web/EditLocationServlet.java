import de.dhbw.erpbackend.service.CreationService;
import de.dhbw.erpbackend.web.BaseServlet;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/edit-location")
public class EditLocationServlet extends BaseServlet {

    @Inject
    private CreationService creationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, jakarta.servlet.ServletException {

        Long id = Long.valueOf(req.getParameter("id"));

        req.setAttribute("location",
                creationService.getLocationById(id));

        req.getRequestDispatcher("/WEB-INF/views/editlocation.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        Long id = Long.valueOf(req.getParameter("id"));

        var location = creationService.getLocationById(id);

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