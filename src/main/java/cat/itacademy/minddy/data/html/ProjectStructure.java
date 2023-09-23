package cat.itacademy.minddy.data.html;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ProjectStructure {
    private LocalDate date;
    private ProjectNode root;
}
