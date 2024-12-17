package com.onlineShop.dto.user.privilege;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeResponse {

    private int id;

    @NotBlank(message = "Privilege type can't be empty")
    private String privilegeType;

}