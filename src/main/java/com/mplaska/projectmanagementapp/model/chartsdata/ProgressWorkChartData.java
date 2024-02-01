package com.mplaska.projectmanagementapp.model.chartsdata;


import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import javafx.util.Pair;

import java.util.ArrayList;

public class ProgressWorkChartData {
    private final Project selectedProject;

    public ProgressWorkChartData(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    public ArrayList<Pair<String, Double>> createDataset() {
        ArrayList<Pair<String, Double>> dataset = new ArrayList<>();

        for (Task task : ManagerFacade.getInstance().getTaskStorage().getList()) {
            if (task.getAssignedProject().isEqual(selectedProject)) {
                double completionPercent = 100.0;
                if (!task.getSubtasksList().getList().isEmpty()) {
                    completionPercent = (double) task.amountOfCompletedSubtasks() / task.getSubtasksList().getList().size() * 100;
                }

                dataset.add(new Pair<>(task.getName(), completionPercent));
            }
        }

        return dataset;
    }
}
