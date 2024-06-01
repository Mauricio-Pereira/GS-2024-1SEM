package org.example;

import org.fiap.entities.User;
import org.fiap.repositories.UserRepository;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();

        // Cria um usuário
        User user = new User("John", "Doe", "john@email.com", "Senha@123", "buyer", "11 99999-9999", LocalDate.of(1990, 1, 1));
        User user2 = new User("Maria", "Doe", "maria@email.com", "Senha@123", "buyer", "11 99999-9999", LocalDate.of(1990, 1, 1));
       userRepository.create(user);
         userRepository.create(user2);

        // Lê o usuário pelo ID
        System.out.println("User retrieved by ID: " + userRepository.readById(1));

        // Atualiza o usuário
        user.setName("John Updated");
        user.setLastName("Doe Updated");
        userRepository.updateById(user,1);

        // Lê o usuário atualizado
        User updatedUser = userRepository.readById(1);

        // Remove o usuário
        userRepository.deleteById(1);

        // Tenta ler o usuário removido
        User deletedUser = userRepository.readById(1);
        if (deletedUser == null) {
            System.out.println("User not found after deletion");
        } else {
            System.out.println("User retrieved after deletion: " + deletedUser);
        }
    }
}
