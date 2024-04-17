package org.example.employee.datagrid.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeePairWorkingTogetherDataGrid {

    private final int employeeID1;
    private final int employeeID2;
    private final int projectID;
    private final long workingTimeInDays;
}
