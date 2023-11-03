package cat.itacademy.minddy.data.dto.views;

import cat.itacademy.minddy.data.dao.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class TaskRequest {

    private TaskData taskData;
    private String[] tags =new String[0];


     public static TaskRequest fromEntity(Task entity){
        return new TaskRequest(
              TaskData.fromEntity(entity), (String[]) entity.getTags().stream().map(t-> t.getId().getName()).toArray()
        );
    }

}
