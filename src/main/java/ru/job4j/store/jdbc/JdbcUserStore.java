package ru.job4j.store.jdbc;

import lombok.extern.slf4j.Slf4j;
import ru.job4j.model.User;
import ru.job4j.store.ConnectionPool;
import ru.job4j.store.UserStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class JdbcUserStore implements UserStore {

    private JdbcUserStore() {
    }

    private static class StoreHolder {
        public static final JdbcUserStore HOLDER_INSTANCE = new JdbcUserStore();
    }

    public static JdbcUserStore getInstance() {
        return StoreHolder.HOLDER_INSTANCE;
    }

    @Override
    public Collection<User> findAll() {
        List<User> rsl = new ArrayList<>();
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    rsl.add(new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getString("password")));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return rsl;
    }

    private void create(User user) {
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO users(name, email, password) VALUES (?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            create(user);
        } else {
            update(user);
        }
    }

    @Override
    public User findById(int id) {
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getString("password")
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
             PreparedStatement ps = cn.prepareStatement("delete from users where id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    private void update(User user) {
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("update users set name=?, email=?, password=? where id=?")) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE email=?")) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password")
                    );
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
