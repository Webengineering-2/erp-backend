package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.domain.Customer;
import de.dhbw.erpbackend.domain.Location;
import de.dhbw.erpbackend.domain.Product;
import de.dhbw.erpbackend.service.CreationService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/create")
public class CreateServlet extends ProtectedServlet {

    @Inject
    private CreationService creationService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String view = request.getParameter("createView");

        if (view == null || view.isBlank()) {
            view = "products";
        }
        request.setAttribute("createView", view);

        String search = request.getParameter("search");

        switch (view.toLowerCase()) {
            case "products" -> {
                List<Product> products = creationService.getMatchingProducts(search);
                request.setAttribute("products", products);
                List<Category> categories = creationService.getMatchingCategories(null);
                request.setAttribute("categories", categories);
            }
            case "categories" -> {
                List<Category> categories = creationService.getMatchingCategories(search);
                request.setAttribute("categories", categories);
            }
            case "locations" -> {
                List<Location> locations = creationService.getMatchingLocations(search);
                request.setAttribute("locations", locations);
            }
            case "customers" -> {
                List<Customer> customers = creationService.getMatchingCustomers(search);
                request.setAttribute("customers", customers);
            }
        }

        request.getRequestDispatcher("/WEB-INF/views/create.jsp")
                .forward(request, response);
    }
}