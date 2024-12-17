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
public class Category_Subcategory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_category")
    private Category category;

    @NotBlank
    @Column(name="name_subcategory")
    @Length(max = 100)
    private String nameSubcategory;

}
