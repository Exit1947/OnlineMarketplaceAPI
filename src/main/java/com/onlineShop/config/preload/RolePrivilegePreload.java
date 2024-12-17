package com.onlineShop.config.preload;

import com.onlineShop.models.Users.RolePrivilege.Privilege;
import com.onlineShop.models.Users.RolePrivilege.PrivilegeType;
import com.onlineShop.models.Users.RolePrivilege.Role;
import com.onlineShop.models.Users.RolePrivilege.RoleType;
import com.onlineShop.service.PrivilegeService;
import com.onlineShop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RolePrivilegePreload {

    public final Role ADMIN;
    public final Role MODERATOR;
    public final Role SALES_REP;
    public final Role END_USER;

    public final List<Privilege> baseRegisterPrivilege;
    public final List<Role> listOfAllRoles;
    public final List<Privilege> listOfAllPrivileges;

    @Autowired
    public RolePrivilegePreload(RoleService roleService, PrivilegeService privilegeService) {
        listOfAllRoles = roleService.getAll();
        listOfAllPrivileges = privilegeService.getAll();
        baseRegisterPrivilege = baseRegisterPrivilege();

        ADMIN = findByTypeOfRole(RoleType.ADMIN);
        MODERATOR = findByTypeOfRole(RoleType.MODERATOR);
        SALES_REP = findByTypeOfRole(RoleType.SALES_REP);
        END_USER = findByTypeOfRole(RoleType.END_USER);
    }

    public Optional<Role> getRole(int id){
        return listOfAllRoles
                .stream()
                .filter(role -> role.getId() == id)
                .findFirst();
    }

    public Optional<Privilege> getPrivilege(int id){
        return listOfAllPrivileges
                .stream()
                .filter(role -> role.getId() == id)
                .findFirst();
    }

    private Role findByTypeOfRole(RoleType type){
        return listOfAllRoles.stream()
                .filter(role -> role.getType().equals(type))
                .findFirst().orElseGet(null);
    }

    private List<Privilege> baseRegisterPrivilege(){
        List<PrivilegeType> privilegeTypes = baseEndUserPrivilegeType();

        return listOfAllPrivileges.stream()
                .filter(priv -> privilegeTypes.contains(priv.getType()))
                .toList();
    }

    private List<PrivilegeType> baseEndUserPrivilegeType(){
        return List.of(PrivilegeType.ROLE_END_USER, PrivilegeType.BUY_PRODUCT);
    }

}
