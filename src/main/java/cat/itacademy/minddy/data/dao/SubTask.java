package cat.itacademy.minddy.data.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter @Setter
public class SubTask implements Serializable {
    private String name;
    private boolean done;

    public SubTask(String name) {
        this.name = name;
        this.done=false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubTask subTask = (SubTask) o;

        if (isDone() != subTask.isDone()) return false;
        return getName().equals(subTask.getName());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + (isDone() ? 1 : 0);
        return result;
    }
}
