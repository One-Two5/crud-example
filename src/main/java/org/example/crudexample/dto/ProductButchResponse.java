package org.example.crudexample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность для отправки списка продуктов")
public class ProductButchResponse {

    @Schema(description = "Наименование продукта", example = "smartphone", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ProductResponse> productResponseList;
}
