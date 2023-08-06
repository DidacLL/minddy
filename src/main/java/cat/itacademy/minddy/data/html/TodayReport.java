package cat.itacademy.minddy.data.html;

import cat.itacademy.minddy.data.dto.views.TaskMinimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
@AllArgsConstructor
@Getter
public class TodayReport {
    private LocalDate date;
    private ArrayList<TaskMinimal> tasks;
    private int missedTasks;
    private String[] nearDeathLines;

}
