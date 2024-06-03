package org.fiap.services;

import org.fiap.entities.Order;
import org.fiap.repositories.OrderRepository;

public class OrderService {

    OrderRepository orderRepository = new OrderRepository();

    public OrderService(){
    }

    public boolean create(Order order, int id){
        var validation = order.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                orderRepository.create(order);
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateById(Order order, int id){
        var validation = order.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                orderRepository.updateById(order, id);
            }
        } catch (Exception e) {
            throw e;
        }

    }
}
