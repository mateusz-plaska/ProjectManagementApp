package com.mplaska.projectmanagementapp.model.inputdata;


import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.coredata.TaskElement;

import java.time.LocalDate;

public class TaskInputData extends TaskElementInputData {
    private final Project assignProject;

    public TaskInputData(TaskElement selectedTaskElement, String name, String description,
                         Integer priority, LocalDate deadline, Integer lengthOfReminding,
                         Project assignProject) {
        super(selectedTaskElement, name, description, priority, deadline, lengthOfReminding);
        this.assignProject = assignProject;
    }

    public Project getAssignProject() {
        return assignProject;
    }
}
