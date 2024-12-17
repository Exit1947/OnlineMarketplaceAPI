package com.onlineShop.repository;

import com.onlineShop.models.Users.Staff.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {}
