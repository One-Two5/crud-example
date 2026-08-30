package org.example.crudexample.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductUpdateDto(

        @NotBlank(message = "Название товара не может быть пустым")
        @Size(max = 255)
        String name,

        @Size(max = 1000)
        String description,

        @NotNull(message = "Цена должна быть указана")
        @PositiveOrZero
        BigDecimal price,

        @NotNull(message = "Количество должно быть указано")
        @PositiveOrZero
        Integer stockQuantity
) {
}
