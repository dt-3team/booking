package anticorona;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;
import java.util.Date;

@Entity
@Table(name="Booking")
public class Booking extends ResourceSupport {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long bookingId;
    private Long vaccineId;
    private String vcName;
    private Long userId;
    private String status;

    @PostPersist
    public void onPostPersist() throws Exception {
        if(BookingApplication.applicationContext.getBean(anticorona.external.VaccineService.class)
            .checkAndBookStock(this.vaccineId)){
                Booked booked = new Booked();
                BeanUtils.copyProperties(this, booked);
                booked.publishAfterCommit();
            }
        else{
            throw new Exception("Out of Stock Exception Raised.");
        }

    }

    @PreUpdate
    @PostRemove
    public void onCancelled(){
        if("BOOKING_CANCELLED".equals(this.status)){
            BookingCancelled bookingCancelled = new BookingCancelled();
            BeanUtils.copyProperties(this, bookingCancelled);
            bookingCancelled.publishAfterCommit();
        }
    }


    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    public Long getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(Long vaccineId) {
        this.vaccineId = vaccineId;
    }
    public String getVcName() {
        return vcName;
    }

    public void setVcName(String vcName) {
        this.vcName = vcName;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
