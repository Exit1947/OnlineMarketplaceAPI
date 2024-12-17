package com.onlineShop.models.Users;

import com.onlineShop.models.Users.EndUserEntities.cart.Cart;
import com.onlineShop.models.Users.EndUserEntities.shoppingOrder.ShoppingOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EndUser extends UserEntity {

    @NotBlank(message = "First name can't be empty")
    @Column(name="first_name")
    @Length(min = 3, max = 50, message = "Firstname must be between 3 and 50 characters")
    private String firstName;

    @Column(name="last_name")
    @Length(min = 3, max = 50, message = "Lastname must be between 3 and 50 characters")
    private String lastName;

    @OneToOne(fetch = FetchType.LAZY)
    private Cart cart;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_end_user")
    private List<ShoppingOrder> shoppingOrders = new ArrayList<>();

}
