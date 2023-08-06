package cat.itacademy.minddy.data.dao.trackers;

import cat.itacademy.minddy.data.config.SystemEvent;
import cat.itacademy.minddy.data.config.SystemTarget;
import cat.itacademy.minddy.data.dao.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity @Inheritance(strategy = InheritanceType.JOINED)
public abstract class ProjectTracker implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false,updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user", referencedColumnName = "user_id"),
            @JoinColumn(name = "parent_id", referencedColumnName = "holder_id"),
            @JoinColumn(name = "holder_id", referencedColumnName = "own_id")
    })
    Project owner;
    int value,max;
    @Column(columnDefinition = "VARCHAR(30)")
    String name;
    String prompt;
    @Enumerated
    SystemTarget target;
    @Enumerated
    SystemEvent event;
    boolean auto;

    public ProjectTracker(int max, boolean auto, SystemEvent events) {
        this.value = 0;
        this.auto = auto;
        this.max=max;
        this.event=events;
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
