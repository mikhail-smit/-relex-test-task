package mikhail.task.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import mikhail.task.models.Product;
import mikhail.task.models.User;

import java.util.Date;

@Data
public class HarvestResultDTO {
    private int id;

    @Min(1)
    private int userId;

    @Min(1)
    private int productId;

    @Min(0L)
    private int count;

    private Date atMoment;
}
