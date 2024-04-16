package org.example.employee.dto;

import lombok.Getter;

@Getter
public class EmployeePair {

    private final int employeeID1;
    private final int employeeID2;

    public EmployeePair(int employeeID1, int employeeID2) {
        this.employeeID1 = Math.min(employeeID1,employeeID2);
        this.employeeID2 = Math.max(employeeID1,employeeID2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeePair that = (EmployeePair) o;

        if (employeeID1 != that.employeeID1) return false;
        return employeeID2 == that.employeeID2;
    }

    @Override
    public int hashCode() {
        int result = employeeID1;
        result = 31 * result + employeeID2;
        return result;
    }
}
