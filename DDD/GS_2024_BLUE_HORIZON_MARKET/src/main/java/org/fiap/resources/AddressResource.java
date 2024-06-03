package org.fiap.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.fiap.entities.Address;
import org.fiap.entities.User;
import org.fiap.repositories.AddressRepository;
import org.fiap.services.AddressService;
import org.fiap.utils.Log4jLogger;

import java.util.List;

@Path("bluehorizon")
public class AddressResource {

    private Log4jLogger<Address> logger = new Log4jLogger<>(Address.class);
    private AddressService addressService;
    private AddressRepository addressRepository;

    public AddressResource() {
        this.addressService = new AddressService();
        this.addressRepository = new AddressRepository();
    }

    @GET
    @Path("addresses")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Address> getAllCompanies() {
        return addressRepository.readAll();
    }
    @POST
    @Path("addresses/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAddress(Address address, @PathParam("id") int id) {
        try {
            addressService.create(address, id);
            logger.logCreate(addressRepository.readById(address.getId()));
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("addresses/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAddress(@PathParam("id") int id, Address address) {
        try {
            addressService.updateById(address, id);
            logger.logUpdateById(addressRepository.readById(id));
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("addresses/{id}")
    public Response deleteAddress(@PathParam("id") int id) {
        if (addressRepository.readById(id) != null) {
            logger.logDeleteById(addressRepository.readById(id));
            addressRepository.deleteById(id);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("addresses/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddressById(@PathParam("id") int id) {
        Address address = addressRepository.readById(id);
        if (address != null) {
            logger.logReadById(address);
            return Response.status(Response.Status.OK).entity(address).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
