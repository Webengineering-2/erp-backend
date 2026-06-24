package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.service.ItemService;
import de.dhbw.erpbackend.service.UserFacingException;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/sell")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SellResource {

    @Inject
    private ItemService itemService;

    @POST
    @Path("/{id}")
    public Response sell(@PathParam("id") Long id, SellRequest body) {
        try {
            itemService.sell(id, body.getQuantity(), body.getSellUnitPrice(), body.getCustomerId());
            return Response.ok().build();
        } catch (UserFacingException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
