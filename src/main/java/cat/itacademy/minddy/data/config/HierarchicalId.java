package cat.itacademy.minddy.data.config;

import cat.itacademy.minddy.utils.HexConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class HierarchicalId implements Serializable {
    UUID userId;
    String holderId;
    @Column(columnDefinition = "TINYINT")
    @Convert(converter= HexConverter.class)
    String ownId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HierarchicalId hierarchicalId = (HierarchicalId) o;

        if (!getUserId().equals(hierarchicalId.getUserId())) return false;
        if (!getHolderId().equals(hierarchicalId.getHolderId())) return false;
        return getOwnId().equals(hierarchicalId.getOwnId());
    }

    @Override
    public int hashCode() {
        int result = getUserId().hashCode();
        result = 31 * result + getHolderId().hashCode();
        result = 31 * result + getOwnId().hashCode();
        return result;
    }
}
