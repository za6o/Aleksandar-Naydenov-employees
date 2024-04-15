package org.cirma.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PairWithMaxWorkingHoursPerProject {

    private final long workingTogetherInMs;
    private final long workingTogetherInDays;
    private final int EmployeeID1;
    private final int EmployeeID2;

    @Override
    public String toString() {
        return  EmployeeID1 + ", " + EmployeeID2 + ", " + workingTogetherInDays;
    }
}
