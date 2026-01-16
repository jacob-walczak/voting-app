package pl.jakubwalczak.voting.common.error;

import java.time.OffsetDateTime;
import java.util.List;

public class ApiError {

    private final OffsetDateTime timestamp;
    private final int status;
    private final String message;
    private final List<FieldError> fieldErrors;

    public ApiError(OffsetDateTime timestamp, int status, String message, List<FieldError> fieldErrors) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public static ApiError of(int status, String message) {
        return new ApiError(OffsetDateTime.now(), status, message, List.of());
    }

    public static ApiError ofValidation(String message, List<FieldError> fieldErrors) {
        return new ApiError(OffsetDateTime.now(), 400, message, fieldErrors);
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public record FieldError(String field, String message) { }
}
