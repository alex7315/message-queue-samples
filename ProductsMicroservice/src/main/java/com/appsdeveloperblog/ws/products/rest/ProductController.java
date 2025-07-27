package com.appsdeveloperblog.ws.products.rest;

import com.appsdeveloperblog.ws.products.model.CreateProductRestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains implementation of REST endpoint methods to manage product data</br>
 * Base path: http(s)://<host>:[port]/api/product e.g. {@code http://localhost:8080/api/product}
 */
@RestController
@RequestMapping(path = "/api/product")
public class ProductController {

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductRestModel product) {
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
