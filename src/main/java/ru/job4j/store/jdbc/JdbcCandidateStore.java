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
    private final Connection cn = ConnectionPool.getConnection();

    @Override
    public Collection<Candidate> findAll() {
        List<Candidate> rsl = new ArrayList<>();
        try (PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidates")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    rsl.add(new Candidate(
                            it.getInt("id"),
                            it.getString("name")));
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
        try (PreparedStatement ps = cn.prepareStatement("update candidates set name=? where id=?")) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    private void create(Candidate candidate) {
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO candidates(name) VALUES (?)",
                PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
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
        try (PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidates WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(
                            it.getInt("id"),
                            it.getString("name")
                    );
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        Store<Candidate> store = new JdbcCandidateStore();
        store.save(new Candidate(0, "name1"));
        store.save(new Candidate(0, "name2"));
        store.save(new Candidate(0, "name3"));
        for (Candidate candidate : store.findAll()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
        System.out.println(store.findById(3));
        store.save(new Candidate(3, "new name"));
        System.out.println(store.findById(3));
    }
}
