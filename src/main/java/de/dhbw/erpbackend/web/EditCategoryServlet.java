package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.service.CreationService;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/edit-category")
public class EditCategoryServlet extends BaseServlet {

    @Inject
    private CreationService creationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, jakarta.servlet.ServletException {

        Long id = Long.valueOf(req.getParameter("id"));

        Category category = creationService.getCategoriesById(id);

        req.setAttribute("category", category);

        req.getRequestDispatcher("/WEB-INF/views/editcategory.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Long id = Long.valueOf(req.getParameter("id"));

        Category category = creationService.getCategoriesById(id);

        category.setName(req.getParameter("name"));
        category.setDescription(req.getParameter("description"));

        creationService.saveCategory(category);

        resp.setContentType("text/html");
        resp.getWriter().write("""
            <script>
                window.opener.location.reload();
                window.close();
            </script>
            """);
    }
}