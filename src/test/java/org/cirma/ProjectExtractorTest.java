package org.cirma;


import org.cirma.project.dto.Project;
import org.cirma.result.PairWithMaxWorkingHoursPerProject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;



class ProjectExtractorTest {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    ProjectExtractor projectExtractor = new ProjectExtractor();

    @Test
    void getResult() throws ParseException {
        Set<Project> projects = new HashSet<>();
        projects.add(new Project(1,12,sdf.parse("2013-11-01").getTime(),sdf.parse("2013-11-01").getTime()));
        projects.add(new Project(2,10,sdf.parse("2024-04-11").getTime(),sdf.parse("2024-04-16").getTime()));
        projects.add(new Project(1,10,sdf.parse("2011-01-01").getTime(),sdf.parse("2024-04-13").getTime()));
        projects.add(new Project(2,12,sdf.parse("2014-01-01").getTime(),sdf.parse("2015-01-01").getTime()));
        projects.add(new Project(3,13,sdf.parse("2012-01-01").getTime(),sdf.parse("2012-02-01").getTime()));
        projects.add(new Project(4,13,sdf.parse("2011-01-01").getTime(),sdf.parse("2014-02-01").getTime()));

        PairWithMaxWorkingHoursPerProject pairWithMaxWorkingHoursPerProject = projectExtractor.getTheProjectWithMaxWorkingHourForAPair(projects);

        Assertions.assertEquals(4, pairWithMaxWorkingHoursPerProject.getEmployeeID1());
        Assertions.assertEquals(3, pairWithMaxWorkingHoursPerProject.getEmployeeID2());
        Assertions.assertEquals(31, pairWithMaxWorkingHoursPerProject.getWorkingTogetherInDays());
    }
}