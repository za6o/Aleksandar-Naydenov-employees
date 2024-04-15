package org.cirma.project;

import lombok.AllArgsConstructor;
import org.cirma.exceptions.DateFormatParserException;
import org.cirma.project.dto.Project;
import org.cirma.exceptions.ReadFileException;
import org.cirma.validor.FieldValidator;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class ProjectCreatorImpl implements ProjectCreator {

    private final DateParser dateParser;


    @Override
    public Set<Project> convertContent(List<List<String>> fields) throws DateFormatParserException {
        Set<Project> projects = new HashSet<>();

        for (List<String> projectFields : fields) {
            checkFields(projectFields);
            projects.add(createProjectObject(projectFields));

        }

        return projects;
    }

    private void checkFields(List<String> fields) {

        if (fields.size() != ProjectFields.values().length) {
            throw new ReadFileException("Number of column must be equal to:" + ProjectFields.values().length);
        }

        List<String> violationErrors = ProjectFields.validateFields(fields);
        if (!violationErrors.isEmpty()) {
            StringBuilder concatenatedErrorMessage = new StringBuilder();
            violationErrors.forEach(error -> {
                concatenatedErrorMessage.append(error);
                concatenatedErrorMessage.append("\n");
            });
            throw new ReadFileException(concatenatedErrorMessage.toString());
        }

    }

    private Project createProjectObject(List<String> fields) throws DateFormatParserException {
        int employeeId = Integer.parseInt(fields.get(ProjectFields.EMPLOYEE_ID.getPosition()));
        int projectId = Integer.parseInt(fields.get(ProjectFields.PROJECT_ID.getPosition()));
        Long startDate = dateParser.parseDateFromString(fields.get(ProjectFields.START_DATE.getPosition())).getTime();
        Long endDate = fields.get(ProjectFields.END_DATE.getPosition()).equalsIgnoreCase("null") ?
                new Date().getTime() :
                dateParser.parseDateFromString(fields.get(ProjectFields.END_DATE.getPosition())).getTime();

        return new Project(employeeId, projectId, startDate, endDate);
    }

}
