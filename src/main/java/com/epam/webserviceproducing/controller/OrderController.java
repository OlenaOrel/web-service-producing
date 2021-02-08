package com.epam.webserviceproducing.controller;

import com.epam.webserviceproducing.entity.Order;
import com.epam.webserviceproducing.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private OrderService service;
    private static final String ORDER_ID = "/{orderId}";
    private static final String PRODUCT_PATH = "/{orderId}/products/{productId}";

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping(value = ORDER_ID)
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = service.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Order>> getAllOrders(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Order> page = service.getAllOrders(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createOrder(@RequestBody Order order) {
        service.createOrder(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = ORDER_ID)
    public ResponseEntity<HttpStatus> sendOrder(@RequestBody Order order) {
        service.sendOrder(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping(value = ORDER_ID)
    public ResponseEntity<HttpStatus> deleteOrder(@RequestBody Order order) {
        service.deleteOrder(order);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = PRODUCT_PATH)
    public ResponseEntity<Order> addProductToOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        Order order = service.addProductToOrder(orderId, productId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping(value = PRODUCT_PATH)
    public ResponseEntity<Order> deleteProductFromOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        Order order = service.removeProductFromOrder(orderId, productId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
