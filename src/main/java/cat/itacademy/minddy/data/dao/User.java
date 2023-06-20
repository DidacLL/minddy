package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity @Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID id;
    private String name;
    @OneToOne
//    @JoinColumns({
//            @JoinColumn(name = "rootProject_userId", referencedColumnName = "userId"),
//            @JoinColumn(name = "rootProject_holderId", referencedColumnName = "holderId"),
//            @JoinColumn(name = "rootProject_ownId", referencedColumnName = "ownId")
//    })
    private Project rootProject;

    @Embedded
    private DateLog dateLog;
}
