package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.domain.LogType;
import de.dhbw.erpbackend.domain.Product;
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

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    private CreationService creationService;

    @Inject
    private LogService logService;

    @Context
    private HttpServletRequest request;

    @GET
    @Path("/")
    public List<Product> getProducts(@QueryParam("q") String query) {
        return creationService.getMatchingProducts(query);
    }

    @GET
    @Path("/{id}")
    public Product getProduct(@PathParam("id") Long id) {
        return creationService.getProductById(id);
    }

    @POST
    public Response create(Product product) {
        creationService.saveProduct(product);
        logService.log(SessionHelper.currentUsername(request), LogType.PRODUCT_CREATED,
                "Produkt '" + product.getName() + "' wurde erstellt.");
        return Response.ok(product).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Product product) {
        Product savable = creationService.getProductById(id);
        if (savable == null) return Response.status(Response.Status.BAD_REQUEST).build();

        savable.setName(product.getName());
        savable.setUnitPrice(product.getUnitPrice());

        Category saveCategory = creationService.getCategoryById(product.getCategory().getId());
        savable.setCategory(saveCategory);

        creationService.saveProduct(savable);
        logService.log(SessionHelper.currentUsername(request), LogType.PRODUCT_UPDATED,
                "Produkt '" + savable.getName() + "' wurde bearbeitet.");
        return Response.ok(savable).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        creationService.deleteProduct(id);
        logService.log(SessionHelper.currentUsername(request), LogType.PRODUCT_DELETED,
                "Produkt #" + id + " wurde gelöscht.");
        return Response.noContent().build();
    }
}