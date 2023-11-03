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
    private String state;
    private String priority;

    public TaskData(UUID id, String name, String description, LocalDate date, String holder, TaskState state, Priority priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.holder = holder;
        this.state = state.name();
        this.priority = priority.name();
    }

    public static TaskData fromEntity(Task task){
        return new TaskData(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getDate(),
                task.getHolder().getId().toString(),
                task.getState().name(),
                task.getPriority().name()
        );
    }

    public static TaskData fromDTO(String holder,TaskDTO task){
        return new TaskData(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getDate(),
                holder,
                task.getState().name(),
                task.getPriority().name()
        );
    }

}
