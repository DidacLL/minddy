package cat.itacademy.minddy.data.config;

import cat.itacademy.minddy.data.dto.TagDTO;
import lombok.Getter;

@Getter
public enum DefaultTags {
    DELAYED(false,false,"_DELAYED_"),
    ROOT(false,false,"_ROOT_"),
    FAVOURITE(false,false,"_FAV_"),
    PINNED(false,false,"_PIN_"),
    TASK_NOTE(false,false,"_TASK_");
    //TODO Default tags config

    private final String tagName;
    private boolean isVisible,isHeritable;
    DefaultTags(boolean isVisible, boolean isHeritable, String tagName) {
        this.tagName = tagName;
        this.isHeritable=isHeritable;
        this.isVisible=isVisible;
    }

    @Override
    public String toString() {
        return this.tagName;
    }
    public TagDTO toDTO(){
        return new TagDTO(tagName,isVisible,isHeritable);
    }
}
