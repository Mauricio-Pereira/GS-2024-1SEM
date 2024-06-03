package org.fiap.services;

import org.fiap.entities.Product;
import org.fiap.repositories.ProductRepository;

public class ProductService {

    ProductRepository productRepository = new ProductRepository();

    public ProductService(){
        ProductRepository productRepository = new ProductRepository();
    }

    public boolean create(Product product, int id){
        var validation = product.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                productRepository.create(product, id);
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateById(Product product, int id){
        var validation = product.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                productRepository.updateById(product, id);
            }
        } catch (Exception e) {
            throw e;
        }

    }
}
