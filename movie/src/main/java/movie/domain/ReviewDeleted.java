package movie.domain;

import movie.domain.*;
import movie.infra.AbstractEvent;
import lombok.*;
import java.util.*;
@Data
@ToString
public class ReviewDeleted extends AbstractEvent {

    private Long reviewId;
    private Long movieId;
    private String content;
}


