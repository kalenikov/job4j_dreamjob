package ru.job4j.servlet;

import ru.job4j.model.User;
import ru.job4j.store.UserStore;
import ru.job4j.store.jdbc.JdbcUserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        UserStore repo = JdbcUserStore.getInstance();
        Optional<User> user = Optional.ofNullable(repo.findByEmail(email));
        if (user.isPresent()) {
            req.setAttribute("errorMessage", "this email is already registered");
            req.getRequestDispatcher("WEB-INF/jsp/reg.jsp").forward(req, resp);
            return;
        } else {
            repo.save(
                    new User(
                            req.getParameter("name"),
                            email,
                            req.getParameter("password")
                    )
            );
        }
        req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
    }
}
