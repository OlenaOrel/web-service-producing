package com.epam.webserviceproducing.service;

import com.epam.webserviceproducing.dao.ProductRepository;
import com.epam.webserviceproducing.entity.Product;
import com.epam.webserviceproducing.service.impl.DefaultProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class DefaultProductServiceTest {

    private static final Long PRODUCT_ID = 0L;

    @InjectMocks
    private DefaultProductService testInstance;

    @Mock
    private Product product;

    @Mock
    private ProductRepository repository;

    @Test
    void shouldReturnProductWhenGetProductById() {
        doReturn(Optional.of(product)).when(repository).findById(anyLong());

        Product result = testInstance.getProductById(PRODUCT_ID);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(product);
        assertThat(result.getId()).isEqualTo(PRODUCT_ID);
    }

    @Test
    void shouldThrowExceptionWhenGetProductById() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());
        String errorMessage = DefaultProductService.PRODUCT_WITH_ID + PRODUCT_ID + DefaultProductService.NOT_FOUND;

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> testInstance.getProductById(PRODUCT_ID)
        );

        assertThat(thrown.getMessage()).isEqualTo(errorMessage);
    }



}
