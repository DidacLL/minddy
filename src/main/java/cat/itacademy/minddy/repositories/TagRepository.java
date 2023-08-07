package cat.itacademy.minddy.repositories;

import cat.itacademy.minddy.data.config.TagId;
import cat.itacademy.minddy.data.dao.Tag;
import cat.itacademy.minddy.data.dto.TagDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, TagId> {
    @Query(nativeQuery = true,value = """
    SELECT t.name,t.is_visible,t.is_heritable FROM projects_tags tp
        NATURAL JOIN tags t
        WHERE t.user_id= :userId 
        AND tp.project_holder_id= :projectParentId 
        AND tp.project_own_id= :projectOwnId
        AND t.is_visible = :visible
        ORDER BY (SELECT COUNT(*) FROM projects_tags tp2 WHERE tp2.tags_name = t.name AND tp2.tags_user_id=t.user_id) DESC
        
""")
    List<TagDTO> getAllProjectTags(String userId, String projectParentId, String projectOwnId, boolean visible);
    @Query(nativeQuery = true,value = """
    SELECT t.name,t.is_visible,t.is_heritable FROM notes_tags tn
        NATURAL JOIN tags t
        WHERE t.user_id= :userId 
        AND tn.note_id= :noteId 
        AND t.is_visible = :visible
        ORDER BY (SELECT COUNT(*) FROM notes_tags tn2 WHERE tn2.tags_name = t.name AND tn2.tags_user_id=t.user_id) DESC
""")
    List<TagDTO> getAllNoteTags(String noteId, String userId, boolean visible);
    @Query(nativeQuery = true,value = """
    SELECT t.name FROM tags t WHERE t.user_id= :userId AND t.name LIKE CONCAT('%',CONCAT(:nameLike,'%'))
""")
    List<String> getTagsLike(String userId, String nameLike);

    @Query(nativeQuery = true,value = """
            SELECT (
            SELECT COUNT(*) FROM projects_tags tp WHERE tp.tags_user_id=:userId AND tp.tags_name=:tagName
            )+(SELECT COUNT(*) FROM notes_tags tn WHERE tn.tags_user_id=:userId AND tn.tags_name=:tagName)
            """)
    int countTagUses(String userId, String tagName);
    @Query(nativeQuery = true,value = """
        SELECT * FROM tags t WHERE t.user_id=:userId AND t.name IN
         (SELECT nt.tags_name FROM notes_tags nt WHERE nt.tags_user_id =:userId AND nt.note_id=:noteId)
""")
    List<Tag> getNoteTagsEntity(String userId, UUID noteId);
}
