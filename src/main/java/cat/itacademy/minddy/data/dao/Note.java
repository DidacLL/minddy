package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.config.HierarchicalId;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity @Table(name = "notes")
@NoArgsConstructor
@Getter
@Setter
public class Note {
    @EmbeddedId
    HierarchicalId id;
    //-------------------------READABLE STUFF
    String content;

    @Embedded
    DateLog dateLog;
}
