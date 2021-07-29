package ru.job4j.store.jdbc;

import lombok.extern.slf4j.Slf4j;
import ru.job4j.model.Post;
import ru.job4j.store.ConnectionPool;
import ru.job4j.store.Store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class JdbcPostStore implements Store<Post> {
    private final Connection cn = ConnectionPool.getConnection();

    @Override
    public Collection<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description")));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return posts;
    }

    private void create(Post post) {
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    @Override
    public Post findById(int id) {
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description")
                    );
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private void update(Post post) {
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("update post set name=?, description=? where id=?")) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setInt(3, post.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Store<Post> store = new JdbcPostStore();
        store.save(new Post(0, "name1", ""));
        store.save(new Post(0, "name2", ""));
        store.save(new Post(0, "name3", ""));
        for (Post post : store.findAll()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        System.out.println(store.findById(3));
        store.save(new Post(3, "new name", "new desc"));
        System.out.println(store.findById(3));
    }
}
