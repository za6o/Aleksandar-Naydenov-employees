package org.example.employee.project.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;


@Getter
@RequiredArgsConstructor
public class Project{

    private final int employeeID;
    private final int projectID;
    private final Long dateFrom;
    private final Long dateTo;

    @Override
    public String toString() {
        return "Project{" +
                "employeeID=" + employeeID +
                ", projectID=" + projectID +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (employeeID != project.employeeID) return false;
        if (projectID != project.projectID) return false;
        if (!dateFrom.equals(project.dateFrom)) return false;
        return Objects.equals(dateTo, project.dateTo);
    }

    @Override
    public int hashCode() {
        int result = employeeID;
        result = 31 * result + projectID;
        result = 31 * result + dateFrom.hashCode();
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        return result;
    }

}

