package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.service.CreationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/category")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {
    @Inject
    private CreationService creationService;

    @GET
    @Path("/all")
    public List<Category> getCategory(@QueryParam("q") String query) {
        return creationService.getMatchingCategories(query);
    }

    @GET
    @Path("/id/{id}")
    public Category getCategory(@PathParam("id") Long id) {
        return creationService.getCategoryById(id);
    }

    @POST
    public Response create(Category category) {
        creationService.saveCategory(category);
        return Response.ok(category).build();
    }

    @PUT
    @Path("/id/{id}")
    public Response update(@PathParam("id") Long id, Category category) {
        category.setId(id);
        creationService.saveCategory(category);
        return Response.ok(category).build();
    }

    @DELETE
    @Path("/id/{id}")
    public Response delete(@PathParam("id") Long id) {
        creationService.deleteCategory(id);
        return Response.noContent().build();
    }
}
