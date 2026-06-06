package de.dhbw.erpbackend.web;

import de.dhbw.erpbackend.service.CreationService;
import de.dhbw.erpbackend.web.BaseServlet;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import java.io.IOException;
@Transactional
@WebServlet("/delete-entity")
public class DeleteEntityServlet extends BaseServlet {

    @Inject
    private CreationService creationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("id", req.getParameter("id"));
        req.setAttribute("type", req.getParameter("type"));
        req.setAttribute("name", req.getParameter("name"));

        req.getRequestDispatcher("/WEB-INF/views/deleteEntity.jsp")
                .forward(req, resp);
    }
    @Transactional
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String type = req.getParameter("type");
        Long id = Long.valueOf(req.getParameter("id"));

        if (type == null || id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            switch (type.toLowerCase()) {

                case "product":
                    creationService.deleteProduct(id);
                    break;

                case "category":
                    creationService.deleteCategory(id);
                    break;

                case "location":
                    creationService.deleteLocation(id);
                    break;

                case "customer":
                    creationService.deleteCustomer(id);
                    break;

                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown type");
                    return;
            }

            resp.setContentType("text/html");
            resp.getWriter().write("""
                    <script>
                        window.opener.location.reload();
                        window.close();
                    </script>
                    """);
        } catch (Exception e) {
                req.setAttribute("error", "Cannot delete: item is still in use.");

                req.setAttribute("id", id);
                req.setAttribute("type", type);
                req.setAttribute("name", req.getParameter("name"));

                req.getRequestDispatcher("/WEB-INF/views/deleteEntity.jsp")
                        .forward(req, resp);
            }
        }
    }
