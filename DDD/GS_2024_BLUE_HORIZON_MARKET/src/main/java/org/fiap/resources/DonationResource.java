package org.fiap.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.fiap.entities.Donation;
import org.fiap.entities.Ngo;
import org.fiap.repositories.DonationRepository;
import org.fiap.services.DonationService;
import org.fiap.utils.Log4jLogger;

import java.util.List;

@Path("bluehorizon")
public class DonationResource {

    Log4jLogger<Donation> logger = new Log4jLogger<>(Donation.class);
    private DonationRepository donationRepository;
    private DonationService donationService;

    public DonationResource() {
        this.donationRepository = new DonationRepository();
        this.donationService = new DonationService();
    }

    @GET
    @Path("donations")
    public List<Donation> getAllDonations() {
        return donationRepository.readAll();
    }

    @GET
    @Path("donations/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDonationById(@PathParam("id") int id) {
        Donation donation = donationRepository.readById(id);
        if (donation != null) {
            logger.logReadById(donation);
            return Response.status(Response.Status.OK).entity(donation).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("donations")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDonation(Donation donation) {
        try {
            donationService.create(donation);
            logger.logCreate(donationRepository.readById(donation.getId()));
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("donations/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateNgo(@PathParam("id") int id, Donation donation) {
        try {
            donationService.updateById(donation, id);
            logger.logUpdateById(donationRepository.readById(id));
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("donations/{id}")
    public Response deleteNgo(@PathParam("id") int id) {
        if (donationRepository.readById(id) != null) {
            logger.logDeleteById(donationRepository.readById(id));
            donationRepository.deleteById(id);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
