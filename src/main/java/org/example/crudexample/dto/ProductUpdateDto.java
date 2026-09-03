package org.example.crudexample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Сущность для обновления иформации о продукте")
public class ProductUpdateDto {

        @Schema(description = "Наименование продукта", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @NotBlank(message = "Название товара не может быть пустым")
        @Size(max = 255)
        private String name;

        @Schema(description = "Описание продукта", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 1000)
        private String description;

        @Schema(description = "Цена продукта", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @NotNull(message = "Цена должна быть указана")
        @PositiveOrZero
        private BigDecimal price;

        @Schema(description = "Количество продукта", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @NotNull(message = "Количество должно быть указано")
        @PositiveOrZero
        private Integer stockQuantity;
}