package ru.job4j.store;

import ru.job4j.model.User;

public interface UserStore extends Store<User> {
    User findByEmail(String email);
}
