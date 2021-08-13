package ru.job4j.servlet;

import ru.job4j.model.Candidate;
import ru.job4j.model.Post;
import ru.job4j.store.PostStore;
import ru.job4j.store.Store;
import ru.job4j.store.jdbc.JdbcCandidateStore;
import ru.job4j.store.jdbc.JdbcPostStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

import static ru.job4j.util.DateTimeUtil.*;

public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", req.getSession().getAttribute("user"));

        PostStore postStore = JdbcPostStore.getInstance();
        LocalDate startDate = strToLocalDate(req.getParameter("startDate"));
        LocalDate endDate = strToLocalDate(req.getParameter("endDate"));
        Collection<Post> posts = postStore.findAll(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate));
        req.setAttribute("posts", posts);

        Store<Candidate> candidateStore = JdbcCandidateStore.getInstance();
        Collection<Candidate> candidates = candidateStore.findAll();
        req.setAttribute("candidates", candidates);

        req.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(req, resp);
    }
}