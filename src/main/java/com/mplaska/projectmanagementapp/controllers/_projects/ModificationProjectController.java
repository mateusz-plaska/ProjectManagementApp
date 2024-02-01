package com.mplaska.projectmanagementapp.controllers._projects;

import com.mplaska.projectmanagementapp.controllers._projects.ProjectsViewController;
import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import com.mplaska.projectmanagementapp.model.inputdata.ProjectInputData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ModificationProjectController {
    private Project selectedProject;
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
    private DatePicker startDate;

    @FXML
    public void initialize() {
        selectedRow = ProjectsViewController.getSelectedRowsOfTable().get(0);

        selectedProject = null;
        button.setText("Add project");
        if (selectedRow >= 0 && selectedRow < ManagerFacade.getInstance().getProjectStorage().getList().size()) {
            selectedProject = ManagerFacade.getInstance().getProjectStorage().getList().get(selectedRow);
            button.setText("Edit project");
        }

        if (selectedProject != null) {
            setValuesInFields();
        }
    }


    @FXML
    protected void buttonClicked() {
        Project newProject = ManagerFacade.getInstance().createProjectIfPossible(new ProjectInputData(
                selectedProject, name.getText(), description.getText(),
                Integer.parseInt(((RadioButton) priority.getSelectedToggle()).getText()),
                deadline.getValue(), lengthOfRemindingSpinner.getValue(), startDate.getValue()));

        if (newProject == null) {
            return;
        }

        String info = "";
        if (selectedProject == null) {
            ManagerFacade.getInstance().addProject(newProject);
            info = "Successfully added project";
        } else {
            ManagerFacade.getInstance().editProject(selectedRow, newProject);
            info = "Successfully edited project";
        }

        ((Stage) button.getScene().getWindow()).close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(info);

        alert.showAndWait();
    }

    private void setValuesInFields() {
        name.setText(selectedProject.getName());
        description.setText(selectedProject.getDetailedDescription());

        int indexToSelect = selectedProject.getPriority() - 1;

        for (int i = 0; i < priority.getToggles().size(); ++i) {
            priority.getToggles().get(i).setSelected(false);
            if (i == indexToSelect) {
                priority.getToggles().get(i).setSelected(true);
            }
        }

        lengthOfRemindingSpinner.getValueFactory().setValue(selectedProject.getAmountOfDaysSinceWhenToRemind());

        deadline.setValue(selectedProject.getDeadline());

        startDate.setValue(selectedProject.getStartDate());
    }
}
