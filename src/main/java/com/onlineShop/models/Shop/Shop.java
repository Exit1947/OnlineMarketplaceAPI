package com.onlineShop.models.Shop;

import com.onlineShop.models.Users.Staff.Staff;
import com.onlineShop.models.Users.Staff.StaffList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shop {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "City of shop can't be empty")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "Street of shop can't be empty")
    @Column(name = "street")
    private String street;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop")
    private List<StaffList> staff;

}
