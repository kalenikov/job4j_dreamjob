package ru.job4j.store.jdbc;

import lombok.extern.slf4j.Slf4j;
import ru.job4j.model.Candidate;
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
public class JdbcCandidateStore implements Store<Candidate> {
    private JdbcCandidateStore() {
    }

    private static class StoreHolder {
        public static final JdbcCandidateStore HOLDER_INSTANCE = new JdbcCandidateStore();
    }

    public static Store<Candidate> getInstance() {
        return StoreHolder.HOLDER_INSTANCE;
    }

    @Override
    public Collection<Candidate> findAll() {
        List<Candidate> rsl = new ArrayList<>();
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidates")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    rsl.add(new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getInt("city_id")
                    ));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return rsl;
    }


    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private void update(Candidate candidate) {
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "update candidates set name=?, city_id=? where id=?")) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getId());
            ps.setInt(3, candidate.getCityId());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    private void create(Candidate candidate) {
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO candidates(name, city_id) VALUES (?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    @Override
    public Candidate findById(int id) {
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM candidates WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getInt("city_id")
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
             PreparedStatement ps = cn.prepareStatement(
                     "delete from candidates where id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Store<Candidate> store = new JdbcCandidateStore();
        store.save(new Candidate(0, "name1", 1));
        store.save(new Candidate(0, "name2", 2));
        store.save(new Candidate(0, "name3", 3));
        for (Candidate candidate : store.findAll()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
        System.out.println(store.findById(3));
        store.save(new Candidate(3, "new name"));
        System.out.println(store.findById(3));
    }
}
