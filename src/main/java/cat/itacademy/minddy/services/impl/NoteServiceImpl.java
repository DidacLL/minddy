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
import cat.itacademy.minddy.data.dto.views.NoteFullView;
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
import java.util.UUID;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    NoteRepository repo;
    @Autowired
    TagService tagService;

    @PersistenceContext
    private EntityManager em;

    @Override
    public NoteDTO createNewNote(HierarchicalId projectId, NoteDTO note, TagDTO... tags) throws MinddyException {
        try {
            return NoteDTO.fromEntity(repo.save(
                    generateFullEntity(projectId, note).addTag(
                            Arrays.stream(tags).map(tag -> Tag.fromDTO(tag, projectId.getUserId())).toArray(Tag[]::new)
                    )
            ));
        } catch (Exception e) {
            throw new MinddyException(400, "Validation failed");
        }
    }


    @Override
    public NoteDTO createTaskNote(HierarchicalId projectId, String taskId, NoteDTO note, TagDTO... tags) throws MinddyException {
        try {
            var tagArr = new Tag[tags.length + 1];
            tagArr[0] = em.getReference(Tag.class, new TagId(projectId.getUserId(), DefaultTags.TASK_NOTE.getTagName()));
            for (int i = 0; i < tags.length; i++) tagArr[i + 1] = Tag.fromDTO(tags[i], projectId.getUserId());
            return NoteDTO.fromEntity(
                    repo.save(
                            generateFullEntity(projectId, note).addTag(tagArr).setName(taskId).setVisible(false)));
        } catch (Exception e) {
            throw new MinddyException(400, "Validation failed");
        }
    }

    @Override
    public NoteDTO updateNote(HierarchicalId projectId, NoteDTO note, TagDTO... tags) throws MinddyException {
        try {
            var old = repo.findById(note.getId()).orElseThrow();
            var newTags = Arrays.stream(tags).toList();
            var oldTags = tagService.getNoteTagsEntity(projectId.getUserId(), old.getId()).stream().map(TagDTO::fromEntity).toList();
            if (note.getName() != null && !note.getName().trim().isEmpty() && !note.getName().equalsIgnoreCase(old.getName()))
                old.setName(note.getName());
            if (note.getBody() != null && !note.getBody().trim().isEmpty() && !note.getBody().equalsIgnoreCase(old.getBody()))
                old.setBody(note.getBody());
            if (note.getType() != null && !note.getType().equals(old.getType()))
                old.setType(note.getType());

            if (tags.length > 0) {
                for (TagDTO dto : oldTags)
                    if (!newTags.contains(dto)) old.quitTag(Tag.fromDTO(dto, projectId.getUserId()));
                for (TagDTO dto : tags) old.addTag(Tag.fromDTO(dto, projectId.getUserId()));
            }

            return NoteDTO.fromEntity(repo.save(old));
        } catch (Exception e) {
            throw new MinddyException(400, "Inconsistent note data");
        }


    }

    @Override
    public NoteDTO deleteNote(HierarchicalId projectId, UUID noteId) throws MinddyException {
        var note = repo.getNote(projectId.getUserId(),
                        projectId.getHolderId(),
                        projectId.getOwnId(),
                        String.valueOf(noteId))
                .orElseThrow(() -> new MinddyException(400, "Inconsistent Note Data"));
        repo.delete(note);
        return NoteDTO.fromEntity(note);
    }

    @Override
    public Page<NoteMinimal> getAllVisibleNotes(HierarchicalId projectId, int page, int pageSize) {
        return repo.getNotesByHolder(projectId, PageRequest.of(page, pageSize), true);
    }

    @Override
    public List<NoteDTO> getSystemNotes(HierarchicalId projectId) {
        return repo.getSystemNotes(projectId.getUserId(),
                projectId.getHolderId(),
                projectId.getOwnId()).stream().map(NoteDTO::fromEntity).toList();
    }

    @Override
    public List<NoteMinimal> getTaskNotes(HierarchicalId projectId, String taskId) {
        return repo.searchByNameAndTag(
                projectId.getUserId(),
                taskId,
                DefaultTags.TASK_NOTE.getTagName()
        );

    }

    @Override
    public NoteDTO getNote(HierarchicalId projectId, UUID noteId) throws MinddyException {
        return NoteDTO.fromEntity((repo.getNote(projectId.getUserId(),
                projectId.getHolderId(),
                projectId.getOwnId(),
                String.valueOf(noteId)).orElseThrow(() -> new MinddyException(404, "Note " + noteId + " user:" + projectId.getUserId() + " not found"))));
    }


    @Override
    public NoteFullView getFullNote(String user, UUID noteId) throws MinddyException {
        List<TagDTO> noteTags = repo.getNoteTags(user, noteId);
        return new NoteFullView(
                NoteDTO.fromEntity(repo.getNote(user,
                        String.valueOf(noteId)
                ).orElseThrow(() -> new MinddyException(404, "Note not found"))),
                noteTags
        );
    }
    @Override
    public NoteFullView getFullNote(HierarchicalId projectId, UUID noteId) throws MinddyException {
        List<TagDTO> noteTags = repo.getNoteTags(projectId.getUserId(), noteId);
        return new NoteFullView(
                NoteDTO.fromEntity(repo.getNote(projectId.getUserId(),
                        projectId.getHolderId(),
                        projectId.getOwnId(),
                        String.valueOf(noteId)
                ).orElseThrow(() -> new MinddyException(404, "Note not found"))),
                noteTags
        );
    }

    @Override
    public Page<NoteMinimal> searchNotesByTag(HierarchicalId projectId, int page, int pageSize, String[] tagNames, NoteType... types) {
//        int[] arrType = Arrays.stream(types).mapToInt(NoteType::ordinal).toArray();
        return repo.searchByTagAndType(projectId.getUserId(), projectId.getHolderId(), projectId.getOwnId(), PageRequest.of(page, pageSize), tagNames, types);
    }

    @Override
    public Page<NoteMinimal> searchNotesByName(HierarchicalId projectId, int page, int pageSize, String name, NoteType... types) {
        return repo.searchByNameAndType(projectId.getUserId(), projectId.getHolderId(), projectId.getOwnId(), PageRequest.of(page, pageSize), name, types==null||types.length==0?NoteType.values():types);
    }

    @Override
    public Page<NoteMinimal> searchNotesByContent(HierarchicalId projectId, int page, int pageSize, String text, NoteType... types) {
        return repo.searchByBodyAndType(projectId.getUserId(), projectId.getHolderId(), projectId.getOwnId(), PageRequest.of(page, pageSize), text, types==null||types.length==0?NoteType.values():types);
    }


    //-----------------------------------------------------------------------------------------------------PRIVATE METHODS

    private Note generateFullEntity(HierarchicalId projectId, NoteDTO note) {
        Project reference = em.getReference(Project.class, projectId);
        return Note.fromDTO(note).setHolder(reference).setTags(tagService.getNoteTagsEntity(projectId.getUserId(), note.getId()));
    }


}
