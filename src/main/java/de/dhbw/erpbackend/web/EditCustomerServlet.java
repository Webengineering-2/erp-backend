package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.service.CreationService;
import de.dhbw.erpbackend.web.BaseServlet;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/edit-customer")
public class EditCustomerServlet extends BaseServlet {

    @Inject
    private CreationService creationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, jakarta.servlet.ServletException {

        Long id = Long.valueOf(req.getParameter("id"));

        req.setAttribute("customer",
                creationService.getCustomerById(id));

        req.getRequestDispatcher("/WEB-INF/views/editcustomer.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        Long id = Long.valueOf(req.getParameter("id"));

        var customer = creationService.getCustomerById(id);

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