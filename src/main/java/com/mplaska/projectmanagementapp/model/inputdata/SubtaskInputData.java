package com.mplaska.projectmanagementapp.model.inputdata;


import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.coredata.TaskElement;

import java.time.LocalDate;

public class SubtaskInputData extends TaskElementInputData {
    private final Task operatedTask;
    private final String assignedPerson;

    public SubtaskInputData(TaskElement selectedTaskElement, Task operatedTask, String name, String description,
                            int priority, LocalDate deadline, int lengthOfReminding, String assignPerson) {
        super(selectedTaskElement, name, description, priority, deadline, lengthOfReminding);
        this.operatedTask = operatedTask;
        this.assignedPerson = assignPerson;
    }

    public Task getOperatedTask() {
        return operatedTask;
    }

    public String getAssignedPerson() {
        return assignedPerson;
    }
}
