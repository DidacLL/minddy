package cat.itacademy.minddy.data.interfaces;

import cat.itacademy.minddy.data.dao.Tag;

public interface Taggable<T> {

    T addTag(Tag ...tag);
    T quitTag(Tag... tag);
}
