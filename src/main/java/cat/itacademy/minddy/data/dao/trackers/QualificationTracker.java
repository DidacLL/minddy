package cat.itacademy.minddy.data.dao.trackers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor
public class QualificationTracker extends ProjectTracker {
    int total;
    boolean subProjectAVG;

}
