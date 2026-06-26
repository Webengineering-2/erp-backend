package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Location;
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

@Path("/location")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationRessource {
    @Inject
    private CreationService creationService;

    @Inject
    private LogService logService;

    @Context
    private HttpServletRequest request;

    @GET
    @Path("/")
    public List<Location> getLocation(@QueryParam("q") String query) {
        return creationService.getMatchingLocations(query);
    }

    @GET
    @Path("/{id}")
    public Location getLocation(@PathParam("id") Long id) {
        return creationService.getLocationById(id);
    }

    @POST
    public Response create(Location location) {
        creationService.saveLocation(location);
        logService.log(SessionHelper.currentUsername(request), LogType.LOCATION_CREATED,
                "Lagerort '" + location.getName() + "' wurde erstellt.");
        return Response.ok(location).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Location location) {
        location.setId(id);
        creationService.saveLocation(location);
        logService.log(SessionHelper.currentUsername(request), LogType.LOCATION_UPDATED,
                "Lagerort '" + location.getName() + "' wurde bearbeitet.");
        return Response.ok(location).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        creationService.deleteLocation(id);
        logService.log(SessionHelper.currentUsername(request), LogType.LOCATION_DELETED,
                "Lagerort #" + id + " wurde gelöscht.");
        return Response.noContent().build();
    }
}
