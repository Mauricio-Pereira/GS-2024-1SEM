package org.fiap.services;

import org.fiap.entities.Ngo;
import org.fiap.repositories.NgoRepository;

public class NgoService {
    NgoRepository ngoRepository = new NgoRepository();

    public NgoService(){
        NgoRepository ngoRepository = new NgoRepository();
    }

    public boolean create(Ngo ngo, int id){
        var validation = ngo.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                ngoRepository.create(ngo);
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateById(Ngo ngo, int id){
        var validation = ngo.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                ngoRepository.updateById(ngo, id);
            }
        } catch (Exception e) {
            throw e;
        }

    }
}
