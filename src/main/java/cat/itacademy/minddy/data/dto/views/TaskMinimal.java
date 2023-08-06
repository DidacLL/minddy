package cat.itacademy.minddy.data.dto.views;

import cat.itacademy.minddy.data.dao.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;
@AllArgsConstructor
@Getter
public class TaskMinimal {
    private UUID id;
    private LocalDate date;
    private String holder;
    private String name;
    private String holderName;

    public static TaskMinimal fromEntity(Task task){
        return new TaskMinimal(task.getId(),task.getDate(),task.getHolder().getId().toString(),task.getName(),task.getHolder().getName());

    }
}
