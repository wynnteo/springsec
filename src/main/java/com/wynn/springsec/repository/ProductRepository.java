package com.wynn.springsec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wynn.springsec.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
