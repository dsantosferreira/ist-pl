package errors;

public class TypeCheckError extends RuntimeException {
    public TypeCheckError(String message) {
        super(message);
    }
}
