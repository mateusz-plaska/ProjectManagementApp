package com.mplaska.projectmanagementapp.model.coredata;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public abstract class TaskElement implements Serializable {
    @Serial
    private static final long serialVersionUID = -7475166351271141386L;
    private final String name;
    private final String detailedDescription;
    private final int priority;
    private final LocalDate deadline;
    private boolean finished;
    private final int amountOfDaysSinceWhenToRemind;
    private LocalDate elementCompletionDate;

    protected TaskElement(String name, String detailedDescription, int priority, LocalDate deadline, int amountOfDaysSinceWhenToRemind) {
        this.name = name;
        this.detailedDescription = detailedDescription;
        this.priority = priority;
        this.deadline = deadline;
        this.amountOfDaysSinceWhenToRemind = amountOfDaysSinceWhenToRemind;

        this.finished = false;
        this.elementCompletionDate = null;
    }

    public boolean isEqual(TaskElement comparedElement) {
        return name.equals(comparedElement.getName()) && detailedDescription.equals(comparedElement.getDetailedDescription())
                && deadline.equals(comparedElement.getDeadline()) && priority == comparedElement.priority
                && amountOfDaysSinceWhenToRemind == comparedElement.amountOfDaysSinceWhenToRemind;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public int getPriority() {
        return priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public int getAmountOfDaysSinceWhenToRemind() {
        return amountOfDaysSinceWhenToRemind;
    }

    public Boolean isFinished() {
        return finished;
    }

    public LocalDate getElementCompletionDate() {
        return elementCompletionDate;
    }

    public void setFinished(boolean finished, LocalDate elementCompletionDate) {
        if (finished) {
            this.elementCompletionDate = elementCompletionDate;
        } else {
            this.elementCompletionDate = null;
        }
        this.finished = finished;
    }

    abstract public void operationsIfCompletionStatusHasChanged();
}
