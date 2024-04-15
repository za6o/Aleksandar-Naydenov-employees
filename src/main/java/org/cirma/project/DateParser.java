package org.cirma.project;

import org.cirma.exceptions.DateFormatParserException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {

    private final String[] formats = {
            "yyyy-MM-dd",
            "dd/MM/yyyy",
            "MMMM dd, yyyy",
            "MMM dd, yyyy",
            "MM/dd/yyyy",
    };


    public Date parseDateFromString(String field) throws DateFormatParserException {
        for (String format : formats) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(field);

            } catch (ParseException e) {}
        }
        throw  new DateFormatParserException("Couldn't parse the date");
    }
}
