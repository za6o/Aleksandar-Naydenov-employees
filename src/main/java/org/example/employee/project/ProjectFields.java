package org.example.employee.project;

import lombok.Getter;

public enum ProjectFields {

    EMPLOYEE_ID(0,"EmployeeID"),
    PROJECT_ID(1,"ProjectID"),
    DATE_FROM(2,"DateFrom"),
    DATE_TO(3,"DateTo");

    @Getter
    private final int position;

    @Getter
    private final String name;


    ProjectFields(int position, String name) {
        this.position = position;
        this.name =name;
    }

}
