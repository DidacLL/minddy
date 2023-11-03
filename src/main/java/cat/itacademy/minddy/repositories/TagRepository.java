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
//    @Query(nativeQuery = true, value = """
//                SELECT * FROM projects_tags tp
//                    NATURAL JOIN tags t
//                    WHERE t.user_id= :userId
//                    AND tp.project_holder_id= :projectParentId
//                    AND tp.project_own_id= :projectOwnId
//                    AND t.is_visible = :visible
//                    ORDER BY (SELECT COUNT(*) FROM projects_tags tp2 WHERE tp2.tags_name = t.name AND tp2.tags_user_id=t.user_id) DESC
//
//            """)
    @Query( value = """
            SELECT t FROM Project p JOIN p.tags t
            WHERE t.id.userId = :userId
            AND p.id.holderId = :projectParentId
            AND p.id.ownId = :projectOwnId
            AND t.isVisible = :visible
            ORDER BY (
                SELECT COUNT(*) FROM Project p2 JOIN p2.tags t2 WHERE t2.id.name = t.id.name AND t2.id.userId = t.id.userId
            ) DESC, t.id.name

                        """)
    List<Tag> getProjectTags(String userId, String projectParentId, String projectOwnId, boolean visible);
    @Query(value = """
                SELECT n.tags FROM Note n JOIN n.tags t WHERE n.holder.id.userId= :userId AND n.id=:noteId AND t.isVisible=:isVisible

            """)
//                    ORDER BY (SELECT COUNT(*) FROM notes_tags nt2 WHERE nt2.tags_name = t.name AND nt2.tags_user_id=t.user_id) DESC
//    @Query(value = """
//            SELECT t FROM Tag t WHERE t IN (
//                SELECT n.tags FROM Note n WHERE n.id=:noteId AND n.holder.id.userId=:userId
//            ) AND t.isVisible=:isVisible
//            ORDER BY (SELECT COUNT(n) FROM Note n JOIN n.tags nt WHERE nt = t) DESC""")
    List<Tag> getNoteTags(String userId, UUID noteId, boolean isVisible);

    @Query(value = """
            SELECT t FROM Tag t WHERE t IN (
                SELECT t.tags FROM Task t WHERE t.id=:taskId AND t.holder.id.userId=:userId
            ) AND t.isVisible=:visible
            ORDER BY (SELECT COUNT(t) FROM Task t2 JOIN t2.tags tt WHERE tt = t) DESC
            """)
    List<Tag> getTaskTags(UUID taskId, String userId, boolean visible);

    @Query(nativeQuery = true, value = """
            SELECT t.name FROM tags t WHERE t.user_id= :userId AND t.name LIKE CONCAT('%',CONCAT(:nameLike,'%'))
            ORDER BY (
            (SELECT COUNT(*) FROM projects_tags tp WHERE tp.tags_user_id=:userId AND tp.tags_name=t.name)
            +(SELECT COUNT(*) FROM notes_tags tn WHERE tn.tags_user_id=:userId AND tn.tags_name=t.name)
            +(SELECT COUNT(*) FROM tasks_tags tt WHERE tt.tags_user_id=:userId AND tt.tags_name=t.name))
            """)
    List<String> getTagsLike(String userId, String nameLike);

    @Query(nativeQuery = true, value = """
                SELECT (
                    (SELECT COUNT(*) FROM projects_tags tp WHERE tp.tags_user_id=:userId AND tp.tags_name=:tagName)
                    +(SELECT COUNT(*) FROM notes_tags tn WHERE tn.tags_user_id=:userId AND tn.tags_name=:tagName)
                    +(SELECT COUNT(*) FROM tasks_tags tt WHERE tt.tags_user_id=:userId AND tt.tags_name=:tagName)
                )
            """)
    int countTagUses(String userId, String tagName);


    @Query(value = """
    SELECT new cat.itacademy.minddy.data.dto.TagDTO(t.id.name, t.isHeritable,t.isVisible) FROM Tag t
    WHERE t.id.userId =:userId AND  t.isVisible=true
""")
    List<TagDTO> getAllUserTags(String userId);
}
