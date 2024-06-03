package org.fiap.services;

import org.fiap.entities.Address;
import org.fiap.entities.User;
import org.fiap.repositories.UserRepository;

import java.time.LocalDate;
import java.util.Map;

public class UserService {

    private UserRepository userRepository = new UserRepository();
    private AddressService addressService = new AddressService();

    public UserService(){
        UserRepository userRepository = new UserRepository();
        AddressService addressService = new AddressService();
    }

    public boolean create(User user){
        var validation = user.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                userRepository.create(user);
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateById(User user, int id){
        var validation = user.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                userRepository.updateById(user, id);
            }
        } catch (Exception e) {
            throw e;
        }

    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        throw new IllegalArgumentException("Credenciais inválidas");
    }


    public void createUserWithAddress(Map<String, Object> payload) {
        // Criar o usuário
        User user = new User();
        user.setName((String) payload.get("name"));
        user.setLastName((String) payload.get("lastName"));
        user.setEmail((String) payload.get("email"));
        user.setPassword((String) payload.get("password"));
        user.setUserType((String) payload.get("userType"));
        user.setPhone((String) payload.get("phone"));
        user.setBirthDate(LocalDate.parse((String) payload.get("birthDate")));

        // Salvar o usuário no banco de dados
        userRepository.create(user);
        User createdUser = userRepository.findByEmail(user.getEmail());

        // Criar o endereço
        Address address = new Address();
        address.setZipCode((String) payload.get("cep"));
        address.setCountry((String) payload.get("country"));
        address.setState((String) payload.get("state"));
        address.setCity((String) payload.get("city"));
        address.setNeighborhood((String) payload.get("neighborhood"));
        address.setStreet((String) payload.get("street"));
        address.setNumber((String) payload.get("number"));
        address.setComplement((String) payload.get("complement"));
        address.setUser(createdUser); // Associar o endereço ao usuário criado

        // Salvar o endereço no banco de dados
        addressService.create(address, createdUser.getId());
    }
}
