package ru.job4j.servlet;

import ru.job4j.model.Post;
import ru.job4j.store.Store;
import ru.job4j.store.jdbc.JdbcPostStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class PostServlet extends HttpServlet {
    private final Store<Post> repo = new JdbcPostStore();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("NEW".equals(action)) {
            req.getRequestDispatcher("WEB-INF/jsp/posts/postForm.jsp").forward(req, resp);
        } else if ("EDIT".equals(action)) {
            //TODO move all accesses to the repository from the controller
            Post post = repo.findById(Integer.parseInt(req.getParameter("id")));
            req.setAttribute("post", post);
            req.getRequestDispatcher("WEB-INF/jsp/posts/postForm.jsp").forward(req, resp);
        } else {
            req.setAttribute("posts", repo.findAll());
            req.getRequestDispatcher("WEB-INF/jsp/posts/posts.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        int id = !req.getParameter("id").isEmpty()
                ? Integer.parseInt(req.getParameter("id"))
                : 0;
        repo.save(
                new Post(id,
                        req.getParameter("name"),
                        req.getParameter("description")
                )
        );
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}