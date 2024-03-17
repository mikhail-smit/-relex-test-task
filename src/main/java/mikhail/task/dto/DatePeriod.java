package mikhail.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "DTO for represent date period")
public class DatePeriod {
    private Date from;

    private Date to;
}
