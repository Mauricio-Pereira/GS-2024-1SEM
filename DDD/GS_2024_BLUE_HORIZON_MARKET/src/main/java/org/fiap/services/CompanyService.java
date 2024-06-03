package org.fiap.services;

import org.fiap.entities.Company;
import org.fiap.repositories.CompanyRepository;

public class CompanyService {

    CompanyRepository companyRepository = new CompanyRepository();

    public CompanyService(){
        CompanyRepository companyRepository = new CompanyRepository();
    }

    public boolean create(Company company, int id){
        var validation = company.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                companyRepository.create(company, id);
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateById(Company company, int id){
        var validation = company.validate();
        try {
            if(validation.containsKey(false)) {
                throw new IllegalArgumentException(validation.get(false).toString());
            } else {
                companyRepository.updateById(company, id);
            }
        } catch (Exception e) {
            throw e;
        }

    }

}
