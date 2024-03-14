package mikhail.task.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDTO {
    @NotBlank(message = "No name")
    private String name;

    @NotBlank(message = "No email")
    @Email(message = "Incorrect email")
    private String email;

    @NotBlank(message = "No password")
    @Size(min = 8)
    private String password;
}
