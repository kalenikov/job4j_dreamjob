package ru.job4j.servlet;

import ru.job4j.model.Post;
import ru.job4j.store.Store;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Store.getInstance().save(
                new Post(Integer.valueOf(req.getParameter("id")),
                        req.getParameter("name"),
                        req.getParameter("description")
                )
        );
        resp.sendRedirect(req.getContextPath() + "/posts.jsp");
    }
}