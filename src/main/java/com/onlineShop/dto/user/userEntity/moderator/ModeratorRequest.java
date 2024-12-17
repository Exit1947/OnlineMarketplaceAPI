package com.onlineShop.dto.user.userEntity.moderator;

import com.onlineShop.dto.user.userEntity.staff.StaffRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModeratorRequest extends StaffRequest {

    @NotNull(message = "Shop can't be empty")
    private Long shopId;

    @NotBlank(message = "Admin id can't be empty")
    private String adminId;

}
