package cat.itacademy.minddy.data.dao.trackers;

import cat.itacademy.minddy.data.config.RepeatMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Enumerated;
import java.util.List;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SprintTracker extends ProjectTracker{
    int sprintDuration;
    @Enumerated
    RepeatMode sprintUnit;
    int weekStart;
    int dayStart; //hour
    int dayClose; //hour
    List<String> currentTasks;



}
