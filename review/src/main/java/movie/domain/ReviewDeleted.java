package movie.domain;

import movie.domain.*;
import movie.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class ReviewDeleted extends AbstractEvent {

    private Long reviewId;
    private Long movieId;
    private String content;

    public ReviewDeleted(Review aggregate){
        super(aggregate);
    }
    public ReviewDeleted(){
        super();
    }
}
