package com.mplaska.projectmanagementapp.model.inputdata;

import com.mplaska.projectmanagementapp.model.coredata.TaskElement;

import java.time.LocalDate;

public class ProjectInputData extends TaskElementInputData {
    private final LocalDate startDate;

    public ProjectInputData(TaskElement selectedTaskElement, String name, String description,
                            Integer priority, LocalDate deadline, Integer lengthOfReminding,
                            LocalDate startDate) {
        super(selectedTaskElement, name, description, priority, deadline, lengthOfReminding);
        this.startDate = startDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}
