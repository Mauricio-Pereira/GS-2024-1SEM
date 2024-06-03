package org.fiap.services;

import org.fiap.entities.OrderItem;
import org.fiap.repositories.OrderItemRepository;

public class OrderItemService {

    OrderItemRepository orderItemRepository = new OrderItemRepository();

    public OrderItemService(){
        OrderItemRepository orderItemRepository = new OrderItemRepository();
    }

    public boolean create(OrderItem orderItem){
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
        var validation = orderItem.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                orderItemRepository.updateById(orderItem, id);
            }
        } catch (Exception e) {
            throw e;
        }

    }
}
