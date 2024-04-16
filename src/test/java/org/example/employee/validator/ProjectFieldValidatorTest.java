package org.example.employee.validator;

import org.example.employee.exceptions.ValidateFileException;
import org.example.employee.parser.DateParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ProjectFieldValidatorTest {

    FieldValidator fieldValidator = new ProjectFieldValidator(new DateParser());

    List<List<String>> rows;

    @BeforeEach
    public void setUpValidRow() {
        rows = new ArrayList<>();
        rows.add(Arrays.asList("1", "12", "2012-01-01", "2012-02-01"));
    }

    @Test
    void validateFields_invalidEmployeeID() {
        rows.add(Arrays.asList("12e", "132", "2012-01-01", "2012-02-01"));
        Exception expectedException = null;
        try {

            fieldValidator.validateFields(rows);
        }catch (ValidateFileException e){
            expectedException = e;
        }

        Assertions.assertNotNull(expectedException);
        Assertions.assertEquals("EmployeeID must be a whole number at row:1\n",expectedException.getMessage());
    }

    @Test
    void validateFields_invalidProjectID() {
        rows.add(Arrays.asList("12", "132.00", "2012-01-01", "2012-02-01"));
        Exception expectedException = null;
        try {

            fieldValidator.validateFields(rows);
        }catch (ValidateFileException e){
            expectedException = e;
        }

        Assertions.assertNotNull(expectedException);
        Assertions.assertEquals("ProjectID must be a whole number at row:1\n",expectedException.getMessage());
    }

    @Test
    void validateFields_dateFrom_empty() {
        rows.add(Arrays.asList("12", "132", "", "2012-02-01"));
        Exception expectedException = null;
        try {

            fieldValidator.validateFields(rows);
        }catch (ValidateFileException e){
            expectedException = e;
        }

        Assertions.assertNotNull(expectedException);
        Assertions.assertEquals("DateFrom can't be empty or null at row:1\n",expectedException.getMessage());
    }

    @Test
    void validateFields_dateTo_empty() {
        rows.add(Arrays.asList("12", "132", "2012-02-01", ""));
        Exception expectedException = null;
        try {

            fieldValidator.validateFields(rows);
        }catch (ValidateFileException e){
            expectedException = e;
        }

        Assertions.assertNotNull(expectedException);
        Assertions.assertEquals("DateTo can't be empty at row:1\n",expectedException.getMessage());
    }

    @Test
    void validateFields_dateFrom_null() {
        rows.add(Arrays.asList("12", "132", "NULL", "2012-02-01"));
        Exception expectedException = null;
        try {

            fieldValidator.validateFields(rows);
        }catch (ValidateFileException e){
            expectedException = e;
        }

        Assertions.assertNotNull(expectedException);
        Assertions.assertEquals("DateFrom can't be empty or null at row:1\n",expectedException.getMessage());
    }

    @Test
    void validateFields_dateTo_in_the_future() {
        rows.add(Arrays.asList("12", "132", "2017-02-01", "2027-02-01"));
        Exception expectedException = null;
        try {

            fieldValidator.validateFields(rows);
        }catch (ValidateFileException e){
            expectedException = e;
        }

        Assertions.assertNotNull(expectedException);
        Assertions.assertEquals("DateTo must not pass today at row:1\n",expectedException.getMessage());
    }

    @Test
    void validateFields_dateTo_before_dateFrom() {
        rows.add(Arrays.asList("12", "132", "2021-02-01", "2017-02-01"));
        Exception expectedException = null;
        try {

            fieldValidator.validateFields(rows);
        }catch (ValidateFileException e){
            expectedException = e;
        }

        Assertions.assertNotNull(expectedException);
        Assertions.assertEquals("DateTo must be after DateFrom at row:1\n",expectedException.getMessage());
    }
}