package com.onlineShop.models.Users.Staff;

import com.onlineShop.models.Shop.Shop;
import com.onlineShop.models.Users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Staff extends UserEntity {

    @NotBlank(message = "Firstname can't be empty")
    @Column(name="first_name")
    @Length(min = 3, max = 50, message = "Firstname must be between 3 and 50 characters")
    private String firstName;

    @NotBlank(message = "Lastname can't be empty")
    @Column(name="last_name")
    @Length(min = 3, max = 50, message = "Lastname must be between 3 and 50 characters")
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "staff")
    private List<StaffList> shops;

}
