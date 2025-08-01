package com.appsdeveloperblog.ws.products.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateProductRestModel {
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
