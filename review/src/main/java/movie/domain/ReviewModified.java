package movie.domain;

import movie.domain.*;
import movie.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class ReviewModified extends AbstractEvent {

    private Long reviewId;
    private Long movieId;
    private String content;

    public ReviewModified(Review aggregate){
        super(aggregate);
    }
    public ReviewModified(){
        super();
    }
}
