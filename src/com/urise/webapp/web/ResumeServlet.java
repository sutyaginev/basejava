package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("resumes", storage.getAllSorted());
        request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
    }

    private void printAllResumes(PrintWriter writer) {
        for (Resume resume : storage.getAllSorted()) {
            writer.write(String.format("<tr>\n<td>%s</td>\n<td>%s</td>\n</tr>\n", resume.getUuid(), resume.getFullName()));
        }
    }

    private void printResume(PrintWriter writer, String uuid) {
        Resume resume = storage.get(uuid);
        writer.write(String.format("<tr>\n<td>%s</td>\n<td>%s</td>\n</tr>\n", resume.getUuid(), resume.getFullName()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
