package com.epam.webserviceproducing.dao;

import com.epam.webserviceproducing.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
