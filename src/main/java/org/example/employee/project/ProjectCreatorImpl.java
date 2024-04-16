package org.example.employee.project;

import lombok.AllArgsConstructor;
import org.example.employee.exceptions.DateFormatParserException;
import org.example.employee.parser.DateParser;
import org.example.employee.project.dto.Project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class ProjectCreatorImpl implements ProjectCreator {

    private final DateParser dateParser;

    @Override
    public List<Project> createProjectObject(List<List<String>> projectFields) throws DateFormatParserException {
        List<Project> projects = new ArrayList<>();

        for (List<String> columns : projectFields) {
            int employeeId = Integer.parseInt(columns.get(ProjectFields.EMPLOYEE_ID.getPosition()));
            int projectId = Integer.parseInt(columns.get(ProjectFields.PROJECT_ID.getPosition()));
            Long startDate = dateParser.parseDateFromString(columns.get(ProjectFields.DATE_FROM.getPosition())).toEpochDay();
            Long endDate = columns.get(ProjectFields.DATE_TO.getPosition()).equalsIgnoreCase("null") ?
                    new Date().getTime() :
                    dateParser.parseDateFromString(columns.get(ProjectFields.DATE_TO.getPosition())).toEpochDay();

            projects.add(new Project(employeeId, projectId, startDate, endDate));
        }
        return projects;
    }

}
