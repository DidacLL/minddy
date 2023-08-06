package cat.itacademy.minddy.services.impl;

import cat.itacademy.minddy.data.config.DefaultTags;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.NoteType;
import cat.itacademy.minddy.data.config.TagId;
import cat.itacademy.minddy.data.dao.Note;
import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dao.Tag;
import cat.itacademy.minddy.data.dto.NoteDTO;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.data.dto.views.NoteExpanded;
import cat.itacademy.minddy.data.dto.views.NoteMinimal;
import cat.itacademy.minddy.repositories.NoteRepository;
import cat.itacademy.minddy.services.NoteService;
import cat.itacademy.minddy.services.TagService;
import cat.itacademy.minddy.utils.MinddyException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    NoteRepository repo;
    @Autowired
    TagService tagService;

    @PersistenceContext
    private EntityManager em;

    @Override
    public NoteDTO createNewNote(HierarchicalId projectId, NoteDTO note) throws MinddyException {
        try {
            return NoteDTO.fromEntity(repo.save(generateFullEntity(projectId, note)));
        }catch (Exception e){
            throw new MinddyException(400,"Validation failed");
        }
    }


    @Override
    public NoteDTO createTaskNote(HierarchicalId projectId, String taskId, NoteDTO note) throws MinddyException {
        try {
            return NoteDTO.fromEntity(
                    repo.save(
                          generateFullEntity(projectId, note).addTag(em.getReference(Tag.class,new TagId(projectId.getUserId(), DefaultTags.TASK_NOTE.getTagName()))
                    ).setName(taskId).setVisible(false)));
        }catch (Exception e){
            throw new MinddyException(400,"Validation failed");
        }
    }

    @Override
    public NoteDTO updateNote(HierarchicalId projectId, NoteDTO note) throws MinddyException {
        try {
            var old = getFullNote(projectId,note.getId().toString());
            if (note.getName()!=null&&!note.getName().trim().isEmpty()&&note.getName().equalsIgnoreCase(old.getName()))
                old.setName(note.getName());
            if (note.getBody()!=null&&!note.getBody().trim().isEmpty()&&note.getBody().equalsIgnoreCase(old.getBody()))
                old.setBody(note.getBody());
            if (note.getType()!=null&& !note.getType().equals(old.getType()))
                old.setType(note.getType());
            if (!note.getTags().isEmpty() && !note.getTags().equals(old.getTags())) old.setTags(note.getTags());

            return NoteDTO.fromEntity(repo.save(Note.fromDTO(old)));
        }catch (Exception e){
            throw new MinddyException(400,"Inconsistent note data");
        }


    }

    @Override
    public NoteDTO deleteNote(HierarchicalId projectId, String noteId) throws MinddyException {
        var note= repo.getNote(projectId,noteId).orElseThrow(()->new MinddyException(400,"Inconsistent Note Data"));
        repo.delete(note);
        return NoteDTO.fromEntity(note);
    }

    @Override
    public Page<NoteMinimal> getAllVisibleNotes(HierarchicalId projectId, int page, int pageSize) throws MinddyException {
        return repo.getNotesByHolder(projectId, PageRequest.of(page,pageSize),true);
    }

    @Override
    public List<NoteDTO> getSystemNotes(HierarchicalId projectId) throws MinddyException {
        return repo.getSystemNotes(projectId.getUserId(),
                projectId.getHolderId(),
                projectId.getOwnId()).stream().map(NoteDTO::fromEntity).toList();
    }

    @Override
    public List<NoteExpanded> getTaskNotes(HierarchicalId projectId, String taskId) throws MinddyException {
        return repo.searchByNameAndTags(
                projectId,
                taskId,
                DefaultTags.TASK_NOTE.getTagName()
        );

    }

    @Override
    public NoteDTO getFullNote(HierarchicalId projectId, String noteId) throws MinddyException {
        NoteDTO noteDTO = NoteDTO.fromEntity((repo.getNote(projectId, noteId).orElseThrow(() -> new MinddyException(404, "Note not found"))));
        //FIXME check if tags are properly set, if not set it manually by the autowired tagService
        return noteDTO;
    }

    @Override
    public Page<NoteMinimal> searchNotesByTag(HierarchicalId projectId, int page, int pageSize, String[] tagNames, NoteType... types) throws MinddyException {
        int[] arrType = Arrays.stream(types).mapToInt(Enum::ordinal).toArray();
        return repo.searchByTagAndType(projectId.getUserId(), projectId.getHolderId(), projectId.getOwnId(),PageRequest.of(page,pageSize), tagNames,arrType );
    }

    @Override
    public Page<NoteMinimal> searchNotesByName(HierarchicalId projectId, int page, int pageSize, String name, NoteType... types) throws MinddyException {
        int[] arrType = Arrays.stream(types).mapToInt(Enum::ordinal).toArray();
        return repo.searchByNameAndType(projectId.getUserId(), projectId.getHolderId(), projectId.getOwnId(),PageRequest.of(page,pageSize), name,arrType );    }

    @Override
    public Page<NoteMinimal> searchNotesByContent(HierarchicalId projectId, int page, int pageSize, String text, NoteType... types) throws MinddyException {
        int[] arrType = Arrays.stream(types).mapToInt(Enum::ordinal).toArray();
        return repo.searchByBodyAndType(projectId.getUserId(), projectId.getHolderId(), projectId.getOwnId(), PageRequest.of(page,pageSize), text,arrType );
    }


    //-----------------------------------------------------------------------------------------------------PRIVATE METHODS

    private Note generateFullEntity(HierarchicalId projectId, NoteDTO note) throws MinddyException {
        Project reference = em.getReference(Project.class, projectId);
        return Note.fromDTO(note).setHolder(reference).setTags(getTagsFromDTO(projectId, note));
    }

    private List<Tag> getTagsFromDTO(HierarchicalId projectId, NoteDTO note) throws MinddyException {
        var arr= note.getTags().stream().map(TagDTO::getName).toList();
        return tagService.getTagsFromList(projectId.getUserId(), arr.toArray(new String[0]));
    }


}
