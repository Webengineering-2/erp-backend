package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.domain.Customer;
import de.dhbw.erpbackend.service.CreationService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/create-customer")
public class CreateCustomerServlet extends BaseServlet {

    @Inject
    private CreationService creationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/createcustomer.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        Customer customer = new Customer();
        customer.setName(req.getParameter("name"));

        creationService.saveCustomer(customer);

        resp.setContentType("text/html");
        resp.getWriter().write("""
            <script>
                window.opener.location.reload();
                window.close();
            </script>
            """);
    }
}