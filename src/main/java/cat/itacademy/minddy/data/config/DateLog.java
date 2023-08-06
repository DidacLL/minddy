package cat.itacademy.minddy.data.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Instant;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DateLog
{
    //---------------------------------------DATE STUFF
    @CreationTimestamp
    @Column(name = "creation_date",updatable = false,nullable = false)
    Instant creationDate;
    @UpdateTimestamp
    @Column(name = "update_date",nullable = false)
    private Instant updateDate;
}
