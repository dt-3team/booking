package anticorona;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Booking_table")
public class Booking {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long bookingId;
    private Long vaccineId;
    private String vcName;
    private Long userId;
    private String status;

    @PostPersist
    public void onPostPersist(){
        Booked booked = new Booked();
        BeanUtils.copyProperties(this, booked);
        booked.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        anticorona.external.Vaccine vaccine = new anticorona.external.Vaccine();
        // mappings goes here
        // BookingApplication.applicationContext.getBean(anticorona.external.VaccineService.class)
        //     .chkAndModifyStock(vaccine);


    }

    @PostUpdate
    public void onPostUpdate(){
        BookUpdated bookUpdated = new BookUpdated();
        BeanUtils.copyProperties(this, bookUpdated);
        bookUpdated.publishAfterCommit();


    }

    @PreUpdate
    public void onPreUpdate(){
        BookCancelled bookCancelled = new BookCancelled();
        BeanUtils.copyProperties(this, bookCancelled);
        bookCancelled.publishAfterCommit();


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
