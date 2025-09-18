package hw.payments.exception;

import lombok.Getter;

@Getter
public class IntegrationException extends RuntimeException {

    private final Integer code;
    private final String externalMessage;

    public IntegrationException(String message, Integer code, String externalMessage) {
        super(message);
        this.code = code;
        this.externalMessage = externalMessage;
    }
}
