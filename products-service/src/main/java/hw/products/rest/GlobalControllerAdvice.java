package hw.products.rest;

import hw.products.dto.ErrorDto;
import hw.products.exception.NotEnoughMoneyException;
import hw.products.exception.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    protected ErrorDto handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    protected ErrorDto handleRecordNotFoundException(RecordNotFoundException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    @ResponseStatus(PAYMENT_REQUIRED)
    protected ErrorDto handleNotEnoughMoneyException(NotEnoughMoneyException ex) {
        return new ErrorDto(ex.getMessage());
    }
}