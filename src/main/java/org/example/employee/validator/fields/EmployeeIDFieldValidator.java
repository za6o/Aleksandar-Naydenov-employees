package org.example.employee.validator.fields;

import org.example.employee.project.ProjectFields;
import org.example.employee.validator.CommonFieldValidation;

import java.util.List;
import java.util.function.BiConsumer;

public class EmployeeIDFieldValidator  extends CommonFieldValidation implements BiConsumer<List<String>,Integer> {

    public EmployeeIDFieldValidator(List<String> errors) {
        super(errors);
    }

    @Override
    public void accept(List<String> strings, Integer position) {
        String fieldValue = strings.get(ProjectFields.EMPLOYEE_ID.getPosition());
        String fieldName = ProjectFields.EMPLOYEE_ID.getName();
        isEmptyOrNull(fieldValue,fieldName, position);
        isInt(fieldValue,fieldName, position);
    }

}
