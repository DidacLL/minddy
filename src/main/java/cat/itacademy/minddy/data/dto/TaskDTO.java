package cat.itacademy.minddy.data.dto;

import cat.itacademy.minddy.data.config.Priority;
import cat.itacademy.minddy.data.config.RepeatMode;
import cat.itacademy.minddy.data.config.TaskState;
import cat.itacademy.minddy.data.dao.Task;
import cat.itacademy.minddy.data.dto.views.TaskData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter @Setter
public class TaskDTO {
    private UUID id;
    private String name;
    private String description;
//    private List<SubTask> subtasks;
    private TaskState state=TaskState.TODO;
    private LocalDate date;
    private RepeatMode repetition=null;
    private Priority priority=Priority.NORMAL;
    private int repeatValue=0;
    private String holderID;

    public TaskDTO( String name, String description, TaskState state, LocalDate date, Priority priority,String holderID) {
        this.name = name;
        this.description = description;
        this.state = state;
        this.date = date;
        this.priority = priority;
        this.holderID=holderID;
    }

    public static TaskDTO fromData(TaskData taskData) {
        return new TaskDTO(
                taskData.getName(),
                taskData.getDescription(),
                TaskState.parse(taskData.getState()),
                taskData.getDate(),
                Priority.parse(taskData.getPriority()),
                taskData.getHolder()
        ).setId(taskData.getId());
    }

    public boolean isFulfilled(){
        return this.name!=null && !this.name.trim().isEmpty();
    }

    public static TaskDTO fromEntity(Task entity){
        return new TaskDTO()
                .setId(entity.getId())
                .setName(entity.getName())
                .setDescription(entity.getDescription())
//                .setSubtasks(entity.getSubtasks())
                .setState(entity.getState())
                .setDate(entity.getDate())
                .setRepetition(entity.getRepetition())
                .setPriority(entity.getPriority())
                .setRepeatValue(entity.getRepeatValue());
//                .setHolderID(entity.getHolder().getId());
    }

}
