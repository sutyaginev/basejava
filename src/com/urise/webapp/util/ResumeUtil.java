package com.urise.webapp.util;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeUtil {

    public static void addSection(Resume resume, SectionType type, String value) {
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                resume.addSection(type, new TextSection(value));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                resume.addSection(type, new ListSection(new ArrayList<>(Arrays.asList(value.split("\n")))));
                break;
            case EXPERIENCE:
            case EDUCATION:
                String[] companies = value.split("\n");
                CompanySection companySection = new CompanySection(new ArrayList<>());

                for (String company : companies) {
                    String[] companyItems = company.split("\t");
                    Link homePage = new Link(companyItems[0], (companyItems[1] == null || companyItems[1].trim().isEmpty()) ? null : companyItems[1]);

                    List<Company.Position> positions = new ArrayList<>();

                    for (int i = 2; i < companyItems.length; i += 4) {
                        LocalDate dateFrom = companyItems[i].isEmpty() ? null : LocalDate.parse(companyItems[i], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        LocalDate dateTo = companyItems[i + 1].isEmpty() ? null : LocalDate.parse(companyItems[i + 1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        String title = companyItems[i + 2];
                        String description = (companyItems[i + 3] == null || companyItems[i + 3].trim().isEmpty()) ? null : companyItems[i + 3];
                        positions.add(new Company.Position(dateFrom, dateTo, title, description));
                    }

                    companySection.getCompanies().add(new Company(homePage, positions));
                }

                resume.addSection(type, companySection);
        }
    }

    public static List<String> formatCompaniesToString(List<Company> companies) {
        List<String> companiesList = new ArrayList<>();

        for (Company company : companies) {
            StringBuilder str = new StringBuilder(company.getHomePage().getName() + "\t");
            str.append(company.getHomePage().getUrl() == null ? " \t" : company.getHomePage().getUrl() + "\t");

            for (Company.Position position : company.getPositions()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                str.append(position.getDateFrom().format(formatter)).append("\t");
                str.append(position.getDateTo().format(formatter)).append("\t");
                str.append(position.getTitle()).append("\t");
                str.append(position.getDescription() == null ? " \t" : position.getDescription() + "\t");
            }

            companiesList.add(str.toString());
        }

        return companiesList;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
