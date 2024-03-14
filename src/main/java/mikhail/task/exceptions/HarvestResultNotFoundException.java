package mikhail.task.exceptions;

public class HarvestResultNotFoundException extends RuntimeException {
    public HarvestResultNotFoundException() {
    }

    public HarvestResultNotFoundException(String message) {
        super(message);
    }
}
