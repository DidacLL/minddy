package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.ProjectState;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Table(name = "projects")
@NoArgsConstructor
@Getter
@Setter
public class Project {
    @EmbeddedId
    HierarchicalId id;
    //-------------------------READABLE STUFF
    String name;
    String description;
    //-------------------------PROJECT STATE
    ProjectState state;
    //------------------------- DEAD LINE
    LocalDate deadLine;

    @Embedded
    DateLog dateLog;
}
