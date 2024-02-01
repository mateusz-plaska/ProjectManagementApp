package com.mplaska.projectmanagementapp.controllers._subtasks;

import com.mplaska.projectmanagementapp.model.coredata.Subtask;
import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import com.mplaska.projectmanagementapp.model.inputdata.SubtaskInputData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ModificationSubtaskController {
    private Task operatedTask;
    private Subtask selectedSubtask;
    private int selectedRow;

    @FXML
    private Button button;
    @FXML
    private TextField name;
    @FXML
    private TextArea description;
    @FXML
    private ToggleGroup priority;
    @FXML
    private Spinner<Integer> lengthOfRemindingSpinner;
    @FXML
    private DatePicker deadline;
    @FXML
    private TextField assignedPerson;

    @FXML
    public void initialize() {
        operatedTask = SubtasksViewController.getSelectedTask();
        selectedRow = SubtasksViewController.getSelectedRowsOfTable().get(0);

        selectedSubtask = null;
        button.setText("Add subtask");
        if (selectedRow >= 0 && selectedRow < operatedTask.getSubtasksList().getList().size()) {
            selectedSubtask = operatedTask.getSubtasksList().getList().get(selectedRow);
            button.setText("Edit subtask");
        }

        deadline.setValue(operatedTask.getDeadline());
        if (selectedSubtask != null) {
            setValuesInFields();
        }
    }


    @FXML
    protected void buttonClicked() {
        Subtask newSubtask = ManagerFacade.getInstance().createSubtaskIfPossible(new SubtaskInputData(
                selectedSubtask, operatedTask, name.getText(), description.getText(),
                Integer.parseInt(((RadioButton) priority.getSelectedToggle()).getText()),
                deadline.getValue(), lengthOfRemindingSpinner.getValue(), assignedPerson.getText()));

        if (newSubtask == null) {
            return;
        }

        String info = "";
        if (selectedSubtask == null) {
            ManagerFacade.getInstance().addSubtask(operatedTask, newSubtask);
            info = "Successfully added subtask";
        } else {
            ManagerFacade.getInstance().editSubtask(operatedTask, selectedRow, newSubtask);
            info = "Successfully edited subtask";
        }

        ((Stage) button.getScene().getWindow()).close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(info);

        alert.showAndWait();
    }

    private void setValuesInFields() {
        name.setText(selectedSubtask.getName());
        description.setText(selectedSubtask.getDetailedDescription());

        int indexToSelect = selectedSubtask.getPriority() - 1;

        for (int i = 0; i < priority.getToggles().size(); ++i) {
            priority.getToggles().get(i).setSelected(false);
            if (i == indexToSelect) {
                priority.getToggles().get(i).setSelected(true);
            }
        }

        lengthOfRemindingSpinner.getValueFactory().setValue(selectedSubtask.getAmountOfDaysSinceWhenToRemind());

        assignedPerson.setText(selectedSubtask.getAssignedPerson());
    }
}
