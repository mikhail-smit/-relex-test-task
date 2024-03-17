package mikhail.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Schema(description = "Message which returns if error occured")
public class ErrorMessage {
    private String errorMessage;

    private long timestamp;

    private HttpStatus httpStatus;
}
