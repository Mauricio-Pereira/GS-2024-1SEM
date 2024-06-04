package org.fiap.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.fiap.entities.Company;
import org.fiap.entities.OrderItem;
import org.fiap.entities.User;
import org.fiap.repositories.OrderItemRepository;
import org.fiap.services.OrderItemService;
import org.fiap.utils.Log4jLogger;

import java.util.List;

@Path("bluehorizon")
public class OrderItemResource {
    private Log4jLogger<OrderItem> logger = new Log4jLogger<>(OrderItem.class);
    private OrderItemRepository orderItemRepository;
    private OrderItemService orderItemService;

    public OrderItemResource() {
        this.orderItemRepository = new OrderItemRepository();
        this.orderItemService = new OrderItemService();
    }

    @GET
    @Path("orderitems")
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.readAll();
    }

    @POST
    @Path("orderitems")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrderItem(OrderItem orderItem) {
        try {
            orderItemService.create(orderItem);
            logger.logCreate(orderItemRepository.readById(orderItem.getId()));
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("orderitems/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOrderItem(OrderItem orderItem, @PathParam("id") int id) {
        try {
            orderItemService.updateById(orderItem, id);
            logger.logUpdateById(orderItemRepository.readById(id));
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("orderitems/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderItemIdById(@PathParam("id") int id) {
        OrderItem orderItem = orderItemRepository.readById(id);
        if (orderItem != null) {
            logger.logReadById(orderItem);
            return Response.status(Response.Status.OK).entity(orderItem).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
    }

    @DELETE
    @Path("orderitems/{id}")
    public Response deleteOrderItem(@PathParam("id") int id) {
        if (orderItemRepository.readById(id) != null) {
            logger.logDeleteById(orderItemRepository.readById(id));
            orderItemRepository.deleteById(id);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
