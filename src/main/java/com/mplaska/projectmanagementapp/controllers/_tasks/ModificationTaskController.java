package com.mplaska.projectmanagementapp.controllers._tasks;

import com.mplaska.projectmanagementapp.controllers._tasks.TasksViewController;
import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import com.mplaska.projectmanagementapp.model.inputcreatingobject.CreatingObjectFromInput;
import com.mplaska.projectmanagementapp.model.inputdata.TaskInputData;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ModificationTaskController {
    private Task selectedTask;
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
    private ComboBox<Project> assignedProject;


    @FXML
    public void initialize() {
        selectedRow = TasksViewController.getSelectedRowsOfTable().get(0);

        selectedTask = null;
        button.setText("Add task");
        if (selectedRow >= 0 && selectedRow < ManagerFacade.getInstance().getTaskStorage().getList().size()) {
            selectedTask = ManagerFacade.getInstance().getTaskStorage().getList().get(selectedRow);
            button.setText("Edit task");
        }

        List<Project> projectList = new ArrayList<>(ManagerFacade.getInstance().getProjectStorage().getList());
        projectList.add(0, ManagerFacade.getInstance().createEmptyProject());
        assignedProject.setItems(FXCollections.observableList(projectList));

        if (selectedTask != null) {
            setValuesInFields();
        }
    }


    @FXML
    protected void buttonClicked() {
        Task newTask = ManagerFacade.getInstance().createTaskIfPossible(new TaskInputData(selectedTask,
                name.getText(), description.getText(),
                Integer.parseInt(((RadioButton) priority.getSelectedToggle()).getText()),
                deadline.getValue(), lengthOfRemindingSpinner.getValue(), assignedProject.getValue()));

        if (newTask == null) {
            return;
        }

        String info = "";
        if (selectedTask == null) {
            ManagerFacade.getInstance().addTask(newTask);
            info = "Successfully added task";
        } else {
            ManagerFacade.getInstance().editTask(selectedRow, newTask);
            info = "Successfully edited task";
        }

        ((Stage) button.getScene().getWindow()).close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(info);

        alert.showAndWait();
    }

    private void setValuesInFields() {
        name.setText(selectedTask.getName());
        description.setText(selectedTask.getDetailedDescription());

        int indexToSelect = selectedTask.getPriority() - 1;

        for (int i = 0; i < priority.getToggles().size(); ++i) {
            priority.getToggles().get(i).setSelected(false);
            if (i == indexToSelect) {
                priority.getToggles().get(i).setSelected(true);
            }
        }

        lengthOfRemindingSpinner.getValueFactory().setValue(selectedTask.getAmountOfDaysSinceWhenToRemind());

        deadline.setValue(selectedTask.getDeadline());

        assignedProject.setValue(selectedTask.getAssignedProject());
    }
}
