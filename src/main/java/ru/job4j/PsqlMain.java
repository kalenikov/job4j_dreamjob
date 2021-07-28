package ru.job4j;

import ru.job4j.model.Post;
import ru.job4j.store.PsqlStore;
import ru.job4j.store.Store;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "name1", ""));
        store.save(new Post(0, "name2", ""));
        store.save(new Post(0, "name3", ""));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        System.out.println(store.findById(3));
        store.save(new Post(3, "new name", "new desc"));
        System.out.println(store.findById(3));
    }
}
