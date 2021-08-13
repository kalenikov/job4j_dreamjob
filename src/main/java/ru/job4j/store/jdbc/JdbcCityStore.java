package ru.job4j.store.jdbc;

import lombok.extern.slf4j.Slf4j;
import ru.job4j.model.City;
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
public class JdbcCityStore implements Store<City> {

    private JdbcCityStore() {
    }

    private static class StoreHolder {
        public static final JdbcCityStore HOLDER_INSTANCE = new JdbcCityStore();
    }

    public static JdbcCityStore getInstance() {
        return StoreHolder.HOLDER_INSTANCE;
    }


    @Override
    public Collection<City> findAll() {

        List<City> rsl = new ArrayList<>();
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM city")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    rsl.add(new City(
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
    public void save(City city) {
        if (city.getId() == 0) {
            create(city);
        } else {
            update(city);
        }
    }

    private void update(City city) {
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("update city set name=? where id=?")) {
            ps.setString(1, city.getName());
            ps.setInt(2, city.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public City findById(int id) {
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM city WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new City(
                            it.getInt("id"),
                            it.getString("name"));

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
             PreparedStatement ps = cn.prepareStatement("delete from city where id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    private void create(City city) {
        try (Connection cn = ConnectionPool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO city(name) VALUES (?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, city.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    city.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
