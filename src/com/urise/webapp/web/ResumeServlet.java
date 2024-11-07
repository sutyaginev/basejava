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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Content-type", "text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + "!");

        String uuid = request.getParameter("uuid");
        PrintWriter writer = response.getWriter();

        writer.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<style>\n" +
                "table, th, td {\n" +
                "  border:1px solid black;\n" +
                "}\n" +
                "</style>\n" +
                "<body>\n" +
                "\n" +
                "<h2>Resume</h2>\n" +
                "\n" +
                "<table>\n" +
                "  <tr>\n" +
                "    <th>Uuid</th>\n" +
                "    <th>Full name</th>\n" +
                "  </tr>");

        if (uuid == null) {
            printAllResumes(writer);
        } else {
            printResume(writer, uuid);
        }


        writer.write("</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n");
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
