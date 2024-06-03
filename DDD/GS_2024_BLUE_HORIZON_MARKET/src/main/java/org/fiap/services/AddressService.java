package org.fiap.services;

import org.fiap.entities.Address;
import org.fiap.repositories.AddressRepository;

public class AddressService {
    private AddressRepository addressRepository = new AddressRepository();
    public AddressService(){
        AddressRepository addressRepository = new AddressRepository();
    }

    public boolean create(Address address, int id){
        var validation = address.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                addressRepository.create(address, id);
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateById(Address address, int id){
        var validation = address.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                addressRepository.updateById(address, id);
            }
        } catch (Exception e) {
            throw e;
        }

    }
}
