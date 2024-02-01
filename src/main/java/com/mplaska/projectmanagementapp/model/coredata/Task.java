package com.mplaska.projectmanagementapp.model.coredata;


import com.mplaska.projectmanagementapp.model.database.datastorage.ListStorage;
import com.mplaska.projectmanagementapp.model.timer.CurrentTime;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Task extends TaskElement implements Serializable {
    @Serial
    private static final long serialVersionUID = 5368479678565001077L;
    private LocalDate additionDate;
    private Project assignedProject;
    private ListStorage<Subtask> subtasksList;

    public Task(String name, String detailedDescription, int priority, LocalDate deadline, int amountOfDaysSinceWhenToRemind,
                Project assignedProject) {
        super(name, detailedDescription, priority, deadline, amountOfDaysSinceWhenToRemind);
        this.assignedProject = assignedProject;
        this.additionDate = CurrentTime.getCurrentSystemTime();
        this.subtasksList = new ListStorage<>();
    }

    public Task(String name, String detailedDescription, int priority, LocalDate deadline, int amountOfDaysSinceWhenToRemind,
                boolean finished, LocalDate elementCompletionDate, Project assignedProject, LocalDate additionDate, ListStorage<Subtask> subtasksList) {
        this(name, detailedDescription, priority, deadline, amountOfDaysSinceWhenToRemind, assignedProject);
        this.additionDate = additionDate;
        this.subtasksList = subtasksList;
        setFinished(finished, elementCompletionDate);
    }

    @Override
    public boolean isEqual(TaskElement comparedTask) {
        return super.isEqual(comparedTask) && assignedProject.isEqual(((Task)comparedTask).getAssignedProject()) && subtasksList.equals(((Task)comparedTask).getSubtasksList());
    }

    @Override
    public void operationsIfCompletionStatusHasChanged() {
        if (!this.isFinished() && this.checkPossibleCompletionOfTask()) {
            this.setFinished(true, CurrentTime.getCurrentSystemTime());
        } else if (this.isFinished() && !this.checkPossibleCompletionOfTask()) {
            this.setFinished(false, null);
        }
    }

    public boolean checkPossibleCompletionOfTask() {
        for (Subtask subtask : subtasksList.getList()) {
            if (!subtask.isFinished()) {
                return false;
            }
        }
        return true;
    }

    public int amountOfCompletedSubtasks() {
        int amount = 0;
        for (Subtask subtask : subtasksList.getList()) {
            if (subtask.isFinished()) {
                amount++;
            }
        }
        return amount;
    }

    public double getPercentValueOfCompletedSubtasks() {
        if (!getSubtasksList().getList().isEmpty()) {
            return (double) amountOfCompletedSubtasks() / getSubtasksList().getList().size() * 100.0;
        }
        return 100.0;
    }

    public void setAssignedProject(Project changedProject) {
        assignedProject = changedProject;
    }

    public Project getAssignedProject() {
        return assignedProject;
    }

    public LocalDate getAdditionDate() {
        return additionDate;
    }

    public ListStorage<Subtask> getSubtasksList() {
        return subtasksList;
    }

    /**
     * used in tests
     */
    public void setAdditionDate(LocalDate date) {
        this.additionDate = date;
    }
}
