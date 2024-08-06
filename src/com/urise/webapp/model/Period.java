package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {

    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final String position;
    private final String description;

    public Period(LocalDate dateFrom, LocalDate dateTo, String position, String description) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.position = position;
        this.description = description;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!Objects.equals(dateFrom, period.dateFrom)) return false;
        if (!Objects.equals(dateTo, period.dateTo)) return false;
        if (!Objects.equals(position, period.position)) return false;
        return Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        int result = dateFrom != null ? dateFrom.hashCode() : 0;
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Period{" +
                "dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", position='" + position + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
