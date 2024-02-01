package com.mplaska.projectmanagementapp.model.inputvalidator;



import com.mplaska.projectmanagementapp.model.coredata.TaskElement;
import com.mplaska.projectmanagementapp.model.inputcreatingobject.CreatingObjectFromInput;
import com.mplaska.projectmanagementapp.model.inputdata.ProjectInputData;
import com.mplaska.projectmanagementapp.model.inputdata.SubtaskInputData;
import com.mplaska.projectmanagementapp.model.inputdata.TaskElementInputData;
import com.mplaska.projectmanagementapp.model.inputdata.TaskInputData;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;

import java.util.ArrayList;

public class InputValidator {
    public void checkIfTaskElementExists(TaskElement taskElement, ArrayList<TaskElement> storage) throws Exception {
        for (TaskElement comparedElement : storage) {
            if (taskElement.isEqual(comparedElement)) {
                throw new Exception("Such element already exists");
            }
        }
    }

    public void checkIfTaskInputIsValidate(TaskInputData taskInputData) throws Exception {
        checkIfTaskElementInputIsCorrect(taskInputData);

        if (taskInputData.getAssignProject().isEqual(ManagerFacade.getInstance().createEmptyProject())) {
            return;
        }

        if (taskInputData.getDeadline().isAfter(taskInputData.getAssignProject().getDeadline())) {
            throw new Exception("Deadline date is after assigned project deadline date");
        }
    }

    public void checkIfProjectInputIsValidate(ProjectInputData projectInputData) throws Exception {
        checkIfTaskElementInputIsCorrect(projectInputData);

        if (projectInputData.getStartDate() == null) {
            throw new Exception("Field cannot be empty");
        }

        if (!projectInputData.getStartDate().isBefore(projectInputData.getDeadline())) {
            throw new Exception("Start date must be earlier than the deadline date");
        }
    }

    public void checkIfSubtaskInputIsValidate(SubtaskInputData subtaskInputData) throws Exception {
        checkIfTaskElementInputIsCorrect(subtaskInputData);

        if (subtaskInputData.getAssignedPerson().isEmpty()) {
            throw new Exception("Field cannot be empty");
        }
    }

    private void checkIfTaskElementInputIsCorrect(TaskElementInputData taskElementInputData) throws Exception {
        if (taskElementInputData.getName().isEmpty() ||
                taskElementInputData.getDescription().isEmpty() ||
                taskElementInputData.getDeadline() == null ||
                taskElementInputData.getLengthOfReminding() == null) {

            throw new Exception("Field cannot be empty");
        }
    }
}
