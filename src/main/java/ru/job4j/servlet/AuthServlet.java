package ru.job4j.servlet;

import ru.job4j.model.User;
import ru.job4j.store.UserStore;
import ru.job4j.store.jdbc.JdbcUserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        UserStore repo = JdbcUserStore.getInstance();
        Optional<User> user = Optional.ofNullable(repo.findByEmail(email));
        if (user.isPresent()) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user.get());
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        } else {
            req.setAttribute("error", "Incorrect email or password");
            req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
        }
    }
}
