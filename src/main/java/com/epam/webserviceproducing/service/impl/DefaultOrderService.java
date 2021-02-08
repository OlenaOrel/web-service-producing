package com.epam.webserviceproducing.service.impl;


import com.epam.webserviceproducing.dao.OrderRepository;
import com.epam.webserviceproducing.entity.Order;
import com.epam.webserviceproducing.entity.OrderStatus;
import com.epam.webserviceproducing.entity.Product;
import com.epam.webserviceproducing.service.OrderService;
import com.epam.webserviceproducing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class DefaultOrderService implements OrderService {

    public static final String ORDER_WITH_ID = "Order with id = ";
    public static final String NOT_FOUND = " not found";
    public static final String CAN_NOT_CHANGE_ORDER = "Order was sent, you can't change it.";

    private OrderRepository repository;
    private ProductService productService;

    @Autowired
    public DefaultOrderService(OrderRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    @Override
    public Order getOrderById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ORDER_WITH_ID + id + NOT_FOUND));
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.CREATED);
        return repository.save(order);
    }

    @Override
    public void sendOrder(Order order) {
        order.setStatus(OrderStatus.SENT);
        repository.save(order);
    }

    @Override
    public void deleteOrder(Order order) {
        checkOrderStatus(order);
        repository.delete(order);
    }

    @Override
    public Order addProductToOrder(Long orderId, Long productId) {
        Order order = getOrderById(orderId);
        checkOrderStatus(order);
        Product product = productService.getProductById(productId);
        order.getProducts()
                .add(product);
        order.setStatus(OrderStatus.UPDATED);
        return repository.save(order);
    }

    private void checkOrderStatus(Order order) {
        if (order.getStatus().equals(OrderStatus.SENT)) {
            throw new UnsupportedOperationException(CAN_NOT_CHANGE_ORDER);
        }
    }

    @Override
    public Order removeProductFromOrder(Long orderId, Long productId) {
        Order order = getOrderById(orderId);
        checkOrderStatus(order);
        Product product = productService.getProductById(productId);
        order.getProducts().remove(product);
        order.setStatus(OrderStatus.UPDATED);
        return repository.save(order);
    }
}
