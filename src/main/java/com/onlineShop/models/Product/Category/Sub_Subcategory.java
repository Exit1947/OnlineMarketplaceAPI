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
public class Sub_Subcategory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name of sub_subcategory can't be empty")
    @Column(name="name_subcategory")
    @Length(max = 100)
    private String nameSubcategory;

    @NotBlank(message = "Name of subsubsubcategory can't be empty")
    @Column(name="name_subsubcategory")
    @Length(max = 100)
    private String nameSubSubcategory;

}
