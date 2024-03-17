package mikhail.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mikhail.task.models.Unit;

@Data
@Schema(description = "DTO for represent product")
public class ProductDTO {
    @Schema(description = "Id of record in DB")
    private int id;

    @Schema(description = "Product name")
    @NotBlank(message = "No name")
    private String name;

    @Schema(description = "Abstract unit count of product")
    @Min(0)
    private int count;

    @Schema(description = "Measure unit of product")
    @NotNull(message = "No units")
    private Unit unit;
}
