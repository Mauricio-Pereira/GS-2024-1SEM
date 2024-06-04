package org.fiap.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.fiap.entities.Order;
import org.fiap.repositories.OrderRepository;
import org.fiap.services.OrderService;
import org.fiap.utils.Log4jLogger;

import java.util.List;

@Path("bluehorizon")
public class OrderResource {

    Log4jLogger<Order> logger = new Log4jLogger<>(Order.class);
    private OrderRepository orderRepository;
    private OrderService orderService;

    public OrderResource() {
        this.orderRepository = new OrderRepository();
        this.orderService = new OrderService();
    }

    @GET
    @Path("orders")
    public List<Order> getAllOrders() {
        return orderRepository.readAll();
    }

    @POST
    @Path("orders/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrderItem(Order order, @PathParam("id") int id) {
        try {
            orderService.create(order, id);
            logger.logCreate(orderRepository.readById(order.getId()));
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("orders/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOrderItem(Order order, @PathParam("id") int id) {
        try {
            orderService.updateById(order, id);
            logger.logUpdateById(orderRepository.readById(id));
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("orders/{id}")
    public Response deleteOrder(@PathParam("id") int id) {
        if (orderRepository.readById(id) != null
                && orderRepository.deleteById(id)) {
            logger.logDeleteById(orderRepository.readById(id));
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @GET
    @Path("orders/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(@PathParam("id") int id) {
        Order order = orderRepository.readById(id);
        if (order != null) {
            logger.logReadById(order);
            return Response.status(Response.Status.OK).entity(order).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
