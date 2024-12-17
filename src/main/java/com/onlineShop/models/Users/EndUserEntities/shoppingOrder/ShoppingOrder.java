package com.onlineShop.models.Users.EndUserEntities.shoppingOrder;

import com.onlineShop.models.Users.EndUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingOrder {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_end_user")
    private EndUser endUser;

    @NotNull
    @Column(name = "shopping_order_created_at")
    private Date orderCreatedAt;

    @NotNull
    @OneToMany(mappedBy = "shoppingOrder")
    List<OrderedProducts> orderedProductsList;

}
