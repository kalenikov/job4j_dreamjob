package ru.job4j.servlet;

import ru.job4j.PropertySource;
import ru.job4j.model.Candidate;
import ru.job4j.store.Store;
import ru.job4j.store.jdbc.JdbcCandidateStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CandidateServlet extends HttpServlet {
    private final Store<Candidate> repo = new JdbcCandidateStore();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("DELETE".equals(action)) {
            doDelete(req, resp);
        } else if ("NEW".equals(action)) {
            req.getRequestDispatcher("WEB-INF/jsp/candidates/candidateForm.jsp").forward(req, resp);
        } else if ("EDIT".equals(action)) {
            //TODO move all accesses to repository from controller
            Candidate candidate = repo.findById(Integer.parseInt(req.getParameter("id")));
            req.setAttribute("candidate", candidate);
            req.getRequestDispatcher("WEB-INF/jsp/candidates/candidateForm.jsp").forward(req, resp);
        } else {
            req.setAttribute("candidates", repo.findAll());
            req.getRequestDispatcher("WEB-INF/jsp/candidates/candidates.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        int id = !req.getParameter("id").isEmpty()
                ? Integer.parseInt(req.getParameter("id"))
                : 0;
        repo.save(new Candidate(id, req.getParameter("name")));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        repo.removeById(Integer.parseInt(id));
        Files.deleteIfExists(Paths.get(PropertySource.get("IMAGE_DIR"), id));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
