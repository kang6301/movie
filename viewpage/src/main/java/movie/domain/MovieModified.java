package movie.domain;

import movie.infra.AbstractEvent;
import lombok.Data;
import java.util.*;


@Data
public class MovieModified extends AbstractEvent {

    private Long movieId;
    private String desc;
    private Long reviewCnt;
    private Integer numberOfSeats;
    private Long customerId;
    private Date startDate;
    private Date endDate;
    private String status;
}
