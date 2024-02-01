package com.mplaska.projectmanagementapp.controllers._subtasks;

import com.mplaska.projectmanagementapp.controllers._subtasks.SubtasksViewController;
import com.mplaska.projectmanagementapp.model.coredata.Subtask;
import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class EvaluateSubtaskController {
    private Task operatedTask;
    private Subtask selectedSubtask;

    @FXML
    protected Button button;
    @FXML
    protected ToggleGroup gradeButtons;

    @FXML
    public void initialize() {
        operatedTask = SubtasksViewController.getSelectedTask();
        selectedSubtask = operatedTask.getSubtasksList().getList().get(SubtasksViewController.getSelectedRowsOfTable().get(0));
        button.setText("Evaluate subtask");
    }

    @FXML
    protected void buttonClicked() {
        String grade = ((RadioButton) gradeButtons.getSelectedToggle()).getText();
        if (grade.equals("-")) {
            ManagerFacade.getInstance().evaluateSubtask(operatedTask, selectedSubtask, -1);
        } else {
            ManagerFacade.getInstance().evaluateSubtask(operatedTask, selectedSubtask, Integer.parseInt(grade));
        }
        operatedTask.getSubtasksList().notifyObservers();

        ((Stage) button.getScene().getWindow()).close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("Successfully evaluated subtask");

        alert.showAndWait();
    }
}
