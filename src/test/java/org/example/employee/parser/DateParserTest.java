package org.example.employee.parser;

import org.example.employee.exceptions.DateFormatParserException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

class DateParserTest {


    DateParser dateParser = new DateParser();


    @ParameterizedTest
    @ValueSource(strings = { "February 17, 2009", "02/17/2009", "17/02/2009", "2009/02/17", "2/17/2009","17/2/2009", "2009/2/17","2/17/2009",
    "February-17-2009","2009/February,17"})
    public void test(String date) throws DateFormatParserException {

        LocalDate localDate = dateParser.parseDateFromString(date);
        System.out.println(localDate);
    }



}