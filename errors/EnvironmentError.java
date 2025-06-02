package errors;

public class EnvironmentError extends RuntimeException {
    public EnvironmentError(String message) {
        super(message);
    }
}
