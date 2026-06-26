package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Category;
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

@Path("/category")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {
    @Inject
    private CreationService creationService;

    @Inject
    private LogService logService;

    @Context
    private HttpServletRequest request;

    @GET
    @Path("/")
    public List<Category> getCategory(@QueryParam("q") String query) {
        return creationService.getMatchingCategories(query);
    }

    @GET
    @Path("/{id}")
    public Category getCategory(@PathParam("id") Long id) {
        return creationService.getCategoryById(id);
    }

    @POST
    public Response create(Category category) {
        creationService.saveCategory(category);
        logService.log(SessionHelper.currentUsername(request), LogType.CATEGORY_CREATED,
                "Kategorie '" + category.getName() + "' wurde erstellt.");
        return Response.ok(category).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Category category) {
        category.setId(id);
        creationService.saveCategory(category);
        logService.log(SessionHelper.currentUsername(request), LogType.CATEGORY_UPDATED,
                "Kategorie '" + category.getName() + "' wurde bearbeitet.");
        return Response.ok(category).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        creationService.deleteCategory(id);
        logService.log(SessionHelper.currentUsername(request), LogType.CATEGORY_DELETED,
                "Kategorie #" + id + " wurde gelöscht.");
        return Response.noContent().build();
    }
}
