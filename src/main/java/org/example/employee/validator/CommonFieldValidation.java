package org.example.employee.validator;

import org.apache.logging.log4j.util.Strings;

import java.util.List;
import java.util.Objects;

public class CommonFieldValidation {

    private final List<String> errors;

    public CommonFieldValidation(List<String> errors) {
        this.errors=errors;
    }

    protected void isEmptyOrNull(String fieldValue, String fieldName, Integer position){

        if (isEmpty(fieldValue)) {
            addError(fieldName + " can't be empty at row:" + position);
        }
        if (isNull(fieldValue)) {
            addError(fieldName + " can't be null at row:"  + position);
        }
    }

    protected boolean isEmpty(String fieldValue){
        return Objects.isNull(fieldValue) || Strings.isEmpty(fieldValue);
    }

    protected boolean isNull(String fieldValue){
        return fieldValue.equalsIgnoreCase("null");
    }

    protected void isInt(String fieldValue, String fieldName, Integer position){
        try {
            Integer.parseInt(fieldValue);
        } catch (NumberFormatException exception){
            addError(fieldName + " must be a whole number at row:" + position );
        }
    }

    protected void addError(String error){
        errors.add(error);
    }

}
