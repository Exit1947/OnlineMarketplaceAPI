package com.onlineShop.converter.users;

import com.onlineShop.dto.user.privilege.PrivilegeResponse;
import com.onlineShop.dto.user.role.RoleResponse;
import com.onlineShop.dto.user.userEntity.admin.AdminRequest;
import com.onlineShop.dto.user.userEntity.admin.AdminResponse;
import com.onlineShop.dto.user.userEntity.endUser.EndUserRequest;
import com.onlineShop.dto.user.userEntity.endUser.EndUserResponse;
import com.onlineShop.dto.user.userEntity.moderator.ModeratorRequest;
import com.onlineShop.dto.user.userEntity.moderator.ModeratorResponse;
import com.onlineShop.dto.user.userEntity.salesRep.SalesRepRequest;
import com.onlineShop.dto.user.userEntity.salesRep.SalesRepResponse;
import com.onlineShop.dto.user.userEntity.staff.StaffRequest;
import com.onlineShop.dto.user.userEntity.staff.StaffResponse;
import com.onlineShop.dto.user.userEntity.userEntity.UserEntityRequest;
import com.onlineShop.dto.user.userEntity.userEntity.UserEntityResponse;
import com.onlineShop.models.Shop.Company.Company;
import com.onlineShop.models.Shop.Shop;
import com.onlineShop.models.Users.EndUser;
import com.onlineShop.models.Users.RolePrivilege.Privilege;
import com.onlineShop.models.Users.RolePrivilege.Role;
import com.onlineShop.models.Users.Staff.*;
import com.onlineShop.models.Users.UserEntity;

public class UserEntityConverter {

    public static Admin toAdmin(final AdminRequest adminRequest) {
        Admin admin = new Admin();
        toStaffEntity(admin, adminRequest);
        return admin;
    }

    public static Moderator toModerator(final ModeratorRequest moderatorRequest) {
        Moderator moderator = new Moderator();
        toStaffEntity(moderator, moderatorRequest);

        return moderator;
    }

    public static SalesRep toSalesRep(final SalesRepRequest salesRepRequest) {
        SalesRep salesRep = new SalesRep();
        toStaffEntity(salesRep, salesRepRequest);

        return salesRep;
    }

    public static EndUser toEndUser(final EndUserRequest endUserRequest){
        EndUser endUser = new EndUser();
        toUserEntity(endUser, endUserRequest);

        endUser.setFirstName(endUserRequest.getFirstName());
        endUser.setLastName(endUserRequest.getLastName());

        return endUser;
    }

    public static AdminResponse toAdminResponse(Admin admin) {
        AdminResponse adminResponse = new AdminResponse();
        toStaffResponse(adminResponse, admin);
        return adminResponse;
    }

    public static ModeratorResponse toModeratorResponse(Moderator moderator) {
        ModeratorResponse moderatorResponse = new ModeratorResponse();
        toStaffResponse(moderatorResponse, moderator);
        moderatorResponse.setShopId(moderator.getShop().getId());
        moderatorResponse.setAdminId(moderator.getAdmin().getId());

        return moderatorResponse;
    }

    public static SalesRepResponse toSalesRepResponse(SalesRep salesRep) {
        SalesRepResponse salesRepResponse = new SalesRepResponse();
        toStaffResponse(salesRepResponse, salesRep);
        salesRepResponse.setShopId(salesRep.getShop().getId());
        salesRepResponse.setAdminId(salesRep.getAdmin().getId());
        salesRepResponse.setCompanyId(salesRep.getCompany().getId());

        return salesRepResponse;
    }

    public static EndUserResponse toEndUserResponse(EndUser endUser) {
        EndUserResponse endUserResponse = new EndUserResponse();
        toUserEntityResponse(endUserResponse, endUser);
        endUserResponse.setFirstName(endUser.getFirstName());
        endUserResponse.setLastName(endUser.getLastName());

        return endUserResponse;
    }

    public static RoleResponse toRoleResponse(Role role) {
        return new RoleResponse(role.getId(), role.getType().toString());
    }

    public static PrivilegeResponse toPrivilegeResponse(Privilege privilege) {
        return new PrivilegeResponse(privilege.getId(), privilege.getType().toString());
    }

    private static void toStaffEntity(Staff staff, StaffRequest staffRequest){
        toUserEntity(staff, staffRequest);
        staff.setFirstName(staffRequest.getFirstName());
        staff.setLastName(staffRequest.getLastName());
        staff.setShops(staffRequest.getShops().stream()
                .map(id -> {
                    Shop shop = new Shop();
                    shop.setId(id);

                    StaffList staffList = new StaffList();
                    staffList.setStaff(staff);
                    staffList.setShop(shop);
                    return staffList;})
                .toList());
    }

    private static void toUserEntity(UserEntity userEntity, UserEntityRequest userEntityRequest){
        userEntity.setId(userEntityRequest.getId());
        userEntity.setEmail(userEntityRequest.getEmail());
        userEntity.setLogin(userEntityRequest.getLogin());
        userEntity.setPhoneNumber(userEntityRequest.getPhoneNumber());
        userEntity.setPassword(userEntityRequest.getPassword());
        userEntity.setCreationDate(userEntityRequest.getCreationDate());
    }

    private static void toStaffResponse(StaffResponse staffResponse, Staff staff) {
        toUserEntityResponse(staffResponse, staff);
        staffResponse.setFirstName(staff.getFirstName());
        staffResponse.setLastName(staff.getLastName());
        staffResponse.setShopIds(staff.getShops().stream()
                .map(staffList -> staffList.getShop().getId())
                .toList());
    }

    public static void toUserEntityResponse(UserEntityResponse userEntityResponse, UserEntity userEntity) {
        userEntityResponse.setId(userEntity.getId());
        userEntityResponse.setEmail(userEntity.getEmail());
        userEntityResponse.setLogin(userEntity.getLogin());
        userEntityResponse.setPhoneNumber(userEntity.getPhoneNumber());
        userEntityResponse.setCreationDate(userEntity.getCreationDate());
        userEntityResponse.setRole(toRoleResponse(userEntity.getRole()));
        userEntityResponse.setPrivileges(userEntity.getPrivileges().stream()
                .map(privilege -> toPrivilegeResponse(privilege.getPrivilege())).toList());
    }

}