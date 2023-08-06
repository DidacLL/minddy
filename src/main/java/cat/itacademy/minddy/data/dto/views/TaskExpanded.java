package cat.itacademy.minddy.data.dto.views;

import cat.itacademy.minddy.data.config.Priority;
import cat.itacademy.minddy.data.config.TaskState;
import cat.itacademy.minddy.data.dao.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;
@AllArgsConstructor
@Getter
public class TaskExpanded {
    private UUID id;
    private String name;
    private String description;
    private LocalDate date;
    private String owner;
    private TaskState state;
    private Priority priority;
    private String holderName;

    public static TaskExpanded fromEntity(Task task){
        return new TaskExpanded(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getDate(),
                task.getHolder().getId().toString(),
                task.getState(),
                task.getPriority(),
                task.getHolder().getName()
        );
    }

}
