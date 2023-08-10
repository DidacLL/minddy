package cat.itacademy.minddy.data.config;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class HierarchicalId implements Serializable {
    @Column(name = "user_id",updatable = false,nullable = false,columnDefinition = "VARCHAR(36)")
    private String userId;
    @Column(name = "holder_id",nullable = false,columnDefinition = "VARCHAR(36)")
    private String holderId;
    @Column(name = "own_id",nullable = false,columnDefinition = "VARCHAR(2)")
//    @Convert(converter= HexConverter.class)
    private String ownId;

    public HierarchicalId(String userId, String id) {
        this.userId = userId;
        this.holderId= id.substring(0,id.length()-2);
        this.ownId = id.substring(id.length()-2);
    }

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

    public String getFirstParent(){
        int length = holderId.length();
        if(length >=2&& length %2==0) return holderId.substring(length -2);
        return null;
    }

    public List<String> getAllParents() {
        int length = holderId.length();
        if(length >=2&& length %2==0){
            return IntStream.range(0, length / 2)
                    .mapToObj(i -> holderId.substring(i * 2, i * 2 + 2))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return null;
    }

    @Override
    public String toString() {
        return holderId+ ownId;
    }
}
