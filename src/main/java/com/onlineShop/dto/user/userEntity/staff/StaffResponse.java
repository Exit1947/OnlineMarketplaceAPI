package com.onlineShop.dto.user.userEntity.staff;

import com.onlineShop.dto.user.userEntity.userEntity.UserEntityResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponse extends UserEntityResponse {

    @NotBlank(message = "Lastname can't be empty")
    @Length(min = 3, max = 50, message = "Lastname must be between 3 and 50 characters")
    private String lastName;

    @NotNull(message = "Shop list can't be empty")
    private List<Long> shopIds;

}
