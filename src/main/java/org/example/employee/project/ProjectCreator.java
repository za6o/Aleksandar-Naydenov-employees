package org.example.employee.project;

import org.example.employee.exceptions.DateFormatParserException;
import org.example.employee.project.dto.Project;

import java.util.List;

public interface ProjectCreator {

     List<Project> createProjectObject(List<List<String>> projectFields) throws DateFormatParserException;
}
