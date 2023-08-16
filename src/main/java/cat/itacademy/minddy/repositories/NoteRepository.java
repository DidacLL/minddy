package cat.itacademy.minddy.repositories;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.NoteType;
import cat.itacademy.minddy.data.dao.Note;
import cat.itacademy.minddy.data.dto.NoteDTO;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.data.dto.views.NoteMinimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    @Query(nativeQuery = true,value = """
    SELECT  * FROM notes n WHERE n.user= :userId
    AND n.parent_id =:parentId
    AND n.holder_id=:holderId
    AND n.id= :noteId
""")
    Optional<Note> getNote(String userId,String parentId,String holderId, String noteId);
    @Query("SELECT new cat.itacademy.minddy.data.dto.TagDTO(t.id.name, t.isVisible, t.isHeritable) FROM Note n JOIN n.tags t WHERE n.holder.id.userId = :userId AND n.id = :noteId")
    List<TagDTO> getNoteTags(String userId,UUID noteId);
    @Query(
            value = """
            SELECT NEW cat.itacademy.minddy.data.dto.NoteDTO(
                n.id,
                n.name,
                n.body,
                n.type,
                n.isVisible)
                   
            FROM Note n WHERE n.holder.id.userId=:userId AND n.name LIKE :name AND
            :tags IN (SELECT t.id.name FROM n.tags t)
            """
    )
    List<NoteDTO> searchByNameAndTag(@Param(value = "userId") String userId, @Param(value = "name")String name, @Param(value = "tags") String tags);

    @Query(nativeQuery = true,
            value = """
            SELECT n.id,
                n.name,
                n.body,
                n.type,
                n.is_visible
            
            FROM notes n
            WHERE n.user=:userId AND n.name LIKE CONCAT('%',:name,'%') AND
            (SELECT COUNT(*) FROM notes_tags nt WHERE nt.tags_user_id=:userId AND nt.note_id = n.id AND nt.tags_name IN (:tags)) = (SELECT COUNT(*)FROM tags t WHERE t.name IN (:tags))
            """
    )
    List<NoteDTO> searchByNameAndTags(@Param(value = "userId") String userId,@Param(value = "name")String name, @Param(value = "tags")List<String> tags);

    @Query(value = """
    SELECT NEW cat.itacademy.minddy.data.dto.views.NoteMinimal(n.id,n.name) FROM Note n
    WHERE n.holder.id=:projectId AND n.isVisible=:visible
""")
    Page<NoteMinimal> getNotesByHolder(HierarchicalId projectId, Pageable pageable, boolean visible);

    @Query(nativeQuery = true,value = """
    SELECT * FROM notes n WHERE n.user= :userId AND NOT n.is_visible
    AND(
        ( n.parent_id= :parentId AND n.holder_id= :holderId)
        OR (n.parent_id LIKE CONCAT(:parentId, :holderId,'%')))
    AND (SELECT COUNT(*) FROM notes_tags nt WHERE nt.note_id=n.id AND nt.tags_name='_TASK_')<1
    ORDER BY n.creation_date
""")
    List<Note> getSystemNotes(String userId,String parentId,String holderId );

@Query("""

        SELECT new cat.itacademy.minddy.data.dto.views.NoteMinimal(n.id, n.name)
        FROM Note n JOIN n.tags t
         WHERE n.holder.id.userId = :userId AND t.id.name IN :tagNames AND n.isVisible = true
         AND n.type IN :types
         AND (n.holder.id.holderId = :parentId
            AND n.holder.id.ownId = :holderId
            OR n.holder.id.holderId LIKE CONCAT(:parentId, :holderId, '%')
         )
         """)
    Page<NoteMinimal> searchByTagAndType(String userId, String parentId, String holderId, Pageable pageRequest, String[] tagNames, NoteType...types);

    @Query(value = """
    SELECT NEW cat.itacademy.minddy.data.dto.views.NoteMinimal(n.id,n.name)  FROM Note n
    WHERE n.holder.id.userId=:userId AND n.isVisible AND n.type IN :types AND(
        (n.holder.id.holderId=:parentId AND n.holder.id.ownId=:holderId)
        OR (n.holder.id.holderId LIKE CONCAT(:parentId,:holderId,'%'))
        AND  n.name LIKE CONCAT('%',:name,'%')
    )
""")
    Page<NoteMinimal> searchByNameAndType(String userId, String parentId, String holderId, Pageable pageable, String name, NoteType... types);

    @Query(value = """
     SELECT NEW cat.itacademy.minddy.data.dto.views.NoteMinimal(n.id,n.name)  FROM Note n
    WHERE n.holder.id.userId=:userId AND n.isVisible AND n.type IN :types AND(
        (n.holder.id.holderId=:parentId AND n.holder.id.ownId=:holderId)
        OR (n.holder.id.holderId LIKE CONCAT(:parentId,:holderId,'%'))
        AND  n.name LIKE CONCAT('%',:text,'%')
    )
""")
    Page<NoteMinimal> searchByBodyAndType(String userId, String parentId, String holderId,Pageable pageable, String text, NoteType... types);
}
