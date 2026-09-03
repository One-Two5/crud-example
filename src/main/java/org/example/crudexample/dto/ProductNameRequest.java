package org.example.crudexample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность для передачи списка наименований продуктов")
public class ProductNameRequest {

    @Schema(description = "Список наименований продуктов", requiredMode =  Schema.RequiredMode.REQUIRED)
    private List<String> names;
}
