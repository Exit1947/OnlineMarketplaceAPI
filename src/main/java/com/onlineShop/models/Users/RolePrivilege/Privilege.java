package com.onlineShop.models.Users.RolePrivilege;

import com.onlineShop.models.Users.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Privilege implements GrantedAuthority {

    public Privilege(PrivilegeType privilegeType){
        this.setType(privilegeType);
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", unique = true)
    private PrivilegeType type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "privilege")
    private List<UserEntityPrivilege> users = new ArrayList<>();

    @Override
    public String getAuthority() {
        return type.name();
    }

}
