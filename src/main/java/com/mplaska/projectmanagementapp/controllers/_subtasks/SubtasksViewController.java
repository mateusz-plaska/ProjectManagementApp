package com.mplaska.projectmanagementapp.controllers._subtasks;

import com.mplaska.projectmanagementapp.controllers.MainController;
import com.mplaska.projectmanagementapp.controllers.table.DeadlineCell;
import com.mplaska.projectmanagementapp.controllers.table.FinishedCell;
import com.mplaska.projectmanagementapp.controllers.table.TableCellWithButton;
import com.mplaska.projectmanagementapp.model.coredata.Subtask;
import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.coredata.TaskElement;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SubtasksViewController extends MainController {
    public static final String SUBTASK_MODIFICATION_MODE_FXML = "subtask-modification-mode.fxml";
    public static final String SUBTASK_EVALUATION_MODE_FXML = "evaluate-subtask-mode.fxml";

    private static Task selectedTask;

    @FXML
    protected Label taskNameField;
    @FXML
    protected Label assignedProjectNameField;
    @FXML
    protected Label completedSubtasksField;
    @FXML
    protected Label percentValueField;
    @FXML
    protected ProgressBar progressBar;

    public SubtasksViewController() {
        super((selectedTask = ManagerFacade.getInstance().getTaskStorage().getList().get(getSelectedRowsOfTable().get(0))).getSubtasksList(),
                SUBTASK_MODIFICATION_MODE_FXML);
    }

    public static Task getSelectedTask() {
        return selectedTask;
    }

    @Override
    public void initialize() {
        selectedTask.getSubtasksList().addObserver(this);

        createTopPanel();

        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("detailedDescription"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("priority"));
        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("deadline"));
        table.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("assignedPerson"));
        table.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gradeString"));
        table.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("finished"));
        ((TableColumn<TaskElement, Void>) table.getColumns().get(7)).setCellFactory(p -> new TableCellWithButton(selectedTask.getSubtasksList()));

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setProperRowsInTable(newValue);
            }
        });

        loadTable();
        update();
    }

    @Override
    protected void loadTable() {
        ((TableColumn<TaskElement, LocalDate>) table.getColumns().get(3)).setCellFactory(column -> new DeadlineCell());
        ((TableColumn<TaskElement, Boolean>) table.getColumns().get(6)).setCellFactory(column -> new FinishedCell());

        FilteredList<Subtask> filteredList = new FilteredList<>(FXCollections.observableList(selectedTask.getSubtasksList().getList()), s -> true);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(subtask -> {
                // If filter text is empty, display all
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return subtask.getName().contains(lowerCaseFilter) ||
                        subtask.getDetailedDescription().contains(lowerCaseFilter) ||
                        Integer.toString(subtask.getPriority()).contains(lowerCaseFilter) ||
                        subtask.getDeadline().toString().contains(lowerCaseFilter) ||
                        subtask.getAssignedPerson().contains(lowerCaseFilter) ||
                        subtask.getGradeString().contains(lowerCaseFilter) ||
                        subtask.isFinished().toString().contains(lowerCaseFilter);
            });
        });

        SortedList<TaskElement> sortedData = new SortedList<>(filteredList);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }

    @FXML
    protected void openAddSubtaskMode() throws IOException {
        super.openAddMode("Adding subtask");
    }

    @FXML
    protected void openEditSubtaskMode() throws IOException {
        super.openEditMode("Editing subtask");
    }

    @FXML
    protected void deleteSubtasks() {
        if (super.isImpossibleToDeleteElements()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Do you want to delete subtasks?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            for (int i = selectedRowsOfTable.size() - 1; i >= 0; i--) {
                ManagerFacade.getInstance().deleteSubtask(selectedTask, selectedRowsOfTable.get(i));
            }
        }
    }

    @FXML
    protected void evaluateSubtask() throws IOException {
        if (isIncorrectSelectionOfTableRow(selectedTask.getSubtasksList().getList())) {
            return;
        }

        if (!selectedTask.getSubtasksList().getList().get(selectedRowsOfTable.get(0)).isFinished()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Cannot evaluate unfinished subtask");

            alert.showAndWait();
            return;
        }
        openNewWindow(SUBTASK_EVALUATION_MODE_FXML, "Evaluating subtask", false);
    }

    private void createTopPanel() {
        taskNameField.setText(selectedTask.getName());
        assignedProjectNameField.setText(selectedTask.getAssignedProject().getName());

        completedSubtasksField.setText(selectedTask.amountOfCompletedSubtasks() + "/" +
                selectedTask.getSubtasksList().getList().size());

        progressBar.setProgress(selectedTask.getPercentValueOfCompletedSubtasks() / 100.0);

        percentValueField.setText(BigDecimal.valueOf(selectedTask.getPercentValueOfCompletedSubtasks()).
                setScale(2, RoundingMode.HALF_UP) + "%");
    }

    @Override
    public void update() {
        createTopPanel();
        super.update();
    }
}
