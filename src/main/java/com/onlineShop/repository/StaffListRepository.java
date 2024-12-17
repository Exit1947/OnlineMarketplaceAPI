package com.onlineShop.repository;

import com.onlineShop.models.Users.Staff.StaffList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffListRepository extends JpaRepository<StaffList, String> {

    void deleteStaffListByStaffId(String staffId);

    void deleteStaffListByShopId(long shopId);

}
