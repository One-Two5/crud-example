package org.example.crudexample.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class ProductNameRequest {

    private List<String> names;
}
