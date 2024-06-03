package org.fiap.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.fiap.entities.User;
import org.fiap.repositories.UserRepository;
import org.fiap.services.UserService;
import org.fiap.utils.Log4jLogger;

import java.util.List;
import java.util.Map;

@Path("bluehorizon")
public class UserResources {

    private Log4jLogger<User> logger = new Log4jLogger<>(User.class);
    private UserRepository userRepository;
    private UserService userService;

    public UserResources() {
        this.userRepository = new UserRepository();
        this.userService = new UserService();
    }

    @GET
    @Path("users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllClientes() {
        return userRepository.readAll();
    }

    @POST
    @Path("users")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUserWithAddress(Map<String, Object> payload) {
        try {
            userService.createUserWithAddress(payload);
            logger.logCreate(userRepository.findByEmail((String) payload.get("email")));
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, User user) {
        try {
            userService.updateById(user, id);
            logger.logUpdateById(userRepository.readById(id));
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("users/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        if (userRepository.readById(id) != null) {
            logger.logDeleteById(userRepository.readById(id));
            userRepository.deleteById(id);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id) {
        User user = userRepository.readById(id);
        if (user != null) {
            logger.logReadById(user);
            return Response.status(Response.Status.OK).entity(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User loginRequest) {
        try {
            User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            logger.logReadById(user);
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("users/id")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserIdByEmail(@QueryParam("email") String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return Response.status(Response.Status.OK).entity(user.getId()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
    }



}
