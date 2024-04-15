package org.cirma.validor;

import org.cirma.project.ProjectFields;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;



//todo add support for different dates
public class ProjectFieldValidator implements FieldValidator{


    @Override
    public List<String> validateFields(List<String> values) {

        List<String> errors = new ArrayList<>();


        String employeeID = values.get(ProjectFields.EMPLOYEE_ID.getPosition());

        errors.addAll(checkForEmptyOrNull("EmployeeID" ,employeeID));
        try {
           Integer.parseInt(employeeID);
        } catch (NumberFormatException exception){
            errors.add("Employee ID must be number");
        }


        String projectID = values.get(ProjectFields.PROJECT_ID.getPosition());
        errors.addAll(checkForEmptyOrNull("ProjectID" ,employeeID));
        try {
            Integer.parseInt(projectID);
        } catch (NumberFormatException exception){
            errors.add("Project ID must be number");
        }

        String startDate = values.get(ProjectFields.START_DATE.getPosition());
        errors.addAll(checkForEmptyOrNull("StartDate" ,startDate));

        try {
            String endDate = values.get(ProjectFields.END_DATE.getPosition());
            if (!endDate.equalsIgnoreCase("null") && new SimpleDateFormat("yyyy-MM-dd").parse(endDate).after(new Date())) {
                errors.add("End date must not pass today.");
            }
        } catch (ParseException e) {
            errors.add("Couldn't parse end date.");
        }

        return errors;
    }


    private List<String> checkForEmptyOrNull(String fieldName, String field){

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
