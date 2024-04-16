package org.example.employee.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class EmployeePairWorkingTogether {

    private final EmployeePair employeePair;
    private final Map<Integer,Long> timeWorkingTogetherByProject;
    private final long totalTimeWorkingTogetherInDays;

    @Override
    public String toString() {
        return  employeePair.getEmployeeID1() + ", " + employeePair.getEmployeeID2() + ", " + totalTimeWorkingTogetherInDays;
    }
}
