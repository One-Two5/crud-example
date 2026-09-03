package org.example.crudexample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность для ответа от сервера, возвращает карту продуктов и их количество")
public class ProductQuantityResponse {

    @Schema(description = "Карта наименований продукта и их количество")
    private Map<String, Long> quantities;

}
