package com.urise.webapp.model;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {

    private static final long serialVersionUID = 1L;

    private List<Company> companies;

    public CompanySection() {
    }

    public CompanySection(Company... companies) {
        this(Arrays.asList(companies));
    }

    public CompanySection(List<Company> companies) {
        Objects.requireNonNull(companies, "companies must not be null");
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return companies.equals(that.companies);
    }

    @Override
    public int hashCode() {
        return companies.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.yyyy");
        for (Company company : companies) {
            sb.append("\n- ")
                    .append(company.getHomePage().getName())
                    .append(" (").append(company.getHomePage().getUrl()).append(")\n  ");
            for (Company.Position position : company.getPositions()) {
                sb.append(position.getDateFrom().format(formatter))
                        .append("-").append(position.getDateTo().format(formatter))
                        .append(": ").append(position.getTitle()).append("\n  ")
                        .append(position.getDescription());
            }
        }
        return sb.toString();
    }
}
