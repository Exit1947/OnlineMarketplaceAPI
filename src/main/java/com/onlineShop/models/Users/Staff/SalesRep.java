package com.onlineShop.models.Users.Staff;

import com.onlineShop.models.Shop.Company.Company;
import com.onlineShop.models.Shop.Shop;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesRep extends Staff {

    @NotNull(message = "Shop can't be empty")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_shop")
    private Shop shop;

    @NotNull(message = "Company can't be empty")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_company")
    private Company company;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_admin")
    private Admin admin;

}
