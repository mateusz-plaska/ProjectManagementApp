package com.mplaska.projectmanagementapp.model.inputdata;


import com.mplaska.projectmanagementapp.model.coredata.TaskElement;

import java.time.LocalDate;

public abstract class TaskElementInputData {
    private final TaskElement selectedTaskElement;
    private final String name;
    private final String description;
    private final Integer priority;
    private final LocalDate deadline;
    private final Integer lengthOfReminding;

    protected TaskElementInputData(TaskElement selectedTaskElement, String name, String description,
                                   Integer priority, LocalDate deadline, Integer lengthOfReminding) {
        this.selectedTaskElement = selectedTaskElement;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.lengthOfReminding = lengthOfReminding;
    }

    public TaskElement getSelectedTaskElement() {
        return selectedTaskElement;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPriority() {
        return priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public Integer getLengthOfReminding() {
        return lengthOfReminding;
    }
}
