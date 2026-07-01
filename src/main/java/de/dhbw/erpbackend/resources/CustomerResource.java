package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Customer;
import de.dhbw.erpbackend.domain.LogType;
import de.dhbw.erpbackend.service.CreationService;
import de.dhbw.erpbackend.service.LogService;
import de.dhbw.erpbackend.web.SessionHelper;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    private CreationService creationService;

    @Inject
    private LogService logService;

    @Context
    private HttpServletRequest request;

    @GET
    @Path("/")
    public List<Customer> getCustomer(@QueryParam("q") String query) {
        return creationService.getMatchingCustomers(query);
    }

    @GET
    @Path("/{id}")
    public Customer getCustomer(@PathParam("id") Long id) {
        return creationService.getCustomerById(id);
    }

    @POST
    public Response create(Customer customer) {
        creationService.saveCustomer(customer);
        logService.log(SessionHelper.currentUsername(request), LogType.CUSTOMER_CREATED,
                "Kunde '" + customer.getName() + "' wurde erstellt.");
        return Response.ok(customer).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Customer customer) {
        customer.setId(id);
        creationService.saveCustomer(customer);
        logService.log(SessionHelper.currentUsername(request), LogType.CUSTOMER_UPDATED,
                "Kunde '" + customer.getName() + "' wurde bearbeitet.");
        return Response.ok(customer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        creationService.deleteCustomer(id);
        logService.log(SessionHelper.currentUsername(request), LogType.CUSTOMER_DELETED,
                "Kunde #" + id + " wurde gelöscht.");
        return Response.noContent().build();
    }
}