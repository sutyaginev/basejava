package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.ResumeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                resume = storage.get(uuid);

                for (SectionType type : SectionType.values()) {
                    Section section = resume.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = new TextSection("");
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = new ListSection("");
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            CompanySection companySection = (CompanySection) section;
                            List<Company> companies = new ArrayList<>();

                            if (companySection != null) {
                                for (Company company : companySection.getCompanies()) {
                                    List<Company.Position> positions = new ArrayList<>();
                                    positions.add(new Company.Position());
                                    positions.addAll(company.getPositions());
                                    companies.add(new Company(company.getHomePage(), positions));
                                }
                            }

                            companies.add(new Company("", "", new Company.Position()));
                            section = new CompanySection(companies);
                            break;
                    }
                    resume.addSection(type, section);
                }
                break;
            case "add":
                resume = new Resume();
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
        Resume resume;

        boolean isCreate = ResumeUtil.isEmpty(uuid);

        if (isCreate) {
            resume = new Resume(fullName);
            storage.save(resume);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());

            if (ResumeUtil.isEmpty(value)) {
                resume.getContacts().remove(type);

            } else {
                resume.addContact(type, value);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());

            if (ResumeUtil.isEmpty(value)) {
                resume.getSections().remove(type);
                continue;
            }

            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    resume.getSections().put(type, new TextSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    resume.getSections().put(type, new ListSection(value.split("\n")));
                    break;
                case EDUCATION:
                case EXPERIENCE:
//                    if (values.length < 2) {
//                        resume.getSections().remove(type);
//                        continue;
//                    }

                    List<Company> companies = new ArrayList<>();
                    String[] urls = request.getParameterValues(type.name() + "url");

                    for (int i = 0; i < values.length; i++) {
                        String companyName = values[i];

                        if (ResumeUtil.isEmpty(companyName)) {
                            continue;
                        }

                        List<Company.Position> positions = new ArrayList<>();
                        String parameter = type.name() + i;
                        String[] datesFrom = request.getParameterValues(parameter + "dateFrom");
                        String[] datesTo = request.getParameterValues(parameter + "dateTo");
                        String[] titles = request.getParameterValues(parameter + "title");
                        String[] descriptions = request.getParameterValues(parameter + "description");

                        for (int j = 0; j < titles.length; j++) {
                            if (ResumeUtil.isEmpty(titles[j])) {
                                continue;
                            }

                            positions.add(new Company.Position(
                                    DateUtil.parseDate(datesFrom[j]),
                                    DateUtil.parseDate(datesTo[j]),
                                    titles[j],
                                    ResumeUtil.isEmpty(descriptions[j]) ? " " : descriptions[j]));
                        }

                        companies.add(new Company(new Link(companyName, ResumeUtil.isEmpty(urls[i]) ? " " : urls[i]), positions));
                    }

                    resume.getSections().put(type, new CompanySection(companies));
                    break;
            }
        }

        if (isCreate) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }

        response.sendRedirect("resume");
    }
}
