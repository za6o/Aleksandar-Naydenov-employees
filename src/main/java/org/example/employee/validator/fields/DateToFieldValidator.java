package org.example.employee.validator.fields;

import org.example.employee.exceptions.DateFormatParserException;
import org.example.employee.parser.DateParser;
import org.example.employee.project.ProjectFields;
import org.example.employee.validator.CommonFieldValidation;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiConsumer;

public class DateToFieldValidator extends CommonFieldValidation implements BiConsumer<List<String>,Integer> {

    private final DateParser dateParser;

    public DateToFieldValidator(List<String> errors, DateParser dateParser) {
        super(errors);
        this.dateParser=dateParser;
    }

    @Override
    public void accept(List<String> strings, Integer position) {

        String fieldValue = strings.get(ProjectFields.DATE_TO.getPosition());
        String fieldName = ProjectFields.DATE_TO.getName();

        if(isEmpty(fieldValue)){
            addError(fieldName + " can't be empty at row:"+ position);
            return;
        }


        try {
            if (!isNull(fieldValue)){
                LocalDate dateTo = dateParser.parseDateFromString(fieldValue);

                if(dateTo.isAfter(LocalDate.now())){
                    addError(fieldName + " must not pass today at row:"+ position);
                }

                if(!isEmpty(strings.get(ProjectFields.DATE_FROM.getPosition())) && !isNull(strings.get(ProjectFields.DATE_FROM.getPosition()))
                        && dateTo.isBefore(dateParser.parseDateFromString(strings.get(ProjectFields.DATE_FROM.getPosition())))){
                    addError(fieldName + " must be after "+ ProjectFields.DATE_FROM.getName() +" at row:"+ position);
                }
            }
        } catch (DateFormatParserException e) {
            addError(fieldName + " couldn't be parsed at row:" + position);
        }
    }

}
