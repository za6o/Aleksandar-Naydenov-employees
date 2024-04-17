package org.example.employee.project;

import org.example.employee.exceptions.PairNotFound;
import org.example.employee.exceptions.ProjectsNotFound;
import org.example.employee.project.dto.Project;
import org.example.employee.dto.EmployeePair;
import org.example.employee.dto.EmployeePairWorkingTogether;
import org.example.employee.datagrid.dto.EmployeePairWorkingTogetherDataGrid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProjectParser {


    public List<EmployeePairWorkingTogetherDataGrid> getAllProjectsForThePair(List<Project> projectsInTheSelectedFile, EmployeePairWorkingTogether timeTogetherForAnEmployeePair) {

        if(projectsInTheSelectedFile.isEmpty() || Objects.isNull(timeTogetherForAnEmployeePair)){
            return Collections.emptyList();
        }

        Map<EmployeePair, EmployeePairWorkingTogether> workingHoursPerPair = getTimeAndProjectsByEmployeePair(projectsInTheSelectedFile);

        int employeeID1 = timeTogetherForAnEmployeePair.getEmployeePair().getEmployeeID1();
        int employeeID2 = timeTogetherForAnEmployeePair.getEmployeePair().getEmployeeID2();
        EmployeePairWorkingTogether employeePairWorkingTogether =
                workingHoursPerPair.get(new EmployeePair(employeeID1, employeeID2));

        List<EmployeePairWorkingTogetherDataGrid> result = new ArrayList<>();
        for (Map.Entry<Integer, Long> workingTimeByProjectId : employeePairWorkingTogether.getTimeWorkingTogetherByProject().entrySet()) {
            result.add(new EmployeePairWorkingTogetherDataGrid(employeeID1,employeeID2,workingTimeByProjectId.getKey(),workingTimeByProjectId.getValue()));
        }

        return result;
    }

    public EmployeePairWorkingTogether getThePairWithMaxWorkingHourForAllProjects(List<Project> projects) {

        if(projects.isEmpty()){
            throw new ProjectsNotFound("No projects found for parsing");
        }

        Map<EmployeePair, EmployeePairWorkingTogether> workingHoursPerPair = getTimeAndProjectsByEmployeePair(projects);
        EmployeePairWorkingTogether employeePairWorkingTogether =
                Collections.max(workingHoursPerPair.entrySet(), Comparator.comparingLong(o -> o.getValue().getTotalTimeWorkingTogetherInDays())).getValue();

        if(employeePairWorkingTogether.getTotalTimeWorkingTogetherInDays() == 0){
            throw new PairNotFound("No overlap of time in the projects");
        }

        return employeePairWorkingTogether;

    }

    public Map<EmployeePair, EmployeePairWorkingTogether> getTimeAndProjectsByEmployeePair(List<Project> projects) {

        Map<EmployeePair, EmployeePairWorkingTogether> result = new HashMap<>();

        for (int i = 0; i < projects.size(); i++) {

            int projectID = projects.get(i).getProjectID();
            int employeeID = projects.get(i).getEmployeeID();
            for (int j = i + 1; j < projects.size(); j++) {

                if (projectID == projects.get(j).getProjectID() &&
                        employeeID != projects.get(j).getEmployeeID()) {

                    EmployeePair employeePair = new EmployeePair(employeeID, projects.get(j).getEmployeeID());

                    long timeWorkingTogetherInThisProjectsByDays = calculateWorkingTime(projects.get(i),projects.get(j));
                    //first project of the pair
                    if(result.get(employeePair) == null){
                        HashMap<Integer, Long> timeWorkingTogetherByProject = new HashMap<>();
                        timeWorkingTogetherByProject.put(projectID,timeWorkingTogetherInThisProjectsByDays);
                        result.put(employeePair,new EmployeePairWorkingTogether(employeePair,timeWorkingTogetherByProject,timeWorkingTogetherInThisProjectsByDays));
                    } else {
                        EmployeePairWorkingTogether employeePairWorkingTogether = result.get(employeePair);
                        Map<Integer, Long> timeWorkingTogetherByProject = employeePairWorkingTogether.getTimeWorkingTogetherByProject();

                        long timeWorkingOnThisProject = timeWorkingTogetherByProject.compute(
                                projectID,
                                (key, oldValue) -> (oldValue == null ? 0 : oldValue) + timeWorkingTogetherInThisProjectsByDays
                        );

                        timeWorkingTogetherByProject.put(projectID,timeWorkingOnThisProject);

                        result.put(employeePair,new EmployeePairWorkingTogether(employeePair,timeWorkingTogetherByProject,
                                employeePairWorkingTogether.getTotalTimeWorkingTogetherInDays()+timeWorkingTogetherInThisProjectsByDays));

                    }
                }
            }

        }

        return result;
    }

    private long calculateWorkingTime(Project project1, Project project2) {
        if (project1.getDateTo() > project2.getDateFrom() && project1.getDateFrom() < project2.getDateTo() ||
                project2.getDateTo() > project1.getDateFrom() && project2.getDateFrom() < project1.getDateTo()) {
            return Math.min(project1.getDateTo(), project2.getDateTo()) - Math.max(project1.getDateFrom(), project2.getDateFrom());
        }
        return 0L;
    }


}
