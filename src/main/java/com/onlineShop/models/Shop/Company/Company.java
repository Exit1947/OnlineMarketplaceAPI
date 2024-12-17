package com.onlineShop.models.Shop.Company;

import com.onlineShop.models.Users.Staff.SalesRep;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name of company can't be empty")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Image of company can't be empty")
    @Column(name = "image")
    private String image;

    @NonNull
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    private List<SalesRep> salesRepList = new ArrayList<>();
}
