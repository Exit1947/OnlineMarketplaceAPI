package com.onlineShop.repository;

import com.onlineShop.models.Product.Characteristic.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Integer> {

    Optional<Characteristic> findById(int id);

    Optional<Characteristic> findByName(String name);

    boolean existsByName(String name);

}
