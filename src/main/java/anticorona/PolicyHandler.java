package anticorona;

import anticorona.config.kafka.KafkaProcessor;
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
    public void wheneverVcCompleted_UpdateStatus(@Payload VcCompleted vcCompleted){

        if(!vcCompleted.validate()) return;

        System.out.println("\n\n##### listener UpdateStatus : " + vcCompleted.toJson() + "\n\n");

        // Sample Logic //
        Booking booking = new Booking();
        bookingRepository.save(booking);
            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
