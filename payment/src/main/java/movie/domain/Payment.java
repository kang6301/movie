package movie.domain;

import movie.domain.PaymentApproved;
import movie.domain.PaymentCancelled;
import movie.PaymentApplication;
import javax.persistence.*;
import java.util.List;
import java.util.Random;

import lombok.Data;
import java.util.Date;


@Entity
@Table(name="Payment_table")
@Data

public class Payment  {


    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long payId;
    
    
    
    
    
    private Long rsvId;
    
    
    
    
    
    private Long movieId;
    
    
    
    
    
    private String status;

    @PostPersist
    public void onPostPersist(){


        PaymentApproved paymentApproved = new PaymentApproved(this);
        paymentApproved.publishAfterCommit();

    }
    @PostUpdate
    public void onPostUpdate(){


        PaymentCancelled paymentCancelled = new PaymentCancelled(this);
        paymentCancelled.publishAfterCommit();

    }

    public static PaymentRepository repository(){
        PaymentRepository paymentRepository = PaymentApplication.applicationContext.getBean(PaymentRepository.class);
        return paymentRepository;
    }




    public static void cancelPayment(ReservationCancelRequested reservationCancelRequested){

        /** Example 1:  new item 
        Payment payment = new Payment();
        repository().save(payment);

        */

        /** Example 2:  finding and process
        
        repository().findById(reservationCancelRequested.get???()).ifPresent(payment->{
            
            payment // do something
            repository().save(payment);


         });
        */

        repository().findById(reservationCancelRequested.getRsvId()).ifPresent(payment->{
            
            repository().delete(payment);


         });

        
    }
    public static void approvePayment(ReservationCreated reservationCreated){

        /** Example 1:  new item 
        Payment payment = new Payment();
        repository().save(payment);

        */
        // Random random = new Random();
        Payment payment = new Payment();
        // payment.setPayId(random.nextLong(1000000000));
        payment.setRsvId(reservationCreated.getRsvId());
        payment.setMovieId(reservationCreated.getMovieId());
        repository().save(payment);

        /** Example 2:  finding and process
        
        repository().findById(reservationCreated.get???()).ifPresent(payment->{
            
            payment // do something
            repository().save(payment);


         });
        */

        
    }


}
