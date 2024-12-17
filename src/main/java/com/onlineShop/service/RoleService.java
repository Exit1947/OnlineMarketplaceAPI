package com.onlineShop.service;

import com.onlineShop.models.Users.RolePrivilege.Role;
import com.onlineShop.repository.RoleRepository;
import com.onlineShop.models.Users.RolePrivilege.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findByType(final RoleType type) {
        return roleRepository.findByType(type);
    }

    public Optional<Role> findById(final int id) {
        return roleRepository.findById(id);
    }

    public List<Role> getAll() {
        List<Role> roleList = roleRepository.findAll();
        if(roleList.isEmpty()){
            for(RoleType role : RoleType.values()){
                roleList.add(new Role(role));
            }
            roleRepository.saveAll(roleList);
        }
        return roleList;
    }

}
