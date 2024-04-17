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
}

