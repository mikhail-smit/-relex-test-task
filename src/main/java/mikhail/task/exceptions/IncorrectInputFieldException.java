package mikhail.task.exceptions;

public class IncorrectInputFieldException extends RuntimeException {
    public IncorrectInputFieldException() {
    }

    public IncorrectInputFieldException(String message) {
        super(message);
    }
}
