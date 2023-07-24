package com.phone.integration_tests;

import com.phone.entities.ErrorObject;
import com.phone.exception.ContactAlreadyExistsException;
import com.phone.exception.ContactNotFoundException;
import com.phone.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@SpringBootTest
public class GlobalExceptionHandlerTest {

    @Mock
    private MethodArgumentNotValidException ex;



    @Test
    public void handleResourceNotFoundExceptionTest() {
        // Arrange
        ContactNotFoundException ex = new ContactNotFoundException("Contact not found");

        // Act
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<ErrorObject> responseEntity = handler.handleResourceNotFoundException(ex);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ErrorObject errorObject = responseEntity.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), errorObject.getStatus());
        assertEquals("Contact not found", errorObject.getMessage());
    }

    @Test
    public void handleNoHandlerFoundExceptionTest() {
        // Arrange
        NoHandlerFoundException ex = mock(NoHandlerFoundException.class);
        String requestURL = "/contact/123";
        when(ex.getRequestURL()).thenReturn(requestURL);

        // Act
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<String> responseEntity = handler.handleNoHandlerFoundException(ex);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        String expectedErrorMessage = "The requested resource '" + requestURL + "' was not found on this server.";
        assertEquals(expectedErrorMessage, responseEntity.getBody());
    }

    @Test
    public void handleMethodNotSupportedExceptionTest() {
        // Arrange
        HttpRequestMethodNotSupportedException ex = mock(HttpRequestMethodNotSupportedException.class);

        // Act
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<String> responseEntity = handler.handleMethodNotSupportedException(ex);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        String expectedErrorMessage = "Requested resource doesn't exist or the HTTP method is not supported.";
        assertEquals(expectedErrorMessage, responseEntity.getBody());
    }

    @Test
    public void handleContactAlreadyExistsExceptionTest() {
        // Arrange
        ContactAlreadyExistsException ex = new ContactAlreadyExistsException("Contact already exists");

        // Act
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<ErrorObject> responseEntity = handler.handleContactAlreadyExistsException(ex);

        // Assert
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        ErrorObject errorObject = responseEntity.getBody();
        assertEquals(HttpStatus.CONFLICT.value(), errorObject.getStatus());
        assertEquals("Contact already exists", errorObject.getMessage());
    }
}
