package movie.domain;

import movie.domain.*;
import movie.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class ReviewCreated extends AbstractEvent {

    private Long reviewId;
    private String content;
    private Long movieId;

    public ReviewCreated(Review aggregate){
        super(aggregate);
    }
    public ReviewCreated(){
        super();
    }
}
