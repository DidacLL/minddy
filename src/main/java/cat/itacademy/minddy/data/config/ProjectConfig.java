package cat.itacademy.minddy.data.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ProjectConfig {
    int sprintDuration;
    @Enumerated(value = EnumType.STRING)
    RepeatMode sprintUnit;
    int weekStart;
    int dayStart; //hour
    int dayClose; //hour

    public int getSprintDuration() {
        return sprintDuration;
    }
}
