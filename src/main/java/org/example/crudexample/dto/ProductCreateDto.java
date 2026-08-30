package org.example.crudexample.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductCreateDto(
        @NotBlank(message = "Название товара не может быть пустым")
        @Size(max = 255, message = "Название не должно превышать 255 символов")
        String name,

        @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
        String description,

        @NotNull(message = "Цена должна быть указана")
        @PositiveOrZero(message = "Цена не может быть отрицательной")
        BigDecimal price,

        @NotNull(message = "Количество на складе должно быть указано")
        @PositiveOrZero(message = "Количество на складе не может быть отрицательным")
        Integer stockQuantity
) {}
