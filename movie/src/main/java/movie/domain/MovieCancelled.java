package movie.domain;

import movie.domain.*;
import movie.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class MovieCancelled extends AbstractEvent {

    private Long movieId;
    private String status;
    private String desc;
    private Long reviewCnt;
    private Integer numberOfSeats;
    private Long customerId;
    private Date startDate;
    private Date endDate;

    public MovieCancelled(Movie aggregate){
        super(aggregate);
    }
    public MovieCancelled(){
        super();
    }
}
