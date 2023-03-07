package movie.infra;

import movie.domain.*;
import movie.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MoviewiewsViewHandler {

    @Autowired
    private MoviewiewsRepository moviewiewsRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenMovieRegistered_then_CREATE_1 (@Payload MovieRegistered movieRegistered) {
        try {

            if (!movieRegistered.validate()) return;

            // view 객체 생성
            Moviewiews moviewiews = new Moviewiews();
            // view 객체에 이벤트의 Value 를 set 함
            moviewiews.setDesc(movieRegistered.getDesc());
            moviewiews.setMovieStatus(movieRegistered.getStatus());
            moviewiews.setNumberOfSeats(movieRegistered.getNumberOfSeats());
            moviewiews.setReviewCnt(0);
            moviewiews.setStartDate(movieRegistered.getStartDate());
            moviewiews.setEndDate(movieRegistered.getEndDate());
            moviewiews.setMovieId(movieRegistered.getMovieId());
            // view 레파지 토리에 save
            moviewiewsRepository.save(moviewiews);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenReservationConfirmed_then_UPDATE_1(@Payload ReservationConfirmed reservationConfirmed) {
        try {
            if (!reservationConfirmed.validate()) return;
                // view 객체 조회
            Optional<Moviewiews> moviewiewsOptional = moviewiewsRepository.findById(reservationConfirmed.getMovieId());

            if( moviewiewsOptional.isPresent()) {
                 Moviewiews moviewiews = moviewiewsOptional.get();
            // view 객체에 이벤트의 eventDirectValue 를 set 함
                moviewiews.setNumberOfSeats(moviewiews.getNumberOfSeats() - 1);
                // view 레파지 토리에 save
                 moviewiewsRepository.save(moviewiews);
                }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenMovieModified_then_UPDATE_2(@Payload MovieModified movieModified) {
        try {
            if (!movieModified.validate()) return;
                // view 객체 조회
            Optional<Moviewiews> moviewiewsOptional = moviewiewsRepository.findById(movieModified.getMovieId());

            if( moviewiewsOptional.isPresent()) {
                 Moviewiews moviewiews = moviewiewsOptional.get();
            // view 객체에 이벤트의 eventDirectValue 를 set 함
                moviewiews.setDesc(movieModified.getDesc());    
                moviewiews.setMovieStatus(movieModified.getStatus());    
                moviewiews.setNumberOfSeats(movieModified.getNumberOfSeats());    
                // view 레파지 토리에 save
                 moviewiewsRepository.save(moviewiews);
                }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenReservationCancelled_then_UPDATE_3(@Payload ReservationCancelled reservationCancelled) {
        try {
            if (!reservationCancelled.validate()) return;
                // view 객체 조회
            Optional<Moviewiews> moviewiewsOptional = moviewiewsRepository.findById(reservationCancelled.getMovieId());

            if( moviewiewsOptional.isPresent()) {
                 Moviewiews moviewiews = moviewiewsOptional.get();
            // view 객체에 이벤트의 eventDirectValue 를 set 함
                moviewiews.setNumberOfSeats(moviewiews.getNumberOfSeats() + 1);
                // view 레파지 토리에 save
                 moviewiewsRepository.save(moviewiews);
                }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenMovieDeleted_then_DELETE_1(@Payload MovieDeleted movieDeleted) {
        try {
            if (!movieDeleted.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            moviewiewsRepository.deleteById(movieDeleted.getMovieId());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

