package org.example.crudexample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность для получения ответа от сервера")
public class ProductResponse {

    @Schema(description = "Уникальный идентификатор", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Наименование продукта", example = "smartphone", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Описание продукта", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "Цена продукта", example = "10000", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;

    @Schema(description = "Количество", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer stockQuantity;
}
