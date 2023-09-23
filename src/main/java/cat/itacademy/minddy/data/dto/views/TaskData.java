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
public class TaskData {
    private UUID id;
    private String name;
    private String description;
    private LocalDate date;
    private String holder;
    private TaskState state;
    private Priority priority;
    private String holderName;

    public static TaskData fromEntity(Task task){
        return new TaskData(
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
