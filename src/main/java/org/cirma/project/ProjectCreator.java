package org.cirma.project;

import org.cirma.exceptions.DateFormatParserException;
import org.cirma.project.dto.Project;

import java.util.List;
import java.util.Set;

public interface ProjectCreator {

    Set<Project> convertContent(List<List<String>> fields) throws DateFormatParserException;
}
