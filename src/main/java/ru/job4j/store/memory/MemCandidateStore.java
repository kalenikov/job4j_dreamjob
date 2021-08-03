package ru.job4j.store.memory;

import ru.job4j.model.Candidate;
import ru.job4j.store.Store;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemCandidateStore implements Store<Candidate> {
    private static final MemCandidateStore INST = new MemCandidateStore();
    private final Map<Integer, Candidate> store = new ConcurrentHashMap<>();
    private static final AtomicInteger ID = new AtomicInteger(10);

    private MemCandidateStore() {
        store.put(1, new Candidate(1, "Junior Java"));
        store.put(2, new Candidate(2, "Middle Java"));
        store.put(3, new Candidate(3, "Senior Java"));
    }

    public static MemCandidateStore getInstance() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return store.values();
    }

    public void save(Candidate item) {
        if (item.getId() == 0) {
            item.setId(ID.incrementAndGet());
        }
        store.put(item.getId(), item);
    }

    public Candidate findById(int id) {
        return store.get(id);
    }

    public void removeById(int id) {
        store.remove(id);
    }
}