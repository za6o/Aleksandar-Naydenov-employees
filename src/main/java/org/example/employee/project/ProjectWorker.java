package org.example.employee.project;

import org.example.employee.dto.EmployeePairWorkingTogether;
import org.example.employee.dto.EmployeePairWorkingTogetherDataGrid;
import org.example.employee.exceptions.DateFormatParserException;
import org.example.employee.parser.DateParser;
import org.example.employee.project.dto.Project;
import org.example.employee.validator.FieldValidator;
import org.example.employee.validator.ProjectFieldValidator;

import java.util.Collections;
import java.util.List;

public class ProjectWorker {

    private final ProjectCreator projectCreator;
    private final ProjectParser projectParser;
    private final FieldValidator fieldValidator;

    public ProjectWorker(ProjectCreator projectCreator,FieldValidator fieldValidator, ProjectParser projectParser) {
        this.projectParser = projectParser;
        this.projectCreator = projectCreator;
        this.fieldValidator = fieldValidator;
    }


    public List<Project> validateAndConvertContentToProjects(List<List<String>> content) throws DateFormatParserException {
        if(content.isEmpty()){
            return Collections.emptyList();
        }
        fieldValidator.validateFields(content);

        return projectCreator.createProjectObject(content);
    }

    public EmployeePairWorkingTogether getThePairWithMaxWorkingHourForAllProjects(List<Project> projects) {
        return projectParser.getThePairWithMaxWorkingHourForAllProjects(projects);
    }

    public List<EmployeePairWorkingTogetherDataGrid> getAllProjectsForThePair(List<Project> projectsInTheSelectedFile, EmployeePairWorkingTogether pairWithMaxWorkingHourForAllProjects) {
        return projectParser.getAllProjectsForThePair(projectsInTheSelectedFile, pairWithMaxWorkingHourForAllProjects);
    }
}
