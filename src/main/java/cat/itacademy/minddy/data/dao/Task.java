package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.*;
import cat.itacademy.minddy.data.dto.TaskDTO;
import cat.itacademy.minddy.utils.converters.SubTaskListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity @Table(name = "tasks")
@NoArgsConstructor
@Getter @Setter
public class Task {
    @EmbeddedId
    private HierarchicalId id;
    //-------------------------READABLE STUFF
    private String name;
    private String description;
    @Convert(converter = SubTaskListConverter.class)
    @Column(columnDefinition = "JSON")
    private List<SubTask> subtasks;
    //-------------------------PROJECT STATE
    private TaskState state;
    //------------------------- DEAD LINE
    private LocalDate date;
    @Embedded
    private DateLog dateLog;
    @Enumerated(EnumType.STRING)
    private RepeatMode repetition;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private int repeatValue;

    static Task fromDTO(TaskDTO dto){
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
