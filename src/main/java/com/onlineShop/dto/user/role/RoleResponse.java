package com.onlineShop.dto.user.role;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {

    private int id;

    @NotBlank(message = "Type of role can't be empty")
    private String type;

}
