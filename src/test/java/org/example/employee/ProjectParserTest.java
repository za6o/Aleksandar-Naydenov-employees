package org.example.employee;


import org.example.employee.dto.EmployeePairWorkingTogether;
import org.example.employee.exceptions.DateFormatParserException;
import org.example.employee.exceptions.PairNotFound;
import org.example.employee.exceptions.ProjectsNotFound;
import org.example.employee.parser.DateParser;
import org.example.employee.project.ProjectParser;
import org.example.employee.project.dto.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class ProjectParserTest {

    private final DateParser dateParser = new DateParser();
    private final ProjectParser projectParser = new ProjectParser();

    @Test
    void getResult() throws  DateFormatParserException {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1,12,dateParser.parseDateFromString("2013-11-01").toEpochDay(),
                dateParser.parseDateFromString("2013-11-01").toEpochDay()));
        projects.add(new Project(2,10,dateParser.parseDateFromString("2024-04-11").toEpochDay(),
                dateParser.parseDateFromString("2024-04-16").toEpochDay()));
        projects.add(new Project(1,10,dateParser.parseDateFromString("2011-01-01").toEpochDay()
                ,dateParser.parseDateFromString("2024-04-13").toEpochDay()));
        projects.add(new Project(2,12,dateParser.parseDateFromString("2014-01-01").toEpochDay(),
                dateParser.parseDateFromString("2015-01-01").toEpochDay()));
        projects.add(new Project(3,13,dateParser.parseDateFromString("2012-01-01").toEpochDay(),
                dateParser.parseDateFromString("2012-02-01").toEpochDay()));
        projects.add(new Project(4,13,dateParser.parseDateFromString("2011-01-01").toEpochDay(),
                dateParser.parseDateFromString("2014-02-01").toEpochDay()));

        EmployeePairWorkingTogether timeTogetherForAnEmployeePair = projectParser.getThePairWithMaxWorkingHourForAllProjects(projects);

        Assertions.assertEquals(3, timeTogetherForAnEmployeePair.getEmployeePair().getEmployeeID1());
        Assertions.assertEquals(4, timeTogetherForAnEmployeePair.getEmployeePair().getEmployeeID2());
        Assertions.assertEquals(31, timeTogetherForAnEmployeePair.getTotalTimeWorkingTogetherInDays());
    }

    @Test
    void getResult_employee_2_working_twice_on_the_same_project() throws DateFormatParserException {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(2,10,dateParser.parseDateFromString("2010-01-01").toEpochDay(),
                dateParser.parseDateFromString("2011-01-30").toEpochDay()));
        projects.add(new Project(1,10,dateParser.parseDateFromString("2011-01-01").toEpochDay(),
                dateParser.parseDateFromString("2024-01-11").toEpochDay()));
        projects.add(new Project(2,10,dateParser.parseDateFromString("2024-01-01").toEpochDay(),
                dateParser.parseDateFromString("2024-04-01").toEpochDay()));
        projects.add(new Project(3,13,dateParser.parseDateFromString("2012-01-01").toEpochDay(),
                dateParser.parseDateFromString("2012-02-01").toEpochDay()));
        projects.add(new Project(4,13,dateParser.parseDateFromString("2011-01-01").toEpochDay(),
                dateParser.parseDateFromString("2014-02-01").toEpochDay()));

        EmployeePairWorkingTogether timeTogetherForAnEmployeePair = projectParser.getThePairWithMaxWorkingHourForAllProjects(projects);

        Assertions.assertEquals(1, timeTogetherForAnEmployeePair.getEmployeePair().getEmployeeID1());
        Assertions.assertEquals(2, timeTogetherForAnEmployeePair.getEmployeePair().getEmployeeID2());
        Assertions.assertEquals(39, timeTogetherForAnEmployeePair.getTotalTimeWorkingTogetherInDays());
    }

    @Test
    void getResult_pair_working_on_2_projects_together() throws  DateFormatParserException {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(2,10,dateParser.parseDateFromString("2010-01-01").toEpochDay(),
                dateParser.parseDateFromString("2011-01-30").toEpochDay()));
        projects.add(new Project(1,10,dateParser.parseDateFromString("2011-01-01").toEpochDay(),
                dateParser.parseDateFromString("2024-01-11").toEpochDay()));
        projects.add(new Project(2,10,dateParser.parseDateFromString("2024-01-01").toEpochDay(),
                dateParser.parseDateFromString("2024-04-01").toEpochDay()));
        projects.add(new Project(3,15,dateParser.parseDateFromString("2012-01-01").toEpochDay(),
                dateParser.parseDateFromString("2012-02-01").toEpochDay()));
        projects.add(new Project(4,13,dateParser.parseDateFromString("2011-01-01").toEpochDay(),
                dateParser.parseDateFromString("2014-02-01").toEpochDay()));
        projects.add(new Project(3,13,dateParser.parseDateFromString("2012-01-01").toEpochDay(),
                dateParser.parseDateFromString("2012-02-01").toEpochDay()));
        projects.add(new Project(4,15,dateParser.parseDateFromString("2011-01-01").toEpochDay(),
                dateParser.parseDateFromString("2014-02-01").toEpochDay()));

        EmployeePairWorkingTogether timeTogetherForAnEmployeePair = projectParser.getThePairWithMaxWorkingHourForAllProjects(projects);

        Assertions.assertEquals(3, timeTogetherForAnEmployeePair.getEmployeePair().getEmployeeID1());
        Assertions.assertEquals(4, timeTogetherForAnEmployeePair.getEmployeePair().getEmployeeID2());
        Assertions.assertEquals(62, timeTogetherForAnEmployeePair.getTotalTimeWorkingTogetherInDays());
    }

    @Test
    void getResult_no_overlapping_of_the_projects() throws DateFormatParserException {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(2,10,dateParser.parseDateFromString("2010-01-01").toEpochDay(),
                dateParser.parseDateFromString("2011-01-30").toEpochDay()));
        projects.add(new Project(1,10,dateParser.parseDateFromString("2011-05-01").toEpochDay(),
                dateParser.parseDateFromString("2024-01-11").toEpochDay()));

        Exception expectedException = null;

        try {
            projectParser.getThePairWithMaxWorkingHourForAllProjects(projects);
        } catch (PairNotFound ex){
            expectedException = ex;
        }

        Assertions.assertNotNull(expectedException);

    }

    @Test
    void getResult_no_projects()  {

        Exception expectedException = null;
        try {
            projectParser.getThePairWithMaxWorkingHourForAllProjects(Collections.emptyList());
        } catch (ProjectsNotFound ex){
            expectedException = ex;
        }

        Assertions.assertNotNull(expectedException);

    }
}