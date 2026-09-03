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
@Schema(description = "Сущность для создания продукта в бд")
public class ProductCreateDto {

        @Schema(description = "Наименование продукта", example = "smartphone", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Название товара не может быть пустым")
        @Size(max = 255, message = "Название не должно превышать 255 символов")
        private String name;

        @Schema(description = "Описание продукта", example = "iphone", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
        private String description;

        @Schema(description = "Цена продукта", example = "10000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Цена должна быть указана")
        @PositiveOrZero(message = "Цена не может быть отрицательной")
        private BigDecimal price;

        @Schema(description = "Количество продукта", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Количество на складе должно быть указано")
        @PositiveOrZero(message = "Количество на складе не может быть отрицательным")
        private Integer stockQuantity;
}
