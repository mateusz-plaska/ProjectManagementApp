package com.mplaska.projectmanagementapp.model.database.datastorage.datamanager;


import com.mplaska.projectmanagementapp.model.coredata.Subtask;
import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.inputcreatingobject.CreatingObjectFromInput;
import com.mplaska.projectmanagementapp.model.inputdata.SubtaskInputData;
import com.mplaska.projectmanagementapp.model.inputvalidator.InputValidator;
import javafx.scene.control.Alert;

import java.util.ArrayList;

public class SubtaskManager {
    private final InputValidator inputValidator;

    public SubtaskManager() {
        inputValidator = new InputValidator();
    }

    public Subtask createSubtaskIfPossible(SubtaskInputData subtaskInputData) {
        try {
            inputValidator.checkIfSubtaskInputIsValidate(subtaskInputData);

            Subtask newSubtask = CreatingObjectFromInput.createSubtask(subtaskInputData);
            inputValidator.checkIfTaskElementExists(newSubtask, new ArrayList<>(subtaskInputData.getOperatedTask().getSubtasksList().getList()));

            return newSubtask;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
            return null;
        }
    }

    public void addSubtask(Task operatedTask, Subtask subtask) {
        operatedTask.getSubtasksList().add(subtask);
    }

    public void editSubtask(Task operatedTask, int index, Subtask newSubtask) {
        operatedTask.getSubtasksList().edit(index, newSubtask);
    }

    public void deleteSubtask(Task operatedTask, int index) {
        operatedTask.getSubtasksList().remove(index);
    }

    public void evaluateSubtask(Task operatedTask, Subtask subtask, int newGrade) {
        subtask.setGrade(newGrade);
        operatedTask.getSubtasksList().notifyObservers();
    }
}
