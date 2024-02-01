package com.mplaska.projectmanagementapp.model.chartsdata;



import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import com.mplaska.projectmanagementapp.model.timer.CurrentTime;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;

public class BurndownChartData {
    private final Project selectedProject;
    private final ArrayList<String> information;
    public BurndownChartData(Project selectedProject) {
        this.selectedProject = selectedProject;
        this.information = new ArrayList<>();
    }

    public ArrayList<String> getInformation() {
        return information;
    }

    //returns ArrayList of 3 series - String (series name) + ArrayList of Pair<data.toEpochDay(), Integer - amount of tasks>
    public ArrayList<Pair<String, ArrayList<Pair<Number, Number>>>> createDataset() {
        ArrayList<Pair<String, ArrayList<Pair<Number, Number>>>> dataset = new ArrayList<>();
        dataset.add(new Pair<>("Right line", new ArrayList<>()));
        dataset.add(new Pair<>("Current progress line", new ArrayList<>()));
        dataset.add(new Pair<>("Curren progress line - after deadline", new ArrayList<>()));

        int currentAmountOfTasks = getNumberOfTasksAtStart();
        if (currentAmountOfTasks == 0) {
            information.add("No tasks assigned before the start of the project");
        }

        //right line
        dataset.get(0).getValue().add(new Pair<>(selectedProject.getStartDate().toEpochDay(), currentAmountOfTasks));
        dataset.get(0).getValue().add(new Pair<>(selectedProject.getDeadline().toEpochDay(), 0));

        //current progress line
        dataset.get(1).getValue().add(new Pair<>(selectedProject.getStartDate().toEpochDay(), currentAmountOfTasks));

        if (CurrentTime.getCurrentSystemTime().isEqual(selectedProject.getStartDate())) {
            information.add("The start of the project is today");
        } else if (CurrentTime.getCurrentSystemTime().isBefore(selectedProject.getStartDate())) {
            information.add("The project has not yet started");
        } else if (CurrentTime.getCurrentSystemTime().isAfter(selectedProject.getDeadline())) {
            information.add("The project deadline has passed");

            currentAmountOfTasks = createCurrentLine(dataset.get(1).getValue(), currentAmountOfTasks,
                    selectedProject.getStartDate().plusDays(1),
                    selectedProject.getDeadline().plusDays(1));
            //current line after deadline
            createCurrentLine(dataset.get(2).getValue(), currentAmountOfTasks,
                    selectedProject.getDeadline(),
                    CurrentTime.getCurrentSystemTime().plusDays(1));
        } else {
            createCurrentLine(dataset.get(1).getValue(), currentAmountOfTasks,
                    selectedProject.getStartDate().plusDays(1),
                    CurrentTime.getCurrentSystemTime().plusDays(1));
        }

        return dataset;
    }

    private int createCurrentLine(ArrayList<Pair<Number, Number>> currentLine, int currentAmountOfTasks,
                                  LocalDate startDateOfIteration, LocalDate endDateBoundOfIteration) {
        for (LocalDate date = startDateOfIteration; date.isBefore(endDateBoundOfIteration); date = date.plusDays(1)) {
            for (Task task : getTasksOfSelectedProject()) {
                if (task.getAdditionDate().isEqual(date)) {
                    currentAmountOfTasks++;
                }

                if (task.getElementCompletionDate() != null && task.getElementCompletionDate().isEqual(date)) {
                    currentAmountOfTasks--;
                }
            }

            currentLine.add(new Pair<>(date.toEpochDay(), currentAmountOfTasks));
        }

        return currentAmountOfTasks;
    }

    private ArrayList<Task> getTasksOfSelectedProject() {
        ArrayList<Task> tasksOfSelectedProject = new ArrayList<>();

        for (int i = 0; i < ManagerFacade.getInstance().getTaskStorage().getList().size(); ++i) {
            if (ManagerFacade.getInstance().getTaskStorage().getList().get(i).getAssignedProject().isEqual(selectedProject)) {
                tasksOfSelectedProject.add(ManagerFacade.getInstance().getTaskStorage().getList().get(i));
            }
        }
        return tasksOfSelectedProject;
    }


    /** ilosc zadana takch ze
     1)addition_date przed startem_project
     2)nieukonczone albo jesli ukonczone to po starcie projektu **/
    private int getNumberOfTasksAtStart () {
        int numberOfTasksAtStart = 0;
        for (Task task : getTasksOfSelectedProject()) {
            if (task.getAdditionDate().isBefore(selectedProject.getStartDate()) || task.getAdditionDate().isEqual(selectedProject.getStartDate())) {
                if (!task.isFinished() || task.getElementCompletionDate().isAfter(selectedProject.getStartDate())) {
                    numberOfTasksAtStart++;
                }
            }
        }
        return numberOfTasksAtStart;
    }
}
