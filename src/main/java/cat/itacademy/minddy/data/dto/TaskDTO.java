package cat.itacademy.minddy.data.dto;

import cat.itacademy.minddy.data.config.RepeatMode;
import cat.itacademy.minddy.data.config.TaskState;
import cat.itacademy.minddy.data.dao.SubTask;
import cat.itacademy.minddy.data.dao.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter @Setter
public class TaskDTO {
    private UUID id;
    private String name;
    private String description;
    private List<SubTask> subtasks;
    private TaskState state;
    private LocalDate date;
    private RepeatMode repetition;
    private int priority;
    private int repeatValue;

    static TaskDTO fromEntity(Task entity){
        return new TaskDTO()
                .setId(entity.getId())
                .setName(entity.getName())
                .setDescription(entity.getDescription())
                .setSubtasks(entity.getSubtasks())
                .setState(entity.getState())
                .setDate(entity.getDate())
                .setRepetition(entity.getRepetition())
                .setPriority(entity.getPriority())
                .setRepeatValue(entity.getRepeatValue());
    }

}
