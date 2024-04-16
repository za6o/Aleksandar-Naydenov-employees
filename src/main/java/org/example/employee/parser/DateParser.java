package org.example.employee.parser;

import org.example.employee.exceptions.DateFormatParserException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class DateParser {

    private final List<String> formats;

    public DateParser() {
        formats = new ArrayList<>();
        createDatePatter();
    }

    private void createDatePatter() {
        String[] years = {"yyyy", "yy"};
        String[] months = {"MMMM", "MMM", "MM", "M"};
        String[] days = {"dd", "d"};
        String[] separators = {"-", "/", ".", "_", " ", ", ", ","};

        for (String year : years) {
            for (String month : months) {
                for (String day : days) {
                    for (String sep1 : separators) {
                        for (String sep2 : separators) {
                            String format = year + sep1 + month + sep2 + day;
                            this.formats.add(format);
                            format = year + sep1 + day + sep2 + month;
                            this.formats.add(format);
                            format = month + sep1 + year + sep2 + day;
                            this.formats.add(format);
                            format = month + sep1 + day + sep2 + year;
                            this.formats.add(format);
                            format = day + sep1 + month + sep2 + year;
                            this.formats.add(format);
                            format = day + sep1 + year + sep2 + month;
                            this.formats.add(format);
                        }
                    }
                }
            }
        }
    }


    public LocalDate parseDateFromString(String field) throws DateFormatParserException {
        for (String format : formats) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
            try {
                return LocalDate.parse(field, dtf);
            } catch (DateTimeParseException e) {
            }
        }
        throw new DateFormatParserException("Couldn't parse the date");
    }
}
