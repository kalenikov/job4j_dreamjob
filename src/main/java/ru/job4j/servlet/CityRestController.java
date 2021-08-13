package ru.job4j.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.model.City;
import ru.job4j.store.Store;
import ru.job4j.store.jdbc.JdbcCityStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class CityRestController extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json; charset=utf-8");
        
        Store<City> repo = JdbcCityStore.getInstance();
        Collection<City> cities = repo.findAll();
        OutputStream output = resp.getOutputStream();
        output.write(GSON.toJson(cities).getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}
