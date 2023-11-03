package cat.itacademy.minddy.services.impl;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.TagId;
import cat.itacademy.minddy.data.dao.Tag;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.repositories.TagRepository;
import cat.itacademy.minddy.services.TagService;
import cat.itacademy.minddy.utils.MinddyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository repo;

    @Override
    public TagDTO getTag(String userId, String tagName) throws MinddyException {
        return TagDTO.fromEntity(
                repo.findById(new TagId(userId, tagName))
                        .orElseThrow(
                                () -> new MinddyException(404, "Tag " + tagName + " does not exist")
                        )
        );
    }

    @Override
    public Tag getTagEntity(String userId, String tagName) throws MinddyException {
        return
                repo.findById(new TagId(userId, tagName))
                        .orElseThrow(
                                () -> new MinddyException(404, "Tag " + tagName + " does not exist")
                        );
    }

    @Override
    public List<TagDTO> getProjectTags(HierarchicalId projectId, boolean visible) {
        return repo.getProjectTags(projectId.getUserId(), projectId.getHolderId(), projectId.getOwnId(), visible).stream().map(TagDTO::fromEntity).toList();
    }

    @Override
    public List<TagDTO> getNoteTags(String userId, UUID noteId, boolean visible) {
        return repo.getNoteTags( userId,noteId, visible).stream().map(TagDTO::fromEntity).toList();
    }

    @Override
    public List<TagDTO> getTaskTags(String userId, UUID taskId, boolean onlyVisible) {
        return repo.getTaskTags(taskId,userId,onlyVisible).stream().map(TagDTO::fromEntity).toList();
    }

    @Override
    public List<Tag> getProjectTagsEntity(HierarchicalId projectId, boolean onlyVisible) {
        return repo.getProjectTags(projectId.getUserId(), projectId.getHolderId(), projectId.getOwnId(), onlyVisible);
    }

    @Override
    public List<Tag> getNoteTagsEntity(String userId, UUID noteId){
        return repo.getNoteTags(userId,noteId,true);
    }

    @Override
    public List<Tag> getTaskTagsEntity(String userId, UUID taskId, boolean onlyVisible) {
        return repo.getTaskTags(taskId,userId,onlyVisible);
    }

    @Override
    public int countTagUses(String userId, String tagName) {
        return repo.countTagUses(userId, tagName);
    }

    @Override
    public TagDTO createTag(String userId, TagDTO dto) throws MinddyException {
        if (repo.existsById(new TagId(userId, dto.getName())))
            throw new MinddyException(400, "Tag " + dto.getName() + " already exists");
        return TagDTO.fromEntity(repo.save(Tag.fromDTO(dto, userId)));
    }
    @Override
    public Tag createTag(String userId, String name) throws MinddyException {
        if (repo.existsById(new TagId(userId, name)))
            throw new MinddyException(400, "Tag " + name + " already exists");
        return repo.save(new Tag().setId(new TagId(userId,name)).setVisible(true).setHeritable(false));    }

    @Override
    public TagDTO updateTag(String userId, String oldName, TagDTO newData) throws MinddyException {
        return TagDTO.fromEntity(repo.save(
                Tag.fromDTO(getTag(userId,oldName)
                        .setName(newData.getName())
                        .setVisible(newData.isVisible())
                        .setHeritable(newData.isHeritable())
                        ,userId)
        ));
    }

    @Override
    public boolean deleteTag(String userId, TagDTO tag) throws MinddyException {
        if(!repo.existsById(new TagId(userId,tag.getName())))throw new MinddyException(404,"Tag "+tag.getName()+" not found");
        repo.delete(Tag.fromDTO(tag,userId));
        return !repo.existsById(new TagId(userId,tag.getName()));
    }

    @Override
    public List<TagDTO> getAllTags(String userId) {
        return repo.getAllUserTags(userId);
    }

    @Override
    public List<String> searchTagsLike(String userId, String nameLike) {
        return repo.getTagsLike(userId, nameLike);
    }
}
//    @Override
//    public List<Tag> getTagsFromList(String userId, String... tagDTOS) throws MinddyException {
//        var arr = new ArrayList<Tag>();
//        for (String name : tagDTOS) {
//            try {
//                arr.add(getTagEntity(userId, name));
//            } catch (MinddyException e) {
//                try {
//                    arr.add(createTag(userId, name));
//                } catch (MinddyException ex) {
//                    throw new MinddyException(418, "Unexpected double error!");
//                }
//            }
//        }
//
//        return arr;
//    }
    //    @Override
//    public List<Tag> getTagsFromList(String userId, TagDTO... tagDTOS) throws MinddyException {
//        var arr = new ArrayList<Tag>();
//        for (TagDTO dto : tagDTOS) {
//            try {
//                arr.add(getTagEntity(userId, dto.getName()));
//            } catch (MinddyException e) {
//                try {
//                    arr.add(createTag(userId, dto));
//                } catch (MinddyException ex) {
//                    throw new MinddyException(418, "Unexpected double error!");
//                }
//            }
//        }
//
//        return arr;
//    }
//
//    @Override
//    @Deprecated
//    public NoteDTO loadTags(String userId, NoteDTO noteDTO) throws MinddyException {
////        return noteDTO.setTags(getTagsFromList(noteDTO.getTags().stream().map(TagDTO::getName).toArray(()->new String()[0])));
//        return null;
//    }
