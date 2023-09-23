package cat.itacademy.minddy.data.html;

import cat.itacademy.minddy.data.dto.views.TaskMinimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class TodayReport {
    private LocalDate date;
    private List<TaskMinimal> tasks;
    private List<TaskMinimal> missedTasks;
    private List<String> nearDeathLines;

}
