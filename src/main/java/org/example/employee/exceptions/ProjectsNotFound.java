package org.example.employee.exceptions;

public class ProjectsNotFound extends RuntimeException {

    public ProjectsNotFound(String message) {
        super(message);
    }
}
