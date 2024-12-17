package com.onlineShop.models.Users.EndUserEntities;

import com.onlineShop.models.Product.Product;
import com.onlineShop.models.Users.EndUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikedProduct {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="id_user")
    private EndUser user;

    @NotNull
    @ManyToOne
    @JoinColumn(name="id_product")
    private Product product;

}
