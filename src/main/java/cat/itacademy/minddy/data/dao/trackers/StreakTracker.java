package cat.itacademy.minddy.data.dao.trackers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class StreakTracker extends ProjectTracker {
    List<Integer> history;
    boolean auto;

}
