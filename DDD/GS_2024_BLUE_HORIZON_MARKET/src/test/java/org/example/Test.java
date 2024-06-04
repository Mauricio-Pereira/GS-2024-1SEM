package org.example;

import org.fiap.entities.*;
import org.fiap.repositories.*;
import org.fiap.services.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        UserRepository userRepository = new UserRepository();
        AddressRepository addressRepository = new AddressRepository();
        CompanyRepository companyRepository = new CompanyRepository();
        ProductRepository productRepository = new ProductRepository();
        OrderRepository orderRepository = new OrderRepository();
        OrderItemRepository orderItemRepository = new OrderItemRepository();
        NgoRepository ngoRepository = new NgoRepository();


        User user = new User("John", "Doe", "john2@email.com", "Senha@123", "admin_company", "11 99999-9999", LocalDate.of(1990, 1, 1));
        User user2 = new User("Maria", "Doe", "maria@email.com", "Senha@123", "buyer", "11 99999-9999", LocalDate.of(1990, 1, 1));
        User user3 = new User("Joao", "Doe", "joao@email.com", "Senha@123", "buyer", "11 99999-9999", LocalDate.of(1990, 1, 1));
        User user4 = new User("Jose", "Doe", "jose@email.com", "Senha@123", "admin_ngo", "11 99999-9999", LocalDate.of(1990, 1, 1));
        userRepository.create(user);
        userRepository.create(user2);
        userRepository.create(user3);
        userRepository.create(user4);

        Ngo ngo = new Ngo(userRepository.readById(4), "UNESCO", "Make the world a better place", "https://www.ngo.com");
        Ngo ngo2 = new Ngo(userRepository.readById(4), "UNESCO2", "Make the world a better place", "https://www.ngo.com");
        Ngo ngo3 = new Ngo(userRepository.readById(4), "UNESCO3", "Make the world a better place", "https://www.ngo.com");
        ngoRepository.create(ngo, 4);
        ngoRepository.create(ngo2, 4);
        ngoRepository.create(ngo3, 4);

        Company company = new Company( "Company Name", "12345678000190", "11 99999-9999", "https://www.company.com", null, 1);
        Company company2 = new Company( "Company Name2", "12345678000190", "11 99999-9999", "https://www.company.com", null, 1);
        companyRepository.create(company, 1);
        companyRepository.create(company2, 1);

        Address address = new Address("12345678", "Brasil",
                "SP", "São Paulo", "Vila Olimpia",
                "Rua Funchal", "123", "Apto 123", userRepository.readById(1));
        Address address2 = new Address("12345678", "Brasil",
                "SP", "São Paulo", "Vila Olimpia",
                "Rua Funchal 2", "1234", "Apto 123", userRepository.readById(1));
        addressRepository.create(address,1);
        addressRepository.create(address2,1 );

        Product product = new Product("Product Name", "Product Description", 100.0, 100, company);
        Product product2 = new Product("Product Name 2", "Product Description", 200.0, 200, company);
        productRepository.create(product,2);
        productRepository.create(product2,2);

        Order order = new Order (3, "pending");
        Order order2 = new Order (3, "paid");
        orderRepository.create(order, order.getBuyer().getId());
        orderRepository.create(order2, order2.getBuyer().getId());

        OrderItem item1 = new OrderItem(1, 1, 2);
        OrderItem item2 = new OrderItem( 2, 2, 3);
        OrderItem item3 = new OrderItem(2, 2, 5);
        orderItemRepository.create(item1);
        orderItemRepository.create(item2);
        orderItemRepository.create(item3);

        orderRepository.deleteById(1);


        //TESTS USERS
        userRepository.readById(1);
        user.setName("John Updated");
        user.setLastName("Doe Updated");
        userRepository.updateById(user,1);
        userRepository.deleteById(2);
        userRepository.readAll();

        //TESTS ADDRESSES
        addressRepository.readAll();
        addressRepository.readById(2);
        address2.setStreet("Rua Funchal Updated");
        addressRepository.updateById(address2, 2);
        addressRepository.deleteById(1);


        //TEST COMPANIES
        company.setName("Company Name Updated");
        companyRepository.updateById(company,1);
        companyRepository.readById(1);
        companyRepository.deleteById(1);
        companyRepository.readAll();

        //TESTS PRODUCTS
        productRepository.readAll();
        productRepository.readById(1);
        product.setName("Product Name Updated");
        productRepository.updateById(product,1);




        //TESTS ORDERS
        orderRepository.readAll();
        orderRepository.readById(order.getId());
        order.setOrderStatus("paid");
        orderRepository.updateById(order, 2);
        orderRepository.deleteById(1);

        //TESTS ORDERITEMS
        orderItemRepository.readAll();
        orderItemRepository.readById(1);
        item1.setQuantity(3);
        orderItemRepository.updateById(item1, 1);
        //orderItemRepository.deleteById(2);


        //productRepository.deleteById(2);


        //TESTS NGOS
        ngoRepository.readAll();
        ngoRepository.readById(1);
        ngo2.setName("UNESCO Updated");
        ngoRepository.updateById(ngo2,2);
        ngoRepository.deleteById(1);
    }
}
