package movie.domain;

import movie.domain.MovieRegistered;
import movie.domain.MovieModified;
import movie.domain.MovieReserved;
import movie.domain.MovieCancelled;
import movie.domain.MovieDeleted;
import movie.MovieApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;


@Entity
@Table(name="Movie_table")
@Data

public class Movie  {


    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long movieId;
    
    
    
    
    
    private String status;
    
    
    
    
    
    private String desc;
    
    
    
    
    
    private Integer reviewCnt;
    
    
    
    
    
    private Integer numberOfSeats;
    
    
    
    
    
    private Long customerId;
    
    
    
    
    
    private Date startDate;
    
    
    
    
    
    private Date endDate;

    @PostPersist
    public void onPostPersist(){


        MovieRegistered movieRegistered = new MovieRegistered(this);
        movieRegistered.publishAfterCommit();

    }
    @PostUpdate
    public void onPostUpdate(){


        MovieModified movieModified = new MovieModified(this);
        movieModified.publishAfterCommit();



        MovieReserved movieReserved = new MovieReserved(this);
        movieReserved.publishAfterCommit();



        MovieCancelled movieCancelled = new MovieCancelled(this);
        movieCancelled.publishAfterCommit();

    }
    @PrePersist
    public void onPrePersist(){
    }
    @PreUpdate
    public void onPreUpdate(){
    }
    @PreRemove
    public void onPreRemove(){


        MovieDeleted movieDeleted = new MovieDeleted(this);
        movieDeleted.publishAfterCommit();

    }

    public static MovieRepository repository(){
        MovieRepository movieRepository = MovieApplication.applicationContext.getBean(MovieRepository.class);
        return movieRepository;
    }



    public void chkAndReqReserve(){
    }

    public static void updateReviewCnt(ReviewCreated reviewCreated){

        /** Example 1:  new item 
        Movie movie = new Movie();
        repository().save(movie);

        */

        /** Example 2:  finding and process
        
        repository().findById(reviewCreated.get???()).ifPresent(movie->{
            
            movie // do something
            repository().save(movie);


         });
        */

        
    }
    public static void updateReviewCnt(ReviewDeleted reviewDeleted){

        /** Example 1:  new item 
        Movie movie = new Movie();
        repository().save(movie);

        */

        /** Example 2:  finding and process
        
        repository().findById(reviewDeleted.get???()).ifPresent(movie->{
            
            movie // do something
            repository().save(movie);


         });
        */

        
    }
    public static void confirmReserve(ReservationConfirmed reservationConfirmed){

        /** Example 1:  new item 
        Movie movie = new Movie();
        repository().save(movie);

        */

        /** Example 2:  finding and process
        
        repository().findById(reservationConfirmed.get???()).ifPresent(movie->{
            
            movie // do something
            repository().save(movie);


         });
        */

        
    }
    public static void cancelReserve(ReservationCancelled reservationCancelled){

        /** Example 1:  new item 
        Movie movie = new Movie();
        repository().save(movie);

        */

        /** Example 2:  finding and process
        
        repository().findById(reservationCancelled.get???()).ifPresent(movie->{
            
            movie // do something
            repository().save(movie);


         });
        */

        
    }


}
