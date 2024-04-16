package org.example.employee.parser;

import lombok.AllArgsConstructor;
import org.example.employee.exceptions.ValidateFileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class CSVParser implements FileParser<List<List<String>>> {

    private final static String COMMA_DELIMITER = ",";

    @Override
    public List<List<String>> parseContent(String fileName) {

        List<List<String>> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                words.add(new ArrayList<>(Arrays.asList(values)));
            }

        } catch (IOException e) {
            throw new ValidateFileException("Couldn't read the content of the the file: " + fileName);
        }

        return words;
    }
    }
