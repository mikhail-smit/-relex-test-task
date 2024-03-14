package mikhail.task.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.stream.Collectors;

/**
 * Util class which creates message from BindingResult field errors
 */
@Component
public class ErrorMessageUtils {
    public String createMessage(Errors errors) {
        return errors.getFieldErrors().stream()
                .map(error -> String.format("Error at: %s, Message: %s; ",
                        error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining());
    }
}
