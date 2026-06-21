package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Customer;
import de.dhbw.erpbackend.service.CreationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    private CreationService creationService;

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
        return Response.ok(customer).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Customer customer) {
        customer.setId(id);
        creationService.saveCustomer(customer);
        return Response.ok(customer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        creationService.deleteCustomer(id);
        return Response.noContent().build();
    }
}