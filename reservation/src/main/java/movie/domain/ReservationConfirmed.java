package movie.domain;

import movie.domain.*;
import movie.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class ReservationConfirmed extends AbstractEvent {

    private Long rsvId;
    private Long movieId;
    private String status;
    private Long payId;
    private Long customerId;
    private Date screeningDate;

    public ReservationConfirmed(Reservation aggregate){
        super(aggregate);
    }
    public ReservationConfirmed(){
        super();
    }
}
