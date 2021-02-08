package com.epam.webserviceproducing.service.impl;

import com.epam.webserviceproducing.dao.ProductRepository;
import com.epam.webserviceproducing.entity.Product;
import com.epam.webserviceproducing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class DefaultProductService implements ProductService {

    public static final String PRODUCT_WITH_ID = "Product with id = ";
    public static final String NOT_FOUND = " not found";


    private ProductRepository repository;

    @Autowired
    public DefaultProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_WITH_ID + id + NOT_FOUND));
    }
}
