package ru.job4j.servlet;

import ru.job4j.model.User;
import ru.job4j.store.Store;
import ru.job4j.store.jdbc.JdbcUserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store<User> repo = JdbcUserStore.getInstance();
        req.setCharacterEncoding("UTF-8");
        repo.save(
                new User(
                        req.getParameter("name"),
                        req.getParameter("email"),
                        req.getParameter("password")
                )
        );
        req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
    }
}
