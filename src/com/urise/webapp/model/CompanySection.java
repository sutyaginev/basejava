package com.urise.webapp.model;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {

    private final List<Company> companies;

    public CompanySection(List<Company> companies) {
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

        return Objects.equals(companies, that.companies);
    }

    @Override
    public int hashCode() {
        return companies != null ? companies.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.yyyy");
        for (Company company : companies) {
            sb.append("\n- ").append(company.getName()).append(" (").append(company.getWebsite()).append(")\n  ");
            for (Period period : company.getPeriods()) {
                sb.append(period.getDateFrom().format(formatter)).append("-").append(period.getDateTo().format(formatter)).
                        append(" - ").append(period.getPosition()).append("\n  ").append(period.getDescription());
            }
        }
        return sb.toString();
    }
}
