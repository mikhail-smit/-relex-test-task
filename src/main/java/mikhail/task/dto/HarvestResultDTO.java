package mikhail.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import mikhail.task.models.Product;
import mikhail.task.models.User;

import java.util.Date;

@Data
@Schema(description = "DTO for harvest result")
public class HarvestResultDTO {
    @Schema(description = "Id of record in DB")
    private int id;

    @Schema(description = "User Id who get this harvest")
    @Min(1)
    private int userId;

    @Schema(description = "Product Id which was obtained from farm")
    @Min(1)
    private int productId;

    @Schema(description = "Abstract unit count of that product")
    @Min(0L)
    private int count;

    @Schema(description = "Date when harvest result added")
    private Date atMoment;
}
