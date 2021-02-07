package com.epam.webserviceproducing.controller;

import com.epam.webserviceproducing.entity.Order;
import com.epam.webserviceproducing.entity.Product;
import com.epam.webserviceproducing.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    private static final Long ORDER_ID = 0L;
    private static final Long PRODUCT_ID = 0L;
    public static final int PAGE_SIZE = 3;

    @InjectMocks
    private OrderController testInstance;

    @Mock
    private OrderService service;

    @Mock
    private Order order;

    @Mock
    private Page<Order> page;

    @Mock
    private Pageable pageable;

    @Test
    void shouldReturnOrderAndHttpStatusOkWhenGetOrderById() {
        doReturn(order).when(service).getOrderById(anyLong());

        ResponseEntity<Order> result = testInstance.getOrderById(ORDER_ID);

        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(order);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnOrdersPageAndHttpStatusOkWhenGetAllOrders() {
        doReturn(page).when(service).getAllOrders(pageable);
        doReturn(PAGE_SIZE).when(page).getTotalElements();

        ResponseEntity<Page<Order>> result = testInstance.getAllOrders(pageable);

        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(page);
        assertThat(result.getBody().getTotalElements()).isEqualTo(PAGE_SIZE);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnHttpStatusCreatedWhenCreateOrder() {
        ResponseEntity<HttpStatus> result = testInstance.createOrder(order);

        verify(service).createOrder(order);
        assertThat(result.hasBody()).isFalse();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnHttpStatusOkWhenSendOrder() {
        ResponseEntity<HttpStatus> result = testInstance.sendOrder(order);

        verify(service).sendOrder(order);
        assertThat(result.hasBody()).isFalse();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnHttpStatusNoContentWhenDeleteOrder() {
        ResponseEntity<HttpStatus> result = testInstance.deleteOrder(order);

        verify(service).deleteOrder(order);
        assertThat(result.hasBody()).isFalse();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldReturnOrderAndHttpStatusOkWhenAddProductToOrder() {
        doReturn(order).when(service).addProductToOrder(anyLong(), anyLong());

        ResponseEntity<Order> result = testInstance.addProductToOrder(ORDER_ID, PRODUCT_ID);

        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(order);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    void shouldReturnOrderAndHttpStatusOkWhenDeleteProductFromOrder() {
        doReturn(order).when(service).removeProductFromOrder(anyLong(), anyLong());

        ResponseEntity<Order> result = testInstance.deleteProductFromOrder(ORDER_ID, PRODUCT_ID);

        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(order);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
