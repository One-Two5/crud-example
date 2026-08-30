package org.example.crudexample.dto;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantityResponse {

    private Map<String, Long> quantities;

}
