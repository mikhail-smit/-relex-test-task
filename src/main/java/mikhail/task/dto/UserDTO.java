package mikhail.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "DTO for represent product")
public class UserDTO {
    @Schema(description = "Id of record in DB")
    private int id;

    @Schema(description = "User firstname and lastname")
    @NotBlank(message = "No name")
    private String name;

    @Schema(description = "User email")
    @NotBlank(message = "No email")
    @Email(message = "Incorrect email")
    private String email;

    @Schema(description = "Is user account locked")
    private boolean isLocked;
}
