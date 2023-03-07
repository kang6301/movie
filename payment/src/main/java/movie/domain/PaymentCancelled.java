package movie.domain;

import movie.domain.*;
import movie.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class PaymentCancelled extends AbstractEvent {

    private Long payId;
    private Long rsvId;
    private Long movieId;
    private String status;

    public PaymentCancelled(Payment aggregate){
        super(aggregate);
    }
    public PaymentCancelled(){
        super();
    }
}
