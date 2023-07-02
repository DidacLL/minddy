package cat.itacademy.minddy.data.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DateLog
{
    //---------------------------------------DATE STUFF
    @CreationTimestamp
    @Column(updatable = false,nullable = false)
    private LocalDate creationDate;
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDate updateDate;
}
