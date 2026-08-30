package org.example.crudexample.dto;

import java.util.Map;

public record ProductQuantityResponse(Map<String, Long> quantities) {}
