package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    private Link homePage;
    private List<Position> positions;

    public Company() {
    }

    public Company(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public Company(Link homePage, List<Position> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!homePage.equals(company.homePage)) return false;
        return positions.equals(company.positions);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Company{" +
                "homePage=" + homePage +
                ", positions=" + positions +
                '}';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {

        private static final long serialVersionUID = 1L;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateFrom;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateTo;
        private String title;
        private String description;

        public Position() {
        }

        public Position(int startYear, Month startMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.NOW, title, description);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), title, description);
        }

        public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.dateFrom = startDate;
            this.dateTo = endDate;
            this.title = title;
            this.description = description;
        }

        public LocalDate getDateFrom() {
            return dateFrom;
        }

        public LocalDate getDateTo() {
            return dateTo;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position position = (Position) o;

            if (!dateFrom.equals(position.dateFrom)) return false;
            if (!dateTo.equals(position.dateTo)) return false;
            if (!title.equals(position.title)) return false;
            return Objects.equals(description, position.description);
        }

        @Override
        public int hashCode() {
            int result = dateFrom.hashCode();
            result = 31 * result + dateTo.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Period{" +
                    "dateFrom=" + dateFrom +
                    ", dateTo=" + dateTo +
                    ", position='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
