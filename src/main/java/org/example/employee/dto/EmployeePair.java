package org.example.employee.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class EmployeePair {

    private final int employeeID1;
    private final int employeeID2;

    public EmployeePair(int employeeID1, int employeeID2) {
        this.employeeID1 = Math.min(employeeID1,employeeID2);
        this.employeeID2 = Math.max(employeeID1,employeeID2);
    }

}
