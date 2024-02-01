package com.mplaska.projectmanagementapp.model.coredata;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Subtask extends TaskElement implements Serializable {
    @Serial
    private static final long serialVersionUID = -2122200432572337137L;
    private final String assignedPerson;
    private int grade = -1;

    public Subtask(String name, String detailedDescription, int priority, LocalDate deadline, int amountOfDaysSinceToRemind, String assignedPerson) {
        super(name, detailedDescription, priority, deadline, amountOfDaysSinceToRemind);
        this.assignedPerson = assignedPerson;
    }

    public Subtask(String name, String detailedDescription, int priority, LocalDate deadline, int amountOfDaysSinceToRemind,
                   String assignedPerson, boolean isFinished, LocalDate elementCompletionDate) {
        this(name, detailedDescription, priority, deadline, amountOfDaysSinceToRemind, assignedPerson);
        setFinished(isFinished, elementCompletionDate);
    }

    @Override
    public boolean isEqual(TaskElement comparedSubtask) {
        return super.isEqual(comparedSubtask) && assignedPerson.equals(((Subtask)comparedSubtask).assignedPerson);
    }

    @Override
    public void operationsIfCompletionStatusHasChanged() {
        if (!this.isFinished()) {
            this.setGrade(-1);
        }
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getAssignedPerson() {
        return assignedPerson;
    }

    public String getGradeString() {
        if (grade >= 0) {
            return Integer.toString(grade);
        }
        return "-";
    }
}
