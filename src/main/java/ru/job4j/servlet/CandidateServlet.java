package ru.job4j.servlet;

import ru.job4j.model.Candidate;
import ru.job4j.store.MemStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ru.job4j.Config.IMAGE_DIR;

public class CandidateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("DELETE".equals(req.getParameter("action"))) {
            doDelete(req, resp);
            return;
        }
        req.setAttribute("candidates", MemStore.getInstance().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        MemStore.getInstance().save(
                new Candidate(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name")
                )
        );
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        MemStore.getInstance().removeById(Integer.parseInt(id));
        Files.deleteIfExists(Paths.get(IMAGE_DIR, id));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
