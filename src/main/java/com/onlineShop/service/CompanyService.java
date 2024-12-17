package com.onlineShop.service;

import com.onlineShop.models.Shop.Company.Company;
import com.onlineShop.models.Shop.Shop;

import java.util.Optional;

public interface CompanyService {

    void save(Company company);

    Optional<Company> getById(long id);

    Optional<Company> getByName(String street);

    void delete(long id);

}
