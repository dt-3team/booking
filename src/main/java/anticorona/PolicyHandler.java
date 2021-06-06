package anticorona;

import anticorona.config.kafka.KafkaProcessor;

import java.util.Optional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    
    @Autowired BookingRepository bookingRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCompleted_UpdateStatus(@Payload Completed completed){

        if(!completed.validate()) return;

        System.out.println("\n\n##### listener UpdateStatus : " + completed.toJson() + "\n\n");
        Optional<Booking> booking = bookingRepository.findById(completed.getBookingId());
        if(booking.isPresent()){
            Booking bookingValue = booking.get();
            bookingValue.setStatus("INJECTION_COMPLETED");
            bookingRepository.save(bookingValue);
        }
            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
