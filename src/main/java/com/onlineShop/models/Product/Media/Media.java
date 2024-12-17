package com.onlineShop.models.Product.Media;

import com.onlineShop.models.Product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @NonNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="id_product")
    private Product product;

    @NotBlank
    @Column(name="mediaName")
    private String mediaName;

    @Positive(message = "Number must not be blank")
    private int number;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MediaType type;

}

