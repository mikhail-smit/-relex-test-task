package mikhail.task.exceptions;

/**
 * When BindingResult has errors controllers thwows this exception.
 */
public class IncorrectInputFieldException extends RuntimeException {
    public IncorrectInputFieldException() {
    }

    public IncorrectInputFieldException(String message) {
        super(message);
    }
}
