package br.com.fiap.techchallenge.order.infra.entrypoint.controller.handler;

import br.com.fiap.techchallenge.order.application.exceptions.AlreadyExistsException;
import br.com.fiap.techchallenge.order.application.exceptions.DoesNotExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.time.DateTimeException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ControllerAdviceTest {

    @Mock
    private WebRequest mockWebRequest;

    @Mock
    private MethodParameter methodParameter;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ControllerAdvice controllerAdvice;

    @Test
    @DisplayName("Should return HTTP Status Bad Request when application throws AlreadyExistsException")
    void handleAlreadyExists() {
        AlreadyExistsException exception = new AlreadyExistsException("");

        assertEquals(HttpStatus.BAD_REQUEST, controllerAdvice.alreadyExists(exception).getStatusCode());
    }

    @Test
    @DisplayName("Should return HTTP Status Not Found when application throws DoesNotExistException")
    void handleNotFound() {
        DoesNotExistException exception = new DoesNotExistException("");

        assertEquals(HttpStatus.NOT_FOUND, controllerAdvice.notFound(exception).getStatusCode());
    }

    @Test
    @DisplayName("Should return HTTP Status Bad Request when application throws DateTimeException")
    void dateTimeException() {
        DateTimeException exception = new DateTimeException("");

        assertEquals(HttpStatus.BAD_REQUEST, controllerAdvice.dateTimeException(exception).getStatusCode());
    }

    @Test
    @DisplayName("Should return HTTP Status Bad Request when application throws MethodArgumentNotValidException")
    void handleMethodArgumentNotValid() {
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);
        HttpHeaders headers = new HttpHeaders();
        HttpStatusCode status = HttpStatus.BAD_REQUEST;

        assertEquals(status, Objects.requireNonNull(controllerAdvice.handleMethodArgumentNotValid(exception, headers, status, mockWebRequest)).getStatusCode());
    }

    @Test
    @DisplayName("Should return HTTP Status Internal Server Error when application throws Exception")
    void handle500Error() {
        Exception exception = new Exception();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, controllerAdvice.handle500Error(exception).getStatusCode());
    }

}