package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Table(name = "tasks")
@NoArgsConstructor
@Getter @Setter
public class Task {
    @EmbeddedId
    HierarchicalId id;
    //-------------------------READABLE STUFF
    String name;
    String description;
    //-------------------------PROJECT STATE
    TaskState state;
    //------------------------- DEAD LINE
    LocalDate date;
    @Embedded
    DateLog dateLog;
    @Enumerated(EnumType.STRING)
    RepeatMode repeat;
    @Enumerated(EnumType.STRING)
    Priority priority;
    int repeatValue;

}
