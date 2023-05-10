package com.wynn.springsec.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wynn.springsec.dto.SuccessResponse;
import com.wynn.springsec.exception.NotFoundException;
import com.wynn.springsec.model.Product;
import com.wynn.springsec.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final String PRODUCT_NOT_FOUND = "Product not found"; 

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable(value = "id") Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            throw new NotFoundException(PRODUCT_NOT_FOUND);
        }
    }

    @PostMapping("/")
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable(value = "id") Long id, @RequestBody Product newProduct) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product prod = product.get();
            prod.setDescription(newProduct.getDescription());
            prod.setPrice(newProduct.getPrice());
            prod.setTitle(newProduct.getTitle());
            prod = productRepository.save(prod);
            return ResponseEntity.ok().body(prod);
        } else {
            throw new NotFoundException(PRODUCT_NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.delete(product.get());
            SuccessResponse successResponse = new SuccessResponse("Product has been deleted successfully.");
            return ResponseEntity.ok().body(successResponse);
        } else {
            throw new NotFoundException(PRODUCT_NOT_FOUND);
        }
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<String> handleMissingPathVariableException(MissingPathVariableException ex) {
        String message = "Required path variable '" + ex.getVariableName() + "' is missing";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
