package de.dhbw.erpbackend.web;


import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.domain.Product;
import de.dhbw.erpbackend.repository.CategoryRepository;
import de.dhbw.erpbackend.repository.ProductRepository;
import de.dhbw.erpbackend.service.CreationService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/create-product")
public class CreateProductServlet extends BaseServlet {
    @Inject
    private CreationService creationService;

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("categories", creationService.getMatchingCategories(null));

        req.getRequestDispatcher(
                        "/WEB-INF/views/createproduct.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws IOException, ServletException {

        Product product = new Product();

        product.setName(req.getParameter("name"));

        Long categoryId =
                Long.valueOf(req.getParameter("categoryId"));
        String priceParam = req.getParameter("price");

        BigDecimal price;
        try {
            price = new BigDecimal(priceParam);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid price");
        }
        product.setUnitPrice(price);

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
