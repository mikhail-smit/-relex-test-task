package mikhail.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthDTO {
    @NotBlank(message = "No email")
    private String email;

    @Size(min = 8)
    private String password;
}
