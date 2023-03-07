package movie.domain;

import movie.domain.*;
import movie.infra.AbstractEvent;
import lombok.*;
import java.util.*;
@Data
@ToString
public class PaymentApproved extends AbstractEvent {

    private Long payId;
    private Long rsvId;
    private Long movieId;
    private String status;
}


