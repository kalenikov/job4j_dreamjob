package ru.job4j.store;

import ru.job4j.model.Candidate;
import ru.job4j.model.Post;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Store {
    private static final Store INST = new Store();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private static AtomicInteger POST_ID = new AtomicInteger(10);

    private Store() {
        posts.put(1, new Post(1, "name1", "desc1", Timestamp.valueOf(LocalDateTime.now())));
        posts.put(2, new Post(2, "name2", "desc2", Timestamp.valueOf(LocalDateTime.now())));
        posts.put(3, new Post(3, "name3", "desc3", Timestamp.valueOf(LocalDateTime.now())));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public static Store getInstance() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    public void save(Post post){
        post.setId(POST_ID.incrementAndGet());
        posts.put(post.getId(), post);
    }
}