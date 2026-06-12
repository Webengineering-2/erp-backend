package de.dhbw.erpbackend.resources;


import de.dhbw.erpbackend.domain.Product;
import de.dhbw.erpbackend.service.CreationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@ApplicationPath("/api")
@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    private CreationService creationService;

    @GET
    @Path("/all")
    public List<Product> getProducts(@QueryParam("q") String query) {
        return creationService.getMatchingProducts(query);
    }

    @GET
    @Path("/id/{id}")
    public Product getProduct(@PathParam("id") Long id) {
        System.out.println("GET PRODUCT BY ID: " + id);
        return creationService.getProductById(id);
    }

    @POST
    public Response create(Product product) {
        creationService.saveProduct(product);
        return Response.ok(product).build();
    }

    @PUT
    @Path("/id/{id}")
    public Response update(@PathParam("id") Long id, Product product) {
        product.setId(id);
        creationService.saveProduct(product);
        return Response.ok(product).build();
    }

    @DELETE
    @Path("/id/{id}")
    public Response delete(@PathParam("id") Long id) {
        creationService.deleteProduct(id);
        return Response.noContent().build();
    }
}