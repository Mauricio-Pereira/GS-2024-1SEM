package org.fiap.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.fiap.entities.Ngo;
import org.fiap.repositories.NgoRepository;
import org.fiap.services.NgoService;
import org.fiap.utils.Log4jLogger;

import java.util.List;

@Path("bluehorizon")
public class NgoResource {

    Log4jLogger<Ngo> logger = new Log4jLogger<>(Ngo.class);
    private NgoRepository ngoRepository;
    private NgoService ngoService;

    public NgoResource() {
        this.ngoRepository = new NgoRepository();
        this.ngoService = new NgoService();
    }

    @GET
    @Path("ngos")
    public List<Ngo> getAllNgos() {
        return ngoRepository.readAll();
    }

    @GET
    @Path("ngos/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNgoById(@PathParam("id") int id) {
        Ngo ngo = ngoRepository.readById(id);
        if (ngo != null) {
            logger.logReadById(ngo);
            return Response.status(Response.Status.OK).entity(ngo).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("ngos/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNgo(Ngo ngo, @PathParam("id") int id) {
        try {
            ngoService.create(ngo, id);
            logger.logCreate(ngoRepository.readById(ngo.getId()));
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("ngos/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateNgo(@PathParam("id") int id, Ngo ngo) {
        try {
            ngoService.updateById(ngo, id);
            logger.logUpdateById(ngoRepository.readById(id));
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("ngos/{id}")
    public Response deleteNgo(@PathParam("id") int id) {
        if (ngoRepository.readById(id) != null) {
            logger.logDeleteById(ngoRepository.readById(id));
            ngoRepository.deleteById(id);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
