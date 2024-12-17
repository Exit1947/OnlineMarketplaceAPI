package com.onlineShop.models.Users.RolePrivilege;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class Role {

    public Role() {
    }

    public Role(final RoleType type) {
        this.type = type;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", unique = true)
    private RoleType type;

}
