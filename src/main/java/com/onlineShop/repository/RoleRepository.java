package com.onlineShop.repository;

import com.onlineShop.models.Users.RolePrivilege.Role;
import com.onlineShop.models.Users.RolePrivilege.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findById(int id);

    Optional<Role> findByType(RoleType type);

    List<Role> findAll();
}
