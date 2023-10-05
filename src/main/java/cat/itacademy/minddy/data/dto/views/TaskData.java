package cat.itacademy.minddy.data.dto.views;

import cat.itacademy.minddy.data.config.Priority;
import cat.itacademy.minddy.data.config.TaskState;
import cat.itacademy.minddy.data.dao.Task;
import cat.itacademy.minddy.data.dto.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class TaskData {
    private UUID id;
    private String name;
    private String description;
    private LocalDate date;
    private String holder;
    private TaskState state;
    private Priority priority;

    public static TaskData fromEntity(Task task){
        return new TaskData(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getDate(),
                task.getHolder().getId().toString(),
                task.getState(),
                task.getPriority()
        );
    }

    public static TaskData fromDTO(TaskDTO task){
        return new TaskData(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getDate(),
                "",
                task.getState(),
                task.getPriority()
        );
    }

}
