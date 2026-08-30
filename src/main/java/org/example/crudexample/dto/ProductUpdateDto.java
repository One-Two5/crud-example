package org.example.crudexample.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDto {

        @NotBlank(message = "Название товара не может быть пустым")
        @Size(max = 255)
        private String name;

        @Size(max = 1000)
        private String description;

        @NotNull(message = "Цена должна быть указана")
        @PositiveOrZero
        private BigDecimal price;

        @NotNull(message = "Количество должно быть указано")
        @PositiveOrZero
        private Integer stockQuantity;
}