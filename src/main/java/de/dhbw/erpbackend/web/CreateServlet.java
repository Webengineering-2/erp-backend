package de.dhbw.erpbackend.web;
import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.domain.Customer;
import de.dhbw.erpbackend.domain.Location;
import de.dhbw.erpbackend.domain.Product;
import de.dhbw.erpbackend.repository.CategoryRepository;
import de.dhbw.erpbackend.repository.CustomerRepository;
import de.dhbw.erpbackend.repository.LocationRepository;
import de.dhbw.erpbackend.repository.ProductRepository;
import de.dhbw.erpbackend.service.CreationService;
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
        CreationService creationService = new CreationService();
        if (view == null || view.isBlank()) {
            view = "products";
        }
        request.setAttribute("createView", view);

        String search = request.getParameter("search");

        if (view.equals("products")) {

            List<Product> products = creationService.getMatchingProducts(search) ;
            request.setAttribute("products", products);
        }

        else if (view.equals("categories")) {
            List<Category> categories = creationService.getMatchingCategories(search) ;
            request.setAttribute("categories", categoryRepository.findAll().toList());
        }

        else if (view.equals("locations")) {
            List<Location> locations = creationService.getMatchingLocations(search) ;
            request.setAttribute("categories", categoryRepository.findAll().toList());}

        else if (view.equals("customers")) {
            List<Customer> customers  = creationService.getMatchingCustomers(search) ;
            request.setAttribute("categories", categoryRepository.findAll().toList());
        }

        request.getRequestDispatcher("/WEB-INF/views/create.jsp")
                .forward(request, response);
    }
}