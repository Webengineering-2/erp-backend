package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Item;
import de.dhbw.erpbackend.domain.LogType;
import de.dhbw.erpbackend.service.ItemService;
import de.dhbw.erpbackend.service.LogService;
import de.dhbw.erpbackend.web.SessionHelper;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemResource {

    @Inject
    private ItemService itemService;

    @Inject
    private LogService logService;

    @Context
    private HttpServletRequest request;

    @PUT
    public Response create(Item item) {
        itemService.createItem(
                item.getProduct().getId(),
                item.getLocation().getId(),
                item.getQuantity(),
                item.getUnitBuyPrice());
        logService.log(SessionHelper.currentUsername(request), LogType.ITEM_CREATED,
                "Warenbestand angelegt: " + item.getQuantity() + " Stück (Produkt #"
                        + item.getProduct().getId() + ", Lagerort #" + item.getLocation().getId() + ").");
        return Response.ok().build();
    }
}
