package ru.job4j.store;

import ru.job4j.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;

public interface PostStore extends Store<Post> {
    Collection<Post> findAll(LocalDateTime startDate, LocalDateTime endDate);
}
