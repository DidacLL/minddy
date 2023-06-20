package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity @Table(name = "tags")
@NoArgsConstructor
@Getter
@Setter
public class Tag {
    @Embeddable @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static final class tagId implements Serializable {
        private UUID userId;
        @Column(columnDefinition = "VARCHAR(36)")
        String name;
    }
    @EmbeddedId
    private tagId id;
    @Embedded
    DateLog dateLog;
}
