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
    private String name;
    private LocalDate date;
    private String holder;
    private String holderName;

    public static TaskMinimal fromEntity(Task task){
        return new TaskMinimal(task.getId(),task.getName(),task.getDate(),task.getHolder().getId().toString(),task.getHolder().getName());

    }
    public static TaskMinimal fromTuple(Object[] tuple){
        UUID id = UUID.fromString((String) tuple[0]);
        LocalDate retDate = ((Date) tuple[1]).toLocalDate();
        String name = (String) tuple[2];
        String holder = (String) tuple[3];
        String holderName = (String) tuple[4];
       return new TaskMinimal(id, name,retDate, holder,  holderName);
    }
}
