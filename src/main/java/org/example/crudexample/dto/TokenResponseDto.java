package org.example.crudexample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность для получения токена авторизации")
public class TokenResponseDto {

    @Schema(description = "Токен авторизации", requiredMode = Schema.RequiredMode.REQUIRED)
    private String token;
}
