package com.onlineShop.models.Users.Staff;

import com.onlineShop.models.Shop.Shop;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffList {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_staff")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "id_shop")
    private Shop shop;

}
