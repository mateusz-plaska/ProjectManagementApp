package com.mplaska.projectmanagementapp.model.inputcreatingobject;



import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.coredata.Subtask;
import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.inputdata.ProjectInputData;
import com.mplaska.projectmanagementapp.model.inputdata.SubtaskInputData;
import com.mplaska.projectmanagementapp.model.inputdata.TaskInputData;

import java.time.LocalDate;

public class CreatingObjectFromInput {
    public static Task createTask(TaskInputData taskInputData) {
        if (taskInputData.getSelectedTaskElement() == null) {
            return new Task(taskInputData.getName(),
                    taskInputData.getDescription(),
                    taskInputData.getPriority(),
                    taskInputData.getDeadline(),
                    taskInputData.getLengthOfReminding(),
                    taskInputData.getAssignProject());
        }

        return new Task(taskInputData.getName(),
                taskInputData.getDescription(),
                taskInputData.getPriority(),
                taskInputData.getDeadline(),
                taskInputData.getLengthOfReminding(),
                taskInputData.getSelectedTaskElement().isFinished(),
                taskInputData.getSelectedTaskElement().getElementCompletionDate(),
                taskInputData.getAssignProject(),
                ((Task)taskInputData.getSelectedTaskElement()).getAdditionDate(),
                ((Task)taskInputData.getSelectedTaskElement()).getSubtasksList());
    }

    public static Project createProject(ProjectInputData projectInputData) {
        if (projectInputData.getSelectedTaskElement() == null) {
            return new Project(projectInputData.getName(),
                    projectInputData.getDescription(),
                    projectInputData.getPriority(),
                    projectInputData.getDeadline(),
                    projectInputData.getLengthOfReminding(),
                    projectInputData.getStartDate());
        }

        return new Project(projectInputData.getName(),
                projectInputData.getDescription(),
                projectInputData.getPriority(),
                projectInputData.getDeadline(),
                projectInputData.getLengthOfReminding(),
                projectInputData.getSelectedTaskElement().isFinished(),
                projectInputData.getSelectedTaskElement().getElementCompletionDate(),
                projectInputData.getStartDate());
    }

    public static Subtask createSubtask(SubtaskInputData subtaskInputData) {
        if (subtaskInputData.getSelectedTaskElement() == null) {
            return new Subtask(subtaskInputData.getName(),
                    subtaskInputData.getDescription(),
                    subtaskInputData.getPriority(),
                    subtaskInputData.getDeadline(),
                    subtaskInputData.getLengthOfReminding(),
                    subtaskInputData.getAssignedPerson());
        }

        return new Subtask(subtaskInputData.getName(),
                subtaskInputData.getDescription(),
                subtaskInputData.getPriority(),
                subtaskInputData.getDeadline(),
                subtaskInputData.getLengthOfReminding(),
                subtaskInputData.getAssignedPerson(),
                subtaskInputData.getSelectedTaskElement().isFinished(),
                subtaskInputData.getSelectedTaskElement().getElementCompletionDate());
    }
}
