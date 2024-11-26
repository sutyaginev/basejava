package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.ResumeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

        Resume resume;

        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                if (uuid != null && !uuid.isEmpty()) {
                    resume = storage.get(uuid);
                } else {
                    resume = new Resume(UUID.randomUUID().toString(), "RandomName");
                    storage.save(resume);
                    storage.get(resume.getUuid());
                }

                break;
            default:
                throw new IllegalArgumentException("Action" + action + " is illegal");
        }

        request.setAttribute("resume", resume);
        request.getRequestDispatcher("view".equals(action) ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume = storage.get(uuid);
        if (fullName != null && !fullName.trim().isEmpty()) {
            resume.setFullName(fullName);
        }


        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());

            if (value != null && !value.trim().isEmpty()) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());

            if (value != null && !value.trim().isEmpty()) {
                ResumeUtil.addSection(resume, type, value);
            } else {
                resume.getSections().remove(type);
            }
        }

        storage.update(resume);
        response.sendRedirect("resume");
    }
}
