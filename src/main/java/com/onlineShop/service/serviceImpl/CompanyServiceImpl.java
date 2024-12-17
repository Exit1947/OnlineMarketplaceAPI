package com.onlineShop.service.serviceImpl;

import com.onlineShop.models.Shop.Company.Company;
import com.onlineShop.models.Shop.Shop;
import com.onlineShop.repository.CompanyRepository;
import com.onlineShop.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    @Override
    public void save(Company company) {
        companyRepository.save(company);
    }

    @Override
    public Optional<Company> getById(long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Optional<Company> getByName(String name) {
        return companyRepository.findByName(name);
    }

    @Override
    public void delete(long id) {
        companyRepository.deleteById(id);
    }

}
