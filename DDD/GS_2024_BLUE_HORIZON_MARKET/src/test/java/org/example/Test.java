package org.example;

import org.fiap.entities.Address;
import org.fiap.entities.User;
import org.fiap.repositories.AddressRepository;
import org.fiap.repositories.UserRepository;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        AddressRepository addressRepository = new AddressRepository();
        // Cria um usuário
        User user = new User("John", "Doe", "john@email.com", "Senha@123", "buyer", "11 99999-9999", LocalDate.of(1990, 1, 1));
        User user2 = new User("Maria", "Doe", "maria@email.com", "Senha@123", "buyer", "11 99999-9999", LocalDate.of(1990, 1, 1));
       userRepository.create(user);
       userRepository.create(user2);/*
        // Lê o usuário pelo ID
        userRepository.readById(1);

        // Atualiza o usuário
        user.setName("John Updated");
        user.setLastName("Doe Updated");
        userRepository.updateById(user,1);

        // Lê o usuário atualizado
        User updatedUser = userRepository.readById(1);

        // Remove o usuário
        userRepository.deleteById(1);

        System.out.println(userRepository.readAll());
        */

        addressRepository.create(new Address("12345678", "Brasil",
                "SP", "São Paulo", "Vila Olimpia",
                "Rua Funchal", "123", "Apto 123", userRepository.readById(1)), 1);
        addressRepository.create(new Address("12345678", "Brasil",
                "SP", "São Paulo", "Vila Olimpia",
                "Rua Funchal 2", "1234", "Apto 123", userRepository.readById(1)), 2);

        System.out.println(addressRepository.readAll());
        addressRepository.readById(2);

        addressRepository.updateById(new Address(2, "Rua Funchal Updated", "123", "Apto 123", "Vila Olimpia", "São Paulo", "SP", "Brasil", "12345678"), 2);
        addressRepository.deleteById(2);
        System.out.println(addressRepository.readAll() );
    }
}
