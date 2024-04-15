package org.cirma.project;

import lombok.Getter;
import org.cirma.exceptions.DateFormatParserException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public enum ProjectFields {

    EMPLOYEE_ID(0,"EmployeeID"){
        @Override
        List<String> validateField(String field) {
            List<String> errors = new ArrayList<>();
            errors.addAll(checkForEmptyOrNull(this.name(),field));
            try {
                Integer.parseInt(field);
            } catch (NumberFormatException exception){
                errors.add(this.getName() + " must be number");
            }
            return errors;
        }
    },
    PROJECT_ID(1,"ProjectID"){
        @Override
        List<String> validateField(String field) {
            List<String> errors = new ArrayList<>();
            errors.addAll(checkForEmptyOrNull(this.getName(),field));
            try {
                Integer.parseInt(field);
            } catch (NumberFormatException exception){
                errors.add(this.name() + " must be number");
            }
            return errors;
        }
    },
    START_DATE(2,"StartDate"){
        @Override
        List<String> validateField(String field) {
            List<String> errors = new ArrayList<>();
            errors.addAll(checkForEmptyOrNull(this.getName(),field));
            return errors;
        }
    },
    END_DATE(3,"EndDate"){
        @Override
        List<String> validateField(String field) {
            List<String> errors = new ArrayList<>();
            try {
                if (!field.equalsIgnoreCase("null") && dateParser.parseDateFromString(field).after(new Date())) {
                        errors.add(this.getName() + "End date must not pass today.");
                }
            } catch (DateFormatParserException e) {
                errors.add("Couldn't parse end date.");
            }
            return errors;
        }
    };

    @Getter
    private final int position;

    @Getter
    private final String name;

    final DateParser dateParser= new DateParser();

    ProjectFields(int position, String name) {
        this.position = position;
        this.name =name;
    }

    public static List<String> validateFields(List<String> values) {
        List<String> errors = new ArrayList<>();

        for (ProjectFields field : ProjectFields.values()) {
            errors.addAll(field.validateField(values.get(field.getPosition())));
        }

        return errors;
    }

    abstract List<String> validateField(String field);

    List<String> checkForEmptyOrNull(String fieldName, String field){

        List<String> errors =new ArrayList<>();
        if (Objects.isNull(field)) {
            errors.add(fieldName + " can't be empty");
        }
        if (fieldName.equalsIgnoreCase("null")) {
            errors.add(fieldName + " can't be null");
        }
        return errors;
    }

}
