package com.onlineShop.models.Product.Category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @NotBlank
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name of category can't be empty")
    @Column(name="name")
    @Length(min = 3, max = 100, message = "Name of category is either too short or exceeds the limit of 3 to 100 characters")
    private String name;

    @NotBlank
    @Column(name="image")
    private String image;

}
