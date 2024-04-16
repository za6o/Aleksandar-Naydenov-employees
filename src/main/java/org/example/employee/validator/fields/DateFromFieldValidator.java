package org.example.employee.validator.fields;

import org.example.employee.exceptions.DateFormatParserException;
import org.example.employee.parser.DateParser;
import org.example.employee.project.ProjectFields;
import org.example.employee.validator.CommonFieldValidation;

import java.util.List;
import java.util.function.BiConsumer;

public class DateFromFieldValidator extends CommonFieldValidation implements BiConsumer<List<String>, Integer> {

    private final DateParser dateParser;

    public DateFromFieldValidator(List<String> errors, DateParser dateParser) {
        super(errors);
        this.dateParser = dateParser;
    }

    @Override
    public void accept(List<String> strings, Integer position) {
        String fieldValue = strings.get(ProjectFields.DATE_FROM.getPosition());
        String fieldName = ProjectFields.DATE_FROM.getName();

        if (isNull(fieldValue) || isEmpty(fieldValue)) {
            addError(fieldName + " can't be empty or null at row:" + position);
            return;
        }

        try {
            dateParser.parseDateFromString(fieldValue);
        } catch (DateFormatParserException e) {
            addError(fieldName + " couldn't be parsed at row:" + position);
        }
    }

}
