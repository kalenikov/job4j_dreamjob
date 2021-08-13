package ru.job4j.store.jdbc;

import lombok.extern.slf4j.Slf4j;
import ru.job4j.model.Post;
import ru.job4j.store.ConnectionPool;
import ru.job4j.store.PostStore;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static ru.job4j.util.DateTimeUtil.localDateTimeToTimestamp;

@Slf4j
public class JdbcPostStore implements PostStore {

    private JdbcPostStore() {
    }

    private static class StoreHolder {
        public static final JdbcPostStore HOLDER_INSTANCE = new JdbcPostStore();
    }

    public static PostStore getInstance() {
        return StoreHolder.HOLDER_INSTANCE;
    }


    @Override
    public Collection<Post> findAll() {
        return findAll(null, null);
    }

    @Override
    public Collection<Post> findAll(LocalDateTime startDate, LocalDateTime endDate) {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM post where created BETWEEN ? AND ? order by created")) {
            ps.setTimestamp(1, localDateTimeToTimestamp(startDate));
            ps.setTimestamp(2, localDateTimeToTimestamp(endDate));
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getTimestamp("created")));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return posts;
    }

    private void create(Post post) {
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name, description, created) VALUES (?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, post.getCreated());
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
                            it.getString("description"),
                            it.getTimestamp("created")
                    );
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void removeById(int id) {
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("delete from post where id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
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
        PostStore store = new JdbcPostStore();
        store.save(new Post(0, "name1", "", Timestamp.valueOf(LocalDateTime.now().minusDays(10))));
        store.save(new Post(0, "name2", "", Timestamp.valueOf(LocalDateTime.now().minusHours(30))));
        store.save(new Post(0, "name3", "", Timestamp.valueOf(LocalDateTime.now())));
//        for (Post post : store.findAll()) {
//            System.out.println(post.getId() + " " + post.getName());
//        }
//        System.out.println(store.findById(3));
//        store.save(new Post(3, "new name", "new desc"));
//        System.out.println(store.findAllBetweenDate(LocalDateTime.now().minusDays(10), LocalDateTime.now()));
        for (Post post : store.findAll(LocalDateTime.now().minusDays(10), LocalDateTime.now())) {
            System.out.println(post);
        }

        for (Post post : store.findAll()) {
            System.out.println(post);
        }

    }
}
