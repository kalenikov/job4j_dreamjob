package ru.job4j.store.memory;

import ru.job4j.model.Post;
import ru.job4j.store.Store;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemPostStore implements Store<Post> {
    private static final MemPostStore INST = new MemPostStore();
    private final Map<Integer, Post> store = new ConcurrentHashMap<>();
    private static final AtomicInteger ID = new AtomicInteger(10);

    private MemPostStore() {
        store.put(1, new Post(1, "name1", "desc1", Timestamp.valueOf(LocalDateTime.now())));
        store.put(2, new Post(2, "name2", "desc2", Timestamp.valueOf(LocalDateTime.now())));
        store.put(3, new Post(3, "name3", "desc3", Timestamp.valueOf(LocalDateTime.now())));
    }

    public static MemPostStore getInstance() {
        return INST;
    }

    public Collection<Post> findAll() {
        return store.values();
    }

    public void save(Post item) {
        if (item.getId() == 0) {
            item.setId(ID.incrementAndGet());
        }
        store.put(item.getId(), item);
    }

    public Post findById(int id) {
        return store.get(id);
    }

    public void removeById(int id) {
        store.remove(id);
    }
}