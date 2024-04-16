package org.example.employee;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.employee.config.DataGridDisplayDataConfig;
import org.example.employee.config.DisplayDataConfig;
import org.example.employee.dto.EmployeePairWorkingTogether;
import org.example.employee.parser.DateParser;
import org.example.employee.project.ProjectCreatorImpl;
import org.example.employee.project.ProjectWorker;
import org.example.employee.project.dto.Project;
import org.example.employee.parser.CSVParser;
import org.example.employee.parser.FileParser;
import org.example.employee.dto.EmployeePairWorkingTogetherDataGrid;
import org.example.employee.validator.ProjectFieldValidator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static Optional<String> chooseAFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "CSV & TXT files", "csv", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        File selectedFile;
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            selectedFile = chooser.getSelectedFile();
            System.out.println("You chose to open this file: " +
                    selectedFile.getName());
            return Optional.of(selectedFile.getAbsolutePath());
        }
        return Optional.empty();
    }


    @Override
    public void start(Stage stage) throws Exception {

        FileParser<List<List<String>>> csvParser = new CSVParser();

        URL resource = Main.class.getClassLoader().getResource("example.cvs");
        if (Objects.isNull(resource)) {
            System.out.println("example.cvs is not found.");
            return;
        }

        List<List<String>> content =
                csvParser.parseContent(resource.getPath());

        DateParser dateParser = new DateParser();
        ProjectWorker projectWorker = new ProjectWorker(new ProjectCreatorImpl(dateParser),new ProjectFieldValidator(dateParser));

        List<Project> projects = projectWorker.validateAndConvertContentToProjects(content);
        EmployeePairWorkingTogether pairWithMaxWorkingHourForAllProjects = projectWorker.getThePairWithMaxWorkingHourForAllProjects(projects);
        System.out.println(pairWithMaxWorkingHourForAllProjects);

        List<EmployeePairWorkingTogetherDataGrid> employeePairWorkingTogetherDataGrids = new ArrayList<>();
        Optional<String> fileToOPen = chooseAFile();
        if (fileToOPen.isPresent()) {
            List<List<String>> contentOfSelectedFile = csvParser.parseContent(fileToOPen.get());
            List<Project> projectsInTheSelectedFile = projectWorker.validateAndConvertContentToProjects(contentOfSelectedFile);
            employeePairWorkingTogetherDataGrids = projectWorker.getAllProjectsForThePair(projectsInTheSelectedFile, pairWithMaxWorkingHourForAllProjects);
        }

        DisplayDataConfig<TableView<EmployeePairWorkingTogetherDataGrid>> displayDataConfig = new DataGridDisplayDataConfig();
        TableView<EmployeePairWorkingTogetherDataGrid> tableView = displayDataConfig.configure();

        ObservableList<EmployeePairWorkingTogetherDataGrid> data = FXCollections.observableArrayList(employeePairWorkingTogetherDataGrids);

        tableView.setItems(data);
        Scene scene = new Scene(tableView);
        stage.setScene(scene);
        stage.show();
    }
}