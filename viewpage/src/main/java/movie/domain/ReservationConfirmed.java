package movie.domain;

import movie.infra.AbstractEvent;
import lombok.Data;
import java.util.*;


@Data
public class ReservationConfirmed extends AbstractEvent {

    private Long rsvId;
    private Long movieId;
    private String status;
    private Long payId;
    private Long customerId;
    private Date screeningDate;
}
