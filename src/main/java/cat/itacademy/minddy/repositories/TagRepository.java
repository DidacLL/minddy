package cat.itacademy.minddy.repositories;

import cat.itacademy.minddy.data.config.TagId;
import cat.itacademy.minddy.data.dao.Tag;
import cat.itacademy.minddy.data.dto.TagDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, TagId> {
    @Query(nativeQuery = true,value = """
    SELECT t.name,t.is_visible,t.is_heritable FROM tags_projects tp 
        NATURAL JOIN tags t
        WHERE t.user_id= :userId 
        AND tp.projects_holder_id= :projectParentId 
        AND tp.projects_own_id= :projectOwnId
        AND t.is_visible = :visible
        ORDER BY (SELECT COUNT(*) FROM tags_projects tp2 WHERE tp2.tag_name = t.name AND tp2.tag_user_id=t.user_id) DESC
        
""")
    List<TagDTO> getAllProjectTags(String userId, String projectParentId, String projectOwnId, boolean visible);
    @Query(nativeQuery = true,value = """
    SELECT t.name,t.is_visible,t.is_heritable FROM tags_notes tn 
        NATURAL JOIN tags t
        WHERE t.user_id= :userId 
        AND tn.notes_id= :noteId 
        AND t.is_visible = :visible
        ORDER BY (SELECT COUNT(*) FROM tags_notes tn2 WHERE tn2.tag_name = t.name AND tn2.tag_user_id=t.user_id) DESC
""")
    List<TagDTO> getAllNoteTags(String noteId, String userId, boolean visible);
    @Query(nativeQuery = true,value = """
    SELECT t.name FROM tags t WHERE t.user_id= :userId AND t.name LIKE CONCAT('%',CONCAT(:nameLike,'%'))
""")
    List<String> getTagsLike(String userId, String nameLike);

    @Query(nativeQuery = true,value = """
            SELECT (
            SELECT COUNT(*) FROM tags_projects tp WHERE tp.tag_user_id=:userId AND tp.tag_name=:tagName
            )+(SELECT COUNT(*) FROM tags_notes tn WHERE tn.tag_user_id=:userId AND tn.tag_name=:tagName)
            """)
    int countTagUses(String userId, String tagName);
}
