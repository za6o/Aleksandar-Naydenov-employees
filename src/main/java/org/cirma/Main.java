package org.cirma;

import org.cirma.exceptions.DateFormatParserException;
import org.cirma.project.DateParser;
import org.cirma.project.ProjectCreator;
import org.cirma.project.ProjectCreatorImpl;
import org.cirma.project.dto.Project;
import org.cirma.parser.CSVParser;
import org.cirma.parser.FileParser;
import org.cirma.result.PairWithMaxWorkingHoursPerProject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws DateFormatParserException {

        FileParser<List<List<String>>> csvParser = new CSVParser();


        URL resource = Main.class.getClassLoader().getResource("example.cvs");
        if(Objects.isNull(resource)){
            System.out.println("example.cvs is not found.");
            return;
        }
        List<List<String>> content =
                csvParser.parseContent(resource.getPath());

        ProjectCreator projectCreator = new ProjectCreatorImpl(new DateParser());
        Set<Project> projects = projectCreator.convertContent(content);
        PairWithMaxWorkingHoursPerProject theProjectWithMaxWorkingHourForAPair = new ProjectExtractor().getTheProjectWithMaxWorkingHourForAPair(projects);
        System.out.println(theProjectWithMaxWorkingHourForAPair);



//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Project_report");
//        sheet.setDefaultColumnWidth(16);
//
//        Row providerNameRow = sheet.createRow(0);
//        Cell providerNameCell = providerNameRow.createCell(4);
//        providerNameCell.setCellValue("Test");




        Optional<String> fileToOPen = chooseAFile();
        if (fileToOPen.isPresent()) {
            List<List<String>> contentOfSelectedFile = csvParser.parseContent(fileToOPen.get());
            Set<Project> projectsInTheSelectedFile = projectCreator.convertContent(contentOfSelectedFile);
            new ProjectExtractor().getAllProejctWorkingTogetherOfAPair(projectsInTheSelectedFile,theProjectWithMaxWorkingHourForAPair);

        }

    }

    private static Optional<String> chooseAFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "CSV & TXT files",  "csv","txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        File selectedFile =null;
        if(returnVal == JFileChooser.APPROVE_OPTION) {

            selectedFile = chooser.getSelectedFile();
            System.out.println("You chose to open this file: " +
                    selectedFile.getName());
            return Optional.of(selectedFile.getAbsolutePath());
        }
        return Optional.empty();
    }


}