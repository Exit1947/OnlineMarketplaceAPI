package com.onlineShop.models.Users.EndUserEntities.cart;

import com.onlineShop.models.Users.EndUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_end_user")
    private EndUser endUser;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

}
