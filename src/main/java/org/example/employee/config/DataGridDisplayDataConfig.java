package org.example.employee.config;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.employee.dto.EmployeePairWorkingTogetherDataGrid;

public class DataGridDisplayDataConfig implements DisplayDataConfig<TableView<EmployeePairWorkingTogetherDataGrid>> {

    @Override
    public TableView<EmployeePairWorkingTogetherDataGrid> configure() {
        TableView<EmployeePairWorkingTogetherDataGrid> tableView = new TableView<>();

        TableColumn<EmployeePairWorkingTogetherDataGrid, Integer> employee1Column = new TableColumn<>("Employee ID 1");
        employee1Column.setCellValueFactory(new PropertyValueFactory<>("EmployeeID1"));

        TableColumn<EmployeePairWorkingTogetherDataGrid, Integer> employee2Column = new TableColumn<>("Employee ID 2");
        employee2Column.setCellValueFactory(new PropertyValueFactory<>("EmployeeID2"));

        TableColumn<EmployeePairWorkingTogetherDataGrid, Integer> ProjectIDColumn = new TableColumn<>("Project ID");
        ProjectIDColumn.setCellValueFactory(new PropertyValueFactory<>("ProjectID"));

        TableColumn<EmployeePairWorkingTogetherDataGrid, Integer> workingDaysColumn = new TableColumn<>("Working Days");
        workingDaysColumn.setCellValueFactory(new PropertyValueFactory<>("workingTimeInDays"));

        tableView.getColumns().addAll(employee1Column, employee2Column,ProjectIDColumn, workingDaysColumn);

        return tableView;
    }
}
