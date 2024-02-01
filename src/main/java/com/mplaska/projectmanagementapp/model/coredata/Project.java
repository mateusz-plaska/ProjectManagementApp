package com.mplaska.projectmanagementapp.model.coredata;

import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import com.mplaska.projectmanagementapp.model.timer.CurrentTime;
import javafx.util.Pair;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Project extends TaskElement implements Serializable {
    @Serial
    private static final long serialVersionUID = -2151319088570527536L;
    private final LocalDate startDate;

    public Project(String name, String detailedDescription, int priority, LocalDate deadline, int amountOfDaysSinceWhenToRemind, LocalDate startDate) {
        super(name, detailedDescription, priority, deadline, amountOfDaysSinceWhenToRemind);
        this.startDate = startDate;
    }

    public Project(String name, String detailedDescription, int priority, LocalDate deadline, int amountOfDaysSinceWhenToRemind,
                   boolean isFinished, LocalDate elementCompletionDate, LocalDate startDate) {
        this(name, detailedDescription, priority, deadline, amountOfDaysSinceWhenToRemind, startDate);
        setFinished(isFinished, elementCompletionDate);
    }

    @Override
    public boolean isEqual(TaskElement comparedProject) {
        return super.isEqual(comparedProject) && startDate.isEqual(((Project)comparedProject).getStartDate());
    }

    @Override
    public void operationsIfCompletionStatusHasChanged() {
        if (!this.isFinished() && this.checkPossibleCompletionOfProject()) {
            this.setFinished(true, CurrentTime.getCurrentSystemTime());
        } else if (this.isFinished() && !this.checkPossibleCompletionOfProject()) {
            this.setFinished(false, null);
        }
    }

    public Pair<Integer, Integer> amountOfCompletedTasksAndAllTasks() {
        int completed = 0;
        int all = 0;
        for (Task task : ManagerFacade.getInstance().getTaskStorage().getList()) {
            if (task.getAssignedProject().isEqual(this)) {
                all++;
                if (task.isFinished()) {
                    completed++;
                }
            }
        }
        return new Pair<>(completed, all);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public boolean checkPossibleCompletionOfProject() {
        for (Task task : ManagerFacade.getInstance().getTaskStorage().getList()) {
            if (task.getAssignedProject().isEqual(this) && !task.isFinished()) {
                return false;
            }
        }
        return true;
    }
}
