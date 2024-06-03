package org.fiap.services;

import org.fiap.entities.Donation;
import org.fiap.repositories.DonationRepository;

public class DonationService {
        DonationRepository donationRepository = new DonationRepository();
        public DonationService(){
            DonationRepository donationRepository = new DonationRepository();
        }

        public boolean create(Donation donation){
            var validation = donation.validate();
            try {
                if(validation.containsKey(false)) {
                    throw new IllegalArgumentException(validation.get(false).toString());
                } else {
                    donationRepository.create(donation);
                    return true;
                }
            } catch (Exception e) {
                throw e;
            }
        }

        public void updateById(Donation donation, int id){
            var validation = donation.validate();
            try {
                if(validation.containsKey(false)) {
                    throw new IllegalArgumentException(validation.get(false).toString());
                } else {
                    donationRepository.updateById(donation, id);
                }
            } catch (Exception e) {
                throw e;
            }

        }
}
