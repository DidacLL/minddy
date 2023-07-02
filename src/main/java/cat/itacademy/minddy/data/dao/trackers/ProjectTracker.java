package cat.itacademy.minddy.data.dao.trackers;

import cat.itacademy.minddy.data.config.SystemEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTracker implements Serializable {
    int value,max;
    List<SystemEvent> event;
    boolean auto;

    public ProjectTracker(int max, boolean auto, SystemEvent ... events) {
        this.value = 0;
        this.auto = auto;
        this.max=max;
        this.event=new ArrayList<>();
        if(events.length>0)Collections.addAll(event, events);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectTracker that = (ProjectTracker) o;

        if (getValue() != that.getValue()) return false;
        if (getMax() != that.getMax()) return false;
        if (isAuto() != that.isAuto()) return false;
        return getEvent().equals(that.getEvent());
    }

    @Override
    public int hashCode() {
        int result = getValue();
        result = 31 * result + getMax();
        result = 31 * result + getEvent().hashCode();
        result = 31 * result + (isAuto() ? 1 : 0);
        return result;
    }

}
