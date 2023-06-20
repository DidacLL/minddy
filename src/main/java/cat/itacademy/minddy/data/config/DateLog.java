package cat.itacademy.minddy.data.config;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
@Embeddable
public class DateLog
{
    //---------------------------------------DATE STUFF
    @CreationTimestamp
    @Column(updatable = false,nullable = false)
    LocalDate creationDate;
    @UpdateTimestamp
    @Column(nullable = false)
    LocalDate updateDate;
}
