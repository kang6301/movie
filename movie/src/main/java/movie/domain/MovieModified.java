package movie.domain;

import movie.domain.*;
import movie.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class MovieModified extends AbstractEvent {

    private Long movieId;
    private String desc;
    private Long reviewCnt;
    private Integer numberOfSeats;
    private Long customerId;
    private Date startDate;
    private Date endDate;
    private String status;

    public MovieModified(Movie aggregate){
        super(aggregate);
    }
    public MovieModified(){
        super();
    }
}
