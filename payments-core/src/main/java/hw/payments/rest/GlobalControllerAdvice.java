package hw.payments.rest;

import hw.payments.dto.ErrorDto;
import hw.payments.exception.IntegrationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    protected ErrorDto handleRuntimeException(RuntimeException ex) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(ex.getMessage());
        return errorDto;
    }

    @ExceptionHandler(IntegrationException.class)
    @ResponseStatus(BAD_GATEWAY)
    protected ResponseEntity<ErrorDto> handelIntegrationException(IntegrationException ex) {
        var error =  new ErrorDto();
        error.setMessage(ex.getMessage());
        error.setExternalMessage(ex.getExternalMessage());

        return ResponseEntity.status(ex.getCode())
                .body(error);
    }
}