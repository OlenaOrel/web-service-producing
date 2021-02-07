package com.epam.webserviceproducing.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExceptionHandlerControllerTest {

    private static final String MESSAGE = "exception";

    @InjectMocks
    private ExceptionHandlerController testInstance;

    @Mock
    private EntityNotFoundException notFoundException;

    @Mock
    private UnsupportedOperationException unsupportedOperationException;

    @Test
    void shouldReturnHttpStatusNofFoundWhenHandleException() {
        when(notFoundException.getMessage()).thenReturn(MESSAGE);

        ResponseEntity<String> result = testInstance.handleException(notFoundException);

        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(MESSAGE);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnHttpStatusConflictWhenHandleException() {
        when(unsupportedOperationException.getMessage()).thenReturn(MESSAGE);

        ResponseEntity<String> result = testInstance.handleException(unsupportedOperationException);

        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(MESSAGE);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }



}
