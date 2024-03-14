package mikhail.task.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mikhail.task.models.Unit;

@Data
public class ProductDTO {
    private int id;

    @NotBlank(message = "No name")
    private String name;

    @Min(0)
    private int count;

    @NotNull(message = "No units")
    private Unit unit;
}
