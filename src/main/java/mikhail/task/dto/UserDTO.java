package mikhail.task.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
    private int id;

    @NotBlank(message = "No name")
    private String name;

    @NotBlank(message = "No email")
    @Email(message = "Incorrect email")
    private String email;

    private boolean isLocked;
}
