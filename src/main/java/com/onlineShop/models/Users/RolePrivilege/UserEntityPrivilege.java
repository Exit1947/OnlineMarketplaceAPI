package com.onlineShop.models.Users.RolePrivilege;

import com.onlineShop.models.Users.UserEntity;
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
public class UserEntityPrivilege {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne

    @JoinColumn(name = "id_userEntity")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "id_privilege")
    private Privilege privilege;

}
