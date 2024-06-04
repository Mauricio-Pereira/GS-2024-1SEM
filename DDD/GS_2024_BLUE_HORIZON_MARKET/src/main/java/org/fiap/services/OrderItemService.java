package org.fiap.services;

import org.fiap.entities.OrderItem;
import org.fiap.entities.Product;
import org.fiap.repositories.OrderItemRepository;
import org.fiap.repositories.ProductRepository;

public class OrderItemService {

    OrderItemRepository orderItemRepository = new OrderItemRepository();

    public OrderItemService(){
        OrderItemRepository orderItemRepository = new OrderItemRepository();
    }

    public boolean create(OrderItem orderItem){
        orderItem.setProduct(new ProductRepository().readById(orderItem.getProduct().getId()));
        orderItem.setPrice(orderItem.getProduct().getPrice() * orderItem.getQuantity());
        var validation = orderItem.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                orderItemRepository.create(orderItem);
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateById(OrderItem orderItem, int id){
        orderItem.setProduct(new ProductRepository().readById(orderItem.getProduct().getId()));
        orderItem.setOrder(orderItemRepository.readById(id).getOrder());
        orderItem.setPrice(orderItem.getProduct().getPrice() * orderItem.getQuantity());
        var validation = orderItem.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                orderItemRepository.updateById(orderItem, id, orderItem.getOrder().getId(), orderItem.getProduct().getId());
            }
        } catch (Exception e) {
            throw e;
        }

    }
}
