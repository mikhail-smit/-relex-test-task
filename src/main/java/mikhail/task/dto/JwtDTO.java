package mikhail.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "DTO returns if auth successful")
public class JwtDTO {
    @Schema(description = "JWT for user")
    private String jwt;
}
