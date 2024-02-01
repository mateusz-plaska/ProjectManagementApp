package com.mplaska.projectmanagementapp.model.database.datastorage.datamanager;


import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.database.datastorage.ListStorage;
import com.mplaska.projectmanagementapp.model.inputcreatingobject.CreatingObjectFromInput;
import com.mplaska.projectmanagementapp.model.inputdata.TaskInputData;
import com.mplaska.projectmanagementapp.model.inputvalidator.InputValidator;
import javafx.scene.control.Alert;

import java.util.ArrayList;

public class TaskStorageManager {
    private final ListStorage<Task> taskStorage;
    private final InputValidator inputValidator;
    public TaskStorageManager() {
        taskStorage = new ListStorage<>();
        inputValidator = new InputValidator();
    }

    public ListStorage<Task> getTaskStorage() {
        return taskStorage;
    }

    public Task createTaskIfPossible(TaskInputData taskInputData) {
        try {
            inputValidator.checkIfTaskInputIsValidate(taskInputData);

            Task newTask = CreatingObjectFromInput.createTask(taskInputData);
            inputValidator.checkIfTaskElementExists(newTask, new ArrayList<>(taskStorage.getList()));

            return newTask;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
            return null;
        }
    }

    public void addTask(Task task) {
        taskStorage.add(task);
    }

    public void editTask(int index, Task newTask) {
        taskStorage.edit(index, newTask);
    }

    public void deleteTask(int index) {
        taskStorage.remove(index);
    }
}
