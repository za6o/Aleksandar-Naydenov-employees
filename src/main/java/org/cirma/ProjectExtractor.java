package org.cirma;

import org.cirma.project.dto.Project;
import org.cirma.result.PairWithMaxWorkingHoursPerProject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class ProjectExtractor {


    public PairWithMaxWorkingHoursPerProject getTheProjectWithMaxWorkingHourForAPair(Set<Project> projects) {

        Map<Integer, List<Project>> projectsByProjectId = groupProjectsByProjectId(projects);

        PairWithMaxWorkingHoursPerProject pairWithMaxWorkingHoursPerProject = null;
        for (Map.Entry<Integer, List<Project>> projectByProjectId : projectsByProjectId.entrySet()) {

            for (int i = 1; i < projectByProjectId.getValue().size(); i++) {
                Project project1 = projectByProjectId.getValue().get(i - 1);
                Project project2 = projectByProjectId.getValue().get(i);

                long timeWorkingTogether = calculateWorkingTime(project1,project2);
//                if (project1.getDateTo() > project2.getDateFrom() && project1.getDateFrom() < project2.getDateTo() ||
//                        project2.getDateTo() > project1.getDateFrom() && project2.getDateFrom() < project1.getDateTo()) {
//
//                    timeWorkingTogether = Math.min(project1.getDateTo(), project2.getDateTo()) -
//                            Math.max(project1.getDateFrom(), project2.getDateFrom());
//                }

                if (Objects.isNull(pairWithMaxWorkingHoursPerProject) || pairWithMaxWorkingHoursPerProject.getWorkingTogetherInMs() < timeWorkingTogether) {
                    long differenceInDays = TimeUnit.DAYS.convert(timeWorkingTogether, TimeUnit.MILLISECONDS);
                    pairWithMaxWorkingHoursPerProject = new PairWithMaxWorkingHoursPerProject(timeWorkingTogether, differenceInDays,
                            project1.getEmployeeID(), project2.getEmployeeID());
                }
            }

        }

        return pairWithMaxWorkingHoursPerProject;

    }

    //todo rename Result to pairWithMaxWorkingHoursPerProject
    public List<PairWithMaxWorkingHoursPerProject> getAllProejctWorkingTogetherOfAPair(Set<Project> projects, PairWithMaxWorkingHoursPerProject pairWithMaxWorkingHoursPerProject) {

        Map<Integer, List<Project>> projectsByProjectId = groupProjectsByProjectId(projects);


        List<PairWithMaxWorkingHoursPerProject> result = new ArrayList<>();
        for (Map.Entry<Integer, List<Project>> projectByProjectId : projectsByProjectId.entrySet()) {


            boolean employeeID1 = false;
            boolean employeeID2 = false;
            Set<Project> projectsTogether = new HashSet<>();
            for (Project project : projectByProjectId.getValue()) {

                if (project.getEmployeeID() == pairWithMaxWorkingHoursPerProject.getEmployeeID1()) {
                    employeeID1 = true;
                    projectsTogether.add(project);
                } else if (project.getEmployeeID() == pairWithMaxWorkingHoursPerProject.getEmployeeID2()) {
                    employeeID2 = true;
                    projectsTogether.add(project);
                }
            }

            if(employeeID1 && employeeID2){
                result.add(getTheProjectWithMaxWorkingHourForAPair(projectsTogether));
            }
            else {
                projectsTogether.clear();
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

    public static Map<Integer, List<Project>> groupProjectsByProjectId(Set<Project> projects) {
        Map<Integer, List<Project>> projectMap = new TreeMap<>();

        for (Project project : projects) {
            int projectId = project.getProjectID();
            if (!projectMap.containsKey(projectId)) {
                projectMap.put(projectId, new ArrayList<>());
            }
            projectMap.get(projectId).add(project);
        }

        return projectMap;
    }

}
