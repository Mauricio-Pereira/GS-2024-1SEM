package org.fiap.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.fiap.entities.Company;
import org.fiap.repositories.CompanyRepository;
import org.fiap.services.CompanyService;
import org.fiap.utils.Log4jLogger;

import java.util.List;

@Path("bluehorizon")
public class CompanyResource {

    private Log4jLogger<Company> logger = new Log4jLogger<>(Company.class);
    private CompanyRepository companyRepository;
    private CompanyService companyService;

    public CompanyResource() {
        this.companyRepository = new CompanyRepository();
        this.companyService = new CompanyService();
    }

    @GET
    @Path("companies")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Company> getAllCompanies() {
        return companyRepository.readAll();
    }

    @POST
    @Path("companies/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCompany(@PathParam("id") int id, Company company) {
        try {
            companyService.create(company, id);
            logger.logCreate(companyRepository.readById(company.getId()));
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("companies/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCompany(@PathParam("id") int id, Company company) {
        try {
            companyService.updateById(company, id);
            logger.logUpdateById(companyRepository.readById(id));
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("companies/{id}")
    public Response deleteCompany(@PathParam("id") int id) {
        if (companyRepository.readById(id) != null) {
            logger.logDeleteById(companyRepository.readById(id));
            companyRepository.deleteById(id);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("companies/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompanyById(@PathParam("id") int id) {
        Company company = companyRepository.readById(id);
        if (company != null) {
            logger.logReadById(company);
            return Response.status(Response.Status.OK).entity(company).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
