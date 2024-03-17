package mikhail.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "DTO for user create and update")
public class UserRegistrationDTO {
    @Schema(description = "User firstname and lastname")
    @NotBlank(message = "No name")
    private String name;

    @Schema(description = "User email, should be unique")
    @NotBlank(message = "No email")
    @Email(message = "Incorrect email")
    private String email;

    @Schema(description = "User password")
    @NotBlank(message = "No password")
    @Size(min = 8, message = "Too short password")
    private String password;

    @Schema(description = "Is user account locked")
    private boolean isLocked;
}
