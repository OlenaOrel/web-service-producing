package com.epam.webserviceproducing.service;

import com.epam.webserviceproducing.dao.OrderRepository;
import com.epam.webserviceproducing.entity.Order;
import com.epam.webserviceproducing.entity.OrderStatus;
import com.epam.webserviceproducing.entity.Product;
import com.epam.webserviceproducing.service.impl.DefaultOrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DefaultOrderServiceTest {

    private static final Long ORDER_ID = 0L;
    private static final Long PRODUCT_ID = 0L;
    private static final long PAGE_SIZE = 3L;
    private static final String ERROR_MESSAGE = DefaultOrderService.CAN_NOT_CHANGE_ORDER;

    @InjectMocks
    private DefaultOrderService testInstance;

    @Mock
    private OrderRepository repository;

    @Mock
    private ProductService productService;

    @Mock
    private Order order;

    @Mock
    private Product product;

    @Mock
    private Page<Order> page;

    @Mock
    private Pageable pageable;

    @Mock
    private List<Product> productList;

    @Test
    void shouldReturnOrderWhenGetOrderById() {
        doReturn(Optional.of(order)).when(repository).findById(anyLong());

        Order result = testInstance.getOrderById(ORDER_ID);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(order);
        assertThat(result.getId()).isEqualTo(ORDER_ID);
    }

    @Test
    void shouldThrowExceptionWhenGetOrderById() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());
        String errorMessage = DefaultOrderService.ORDER_WITH_ID + PRODUCT_ID + DefaultOrderService.NOT_FOUND;

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> testInstance.getOrderById(ORDER_ID)
        );

        assertThat(thrown.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    void shouldReturnPageWhenGetAllOrders() {
        doReturn(page).when(repository).findAll(any(Pageable.class));
        doReturn(PAGE_SIZE).when(page).getTotalElements();

        Page<Order> result = testInstance.getAllOrders(pageable);

        assertThat(result.isEmpty()).isFalse();
        assertThat(result.getTotalElements()).isEqualTo(PAGE_SIZE);
    }

    @Test
    void shouldSaveAndReturnOrderWhenCreateOrder() {
        doReturn(order).when(repository).save(any(Order.class));
        doReturn(OrderStatus.CREATED).when(order).getStatus();

        Order result = testInstance.createOrder(order);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(order);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.CREATED);
    }

    @Test
    void shouldChangeOrderStatusWhenSendOrder() {
        doReturn(OrderStatus.SENT).when(order).getStatus();

        testInstance.sendOrder(order);

        verify(repository).save(order);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.SENT);
    }

    @Test
    void shouldDeleteOrderWhenDeleteOrder() {
        doReturn(OrderStatus.CREATED).when(order).getStatus();

        testInstance.deleteOrder(order);

        verify(repository).delete(order);
    }

    @Test
    void shouldThrowExceptionWhenDeleteOrder() {
        doReturn(OrderStatus.SENT).when(order).getStatus();

        UnsupportedOperationException thrown = assertThrows(
                UnsupportedOperationException.class,
                () -> testInstance.deleteOrder(order)
        );

        assertThat(thrown.getMessage()).isEqualTo(ERROR_MESSAGE);
    }

    @Test
    void shouldAddProductWhenAddProductToOrder() {
        doReturn(Optional.of(order)).when(repository).findById(anyLong());
        doReturn(OrderStatus.CREATED).when(order).getStatus();
        doReturn(product).when(productService).getProductById(anyLong());
        doReturn(productList).when(order).getProducts();
        doReturn(order).when(repository).save(any(Order.class));

        Order result = testInstance.addProductToOrder(ORDER_ID, PRODUCT_ID);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(order);
        verify(order.getProducts()).add(product);
        verify(order).setStatus(OrderStatus.UPDATED);
        verify(repository).save(order);
    }

    @Test
    void shouldThrowExceptionWhenAddProductToOrder() {
        doReturn(Optional.of(order)).when(repository).findById(anyLong());
        doReturn(OrderStatus.SENT).when(order).getStatus();

        UnsupportedOperationException thrown = assertThrows(
                UnsupportedOperationException.class,
                () -> testInstance.addProductToOrder(ORDER_ID, PRODUCT_ID)
        );

        assertThat(thrown.getMessage()).isEqualTo(ERROR_MESSAGE);
    }

    @Test
    void shouldRemoveProductWhenRemoveProductFromOrder() {
        doReturn(Optional.of(order)).when(repository).findById(anyLong());
        doReturn(OrderStatus.CREATED).when(order).getStatus();
        doReturn(product).when(productService).getProductById(anyLong());
        doReturn(productList).when(order).getProducts();
        doReturn(order).when(repository).save(any(Order.class));

        Order result = testInstance.removeProductFromOrder(ORDER_ID, PRODUCT_ID);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(order);
        verify(order.getProducts()).remove(product);
        verify(order).setStatus(OrderStatus.UPDATED);
        verify(repository).save(order);
    }

    @Test
    void shouldThrowExceptionWhenRemoveProductFromOrder() {
        doReturn(Optional.of(order)).when(repository).findById(anyLong());
        doReturn(OrderStatus.SENT).when(order).getStatus();

        UnsupportedOperationException thrown = assertThrows(
                UnsupportedOperationException.class,
                () -> testInstance.addProductToOrder(ORDER_ID, PRODUCT_ID)
        );

        assertThat(thrown.getMessage()).isEqualTo(ERROR_MESSAGE);
    }
}
