package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.config.Priority;
import cat.itacademy.minddy.data.config.RepeatMode;
import cat.itacademy.minddy.data.config.TaskState;
import cat.itacademy.minddy.data.dto.TaskDTO;
import cat.itacademy.minddy.utils.converters.SubTaskListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity @Table(name = "tasks", indexes = {
        @Index(name = "idx_task_state",columnList ="state")
})

@NoArgsConstructor
@Getter @Setter
public class Task {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID id;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user", referencedColumnName = "user_id"),
            @JoinColumn(name = "parent_id", referencedColumnName = "holder_id"),
            @JoinColumn(name = "holder_id", referencedColumnName = "own_id")
    })
    private Project holder;
    //-------------------------READABLE STUFF
    @Column(nullable = false,columnDefinition = "VARCHAR(30)")
    private String name;
    private String description;
    @Convert(converter = SubTaskListConverter.class)
    @Column(columnDefinition = "JSON")
    private List<SubTask> subtasks;
    //-------------------------PROJECT STATE
    @Enumerated
    @Column(nullable = false,name = "state")
    private TaskState state;
    //------------------------- DEAD LINE
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = true)
    private LocalDate date=null;
    @Embedded
    private DateLog dateLog;
    @Enumerated
    private RepeatMode repetition;
    @Column(name = "priority", nullable = false)
    @Enumerated
    private Priority priority;

    private int repeatValue,repeatLimit;

    /**Entity builder method, it only sets independent fields, holder value must be set externally before persist
     * @param dto Fulfilled TaskDTO non dependent fields
     * @return Task Entity
     */
    public static Task fromDTO(TaskDTO dto){
        return new Task()
                .setId(dto.getId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setSubtasks(dto.getSubtasks())
                .setState(dto.getState())
                .setDate(dto.getDate())
                .setRepetition(dto.getRepetition())
                .setPriority(dto.getPriority())
                .setRepeatValue(dto.getRepeatValue());
    }
}
