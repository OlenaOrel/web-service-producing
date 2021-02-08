package com.epam.webserviceproducing.service;

import com.epam.webserviceproducing.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Order getOrderById(Long id);

    Page<Order> getAllOrders(Pageable pageable);

    Order createOrder(Order order);

    void sendOrder(Order order);

    void deleteOrder(Order order);

    Order addProductToOrder(Long orderId, Long productId);

    Order removeProductFromOrder(Long orderId, Long productId);

}
