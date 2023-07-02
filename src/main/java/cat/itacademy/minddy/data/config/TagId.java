package cat.itacademy.minddy.data.config;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagId implements Serializable {
    @Column(name = "user_id")
    private UUID userId;
    @Column(columnDefinition = "VARCHAR(36)")
    private String name;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagId tagId = (TagId) o;

        if (!getUserId().equals(tagId.getUserId())) return false;
        return getName().equals(tagId.getName());
    }

    @Override
    public int hashCode() {
        int result = getUserId().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }
}
