package movie.domain;

import movie.MessageApplication;
import javax.persistence.*;
import java.util.List;
import java.util.Random;

import lombok.Data;
import java.util.Date;


@Entity
@Table(name="Message_table")
@Data

public class Message  {


    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long msgId;
    
    
    
    
    
    private Long movieId;
    
    
    
    
    
    private String content;
    
    
    
    
    
    private Long customerId;


    public static MessageRepository repository(){
        MessageRepository messageRepository = MessageApplication.applicationContext.getBean(MessageRepository.class);
        return messageRepository;
    }




    public static void sendConfirmMsg(ReservationConfirmed reservationConfirmed){

        /** Example 1:  new item 
        Message message = new Message();
        repository().save(message);

        */
        // Random random = new Random();
        Message message = new Message();
        // message.setMsgId(random.nextLong(1000000000));
        message.setMovieId(reservationConfirmed.getMovieId());
        message.setCustomerId(reservationConfirmed.getCustomerId());
        message.setContent("예약이 확정되었습니다.");
        repository().save(message);

        /** Example 2:  finding and process
        
        repository().findById(reservationConfirmed.get???()).ifPresent(message->{
            
            message // do something
            repository().save(message);


         });
        */

        
    }
    public static void sendCancelMsg(ReservationCancelled reservationCancelled){

        /** Example 1:  new item 
        Message message = new Message();
        repository().save(message);

        */

        /** Example 2:  finding and process
        
        repository().findById(reservationCancelled.get???()).ifPresent(message->{
            
            message // do something
            repository().save(message);


         });
        */

        
    }


}
