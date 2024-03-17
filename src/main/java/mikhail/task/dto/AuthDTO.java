package mikhail.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO for user login
 */
@Schema(description = "DTO for user authenticate")
@Data
public class AuthDTO {
    @NotBlank(message = "No email")
    private String email;

    @Size(min = 8)
    private String password;
}
