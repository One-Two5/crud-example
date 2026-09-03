package org.example.crudexample.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.crudexample.dto.TokenResponseDto;
import org.example.crudexample.service.GenerateTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Авторизация", description = "Метод для получения токена")
public class AuthController {

    private final GenerateTokenService generateTokenService;

    @Operation(
            summary = "Получить токен авторизации",
            description = "Возвращает токен"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Токен успешно получен",
                    content = @Content(schema = @Schema(implementation = TokenResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Не удалось получить токен",
                    content = @Content
            )
    })
    @GetMapping("/token")
    public ResponseEntity<TokenResponseDto> getToken() {
        String base64Token = generateTokenService.generateToken();
        return ResponseEntity.ok(new TokenResponseDto(base64Token));
    }
}
