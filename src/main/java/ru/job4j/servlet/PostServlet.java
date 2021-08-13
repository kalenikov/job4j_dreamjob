package ru.job4j.servlet;

import ru.job4j.model.Post;
import ru.job4j.store.PostStore;
import ru.job4j.store.Store;
import ru.job4j.store.jdbc.JdbcPostStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

import static ru.job4j.util.DateTimeUtil.*;

@WebServlet
public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostStore repo = JdbcPostStore.getInstance();
        req.setAttribute("user", req.getSession().getAttribute("user"));
        String action = req.getParameter("action");
        if ("NEW".equals(action)) {
            req.getRequestDispatcher("WEB-INF/jsp/posts/postForm.jsp").forward(req, resp);
        } else if ("EDIT".equals(action)) {
            //TODO move all accesses to the repository from the controller
            Post post = repo.findById(Integer.parseInt(req.getParameter("id")));
            req.setAttribute("post", post);
            req.getRequestDispatcher("WEB-INF/jsp/posts/postForm.jsp").forward(req, resp);
        } else {
            LocalDate startDate = strToLocalDate(req.getParameter("startDate"));
            LocalDate endDate = strToLocalDate(req.getParameter("endDate"));
            Collection<Post> post = repo.findAll(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate));
            req.setAttribute("posts", post);
            req.getRequestDispatcher("WEB-INF/jsp/posts/posts.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Store<Post> repo = JdbcPostStore.getInstance();
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