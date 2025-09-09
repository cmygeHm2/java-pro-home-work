package hw.exception;

public class RecordNotFoundException extends RuntimeException {
    private static final String message = "Запись не найдена";

    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException() {
        super(message);
    }
}
