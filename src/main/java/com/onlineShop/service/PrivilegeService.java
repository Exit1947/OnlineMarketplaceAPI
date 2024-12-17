package com.onlineShop.service;

import com.onlineShop.models.Users.RolePrivilege.Privilege;
import com.onlineShop.models.Users.RolePrivilege.PrivilegeType;
import com.onlineShop.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    @Autowired
    public PrivilegeService(final PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    public Optional<Privilege> findById(final int id){
        return privilegeRepository.findById(id);
    }

    public Optional<Privilege> findByText(final String type) {
        return privilegeRepository.findByType(type);
    }

    public List<Privilege> getAll(){
        List<Privilege> privilegeList = privilegeRepository.findAll();
        if(privilegeList.isEmpty()){
            for(PrivilegeType privilege : PrivilegeType.values()){
                privilegeList.add(new Privilege(privilege));
            }
            privilegeRepository.saveAll(privilegeList);
        }
        return privilegeList;
    }

}
