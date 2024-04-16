package org.example.employee.validator;

import org.example.employee.exceptions.ValidateFileException;
import org.example.employee.parser.DateParser;
import org.example.employee.project.ProjectFields;
import org.example.employee.validator.fields.DateFromFieldValidator;
import org.example.employee.validator.fields.DateToFieldValidator;
import org.example.employee.validator.fields.EmployeeIDFieldValidator;
import org.example.employee.validator.fields.ProjectIDFieldValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;


public class ProjectFieldValidator implements FieldValidator{

    private final List<String> errors;

    private final List<BiConsumer<List<String>,Integer>> validations = new ArrayList<>();
    public ProjectFieldValidator(DateParser dateParser) {
        errors = new ArrayList<>();
        validations.add(new EmployeeIDFieldValidator(errors));
        validations.add(new ProjectIDFieldValidator(errors));
        validations.add(new DateFromFieldValidator(errors,dateParser));
        validations.add(new DateToFieldValidator(errors,dateParser));
    }

    @Override
    public void validateFields(List<List<String>> values) {

        for (int i = 0; i < values.size(); i++) {

            if (values.get(i).size() != ProjectFields.values().length) {
                throw new ValidateFileException("Number of column must be equal to:" + ProjectFields.values().length + " at row:" + i);
            }

            for (BiConsumer<List<String>, Integer>validation : validations) {
                validation.accept(values.get(i),i);
            }
        }

        if (!errors.isEmpty()) {
            StringBuilder concatenatedErrorMessage = new StringBuilder();
            errors.forEach(error -> {
                concatenatedErrorMessage.append(error);
                concatenatedErrorMessage.append("\n");
            });
            throw new ValidateFileException(concatenatedErrorMessage.toString());
        }
    }
}
