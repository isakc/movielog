package movielog.common.exception;

public record ErrorResponse (
        String code,
        String message
) {}
