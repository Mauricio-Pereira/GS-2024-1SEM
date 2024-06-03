package org.fiap.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.fiap.entities.Product;
import org.fiap.repositories.ProductRepository;
import org.fiap.services.ProductService;
import org.fiap.utils.Log4jLogger;
import org.fiap.utils.Logger;

import java.util.List;

@Path("bluehorizon")
public class ProductResource {
    private Log4jLogger<Product> logger = new Log4jLogger<>(Product.class);
    private ProductRepository productRepository;
    private ProductService productService;

    public ProductResource() {
        this.productRepository = new ProductRepository();
        this.productService = new ProductService();
    }

    @GET
    @Path("products")
    public List<Product> getAllProducts() {
        return productRepository.readAll();
    }

    @POST
    @Path("products/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduct(Product product, @PathParam("id") int id) {
        try {
            productService.create(product, id);
            logger.logCreate(productRepository.readById(product.getId()));
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("products/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") int id, Product product) {
        try {
            productService.updateById(product, id);
            logger.logUpdateById(productRepository.readById(id));
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("products/{id}")
    public Response deleteProduct(@PathParam("id") int id) {
        if (productRepository.readById(id) != null) {
            logger.logDeleteById(productRepository.readById(id));
            productRepository.deleteById(id);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@PathParam("id") int id) {
        Product product = productRepository.readById(id);
        if (product != null) {
            logger.logReadById(product);
            return Response.status(Response.Status.OK).entity(product).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
