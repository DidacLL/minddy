package cat.itacademy.minddy.data.dto.views;

import cat.itacademy.minddy.data.dao.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;
@AllArgsConstructor
@Getter
public class TaskMinimal {
    private UUID id;

    public static TaskMinimal fromEntity(Task task){
        return new TaskMinimal(task.getId());

    }
    public static TaskMinimal fromTuple(String tuple){

       return new TaskMinimal(UUID.fromString(tuple));
    }
}
