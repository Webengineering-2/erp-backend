package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Location;
import de.dhbw.erpbackend.service.CreationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/location")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationRessource {
    @Inject
    private CreationService creationService;

    @GET
    @Path("/all")
    public List<Location> getLocation(@QueryParam("q") String query) {
        return creationService.getMatchingLocations(query);
    }

    @GET
    @Path("/id/{id}")
    public Location getLocation(@PathParam("id") Long id) {
        return creationService.getLocationById(id);
    }

    @POST
    public Response create(Location location) {
        creationService.saveLocation(location);
        return Response.ok(location).build();
    }

    @PUT
    @Path("/id/{id}")
    public Response update(@PathParam("id") Long id, Location location) {
        location.setId(id);
        creationService.saveLocation(location);
        return Response.ok(location).build();
    }

    @DELETE
    @Path("/id/{id}")
    public Response delete(@PathParam("id") Long id) {
        creationService.deleteLocation(id);
        return Response.noContent().build();
    }
}
