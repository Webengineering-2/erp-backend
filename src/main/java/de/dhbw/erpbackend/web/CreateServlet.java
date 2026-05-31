package de.dhbw.erpbackend.web;
import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.domain.Customer;
import de.dhbw.erpbackend.domain.Location;
import de.dhbw.erpbackend.domain.Product;
import de.dhbw.erpbackend.repository.CategoryRepository;
import de.dhbw.erpbackend.repository.CustomerRepository;
import de.dhbw.erpbackend.repository.LocationRepository;
import de.dhbw.erpbackend.repository.ProductRepository;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/create")
public class CreateServlet extends BaseServlet {

    @Inject
    private ProductRepository productRepository;

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private LocationRepository locationRepository;

    @Inject
    private CustomerRepository customerRepository;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String view = request.getParameter("createView");
        request.setAttribute("createView", view);
        String search = request.getParameter("search");

        if ("products".equals(view)) {

            List<Product> products;

            if (search != null && !search.isBlank()) {
                products = productRepository.findByNameLike(search);
            } else {
                products = productRepository.findAll().toList();
            }

            request.setAttribute("products", products);
        }

        else if ("categories".equals(view)) {
            List<Category> categories;

            if (search != null && !search.isBlank()) {
                categories = categoryRepository.findByNameLike(search);
            } else {
                categories = categoryRepository.findAll().toList();
            }
            request.setAttribute("categories",
                    categoryRepository.findAll().toList());
        }

        else if ("locations".equals(view)) {
            List<Location> locations;

            if (search != null && !search.isBlank()) {
                locations = locationRepository.findByNameLike(search);
            } else {
                locations = locationRepository.findAll().toList();
            }
            request.setAttribute("categories",
                    categoryRepository.findAll().toList());
        }

        else if ("customers".equals(view)) {
            List<Customer> customers;

            if (search != null && !search.isBlank()) {
                customers = customerRepository.findByNameLike(search);
            } else {
                customers = customerRepository.findAll().toList();
            }
            request.setAttribute("categories",
                    categoryRepository.findAll().toList());
        }

        request.getRequestDispatcher("/WEB-INF/views/create.jsp")
                .forward(request, response);
    }
}