package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.domain.Product;
import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.service.CreationService;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/edit-product")
public class EditProductServlet extends BaseServlet {

    @Inject
    private CreationService creationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, jakarta.servlet.ServletException {

        Long id = Long.valueOf(req.getParameter("id"));

        Product product = creationService.getProductById(id);

        req.setAttribute("product", product);
        req.setAttribute("categories", creationService.getMatchingCategories(null));

        req.getRequestDispatcher("/WEB-INF/views/editproduct.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Long id = Long.valueOf(req.getParameter("id"));

        Product product = creationService.getProductById(id);

        product.setName(req.getParameter("name"));

        BigDecimal price = new BigDecimal(req.getParameter("price"));
        product.setUnitPrice(price);

        Long categoryId = Long.valueOf(req.getParameter("categoryId"));
        Category category = creationService.getCategoriesById(categoryId);

        product.setCategory(category);

        creationService.saveProduct(product);

        resp.setContentType("text/html");
        resp.getWriter().write("""
            <script>
                window.opener.location.reload();
                window.close();
            </script>
            """);
    }
}