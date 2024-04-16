package org.example.employee.exceptions;

public class ValidateFileException extends RuntimeException {

    public ValidateFileException(String errors) {
        super(errors);
    }
}
