package com.mplaska.projectmanagementapp.controllers._tasks;

import com.mplaska.projectmanagementapp.controllers.MainController;
import com.mplaska.projectmanagementapp.controllers.table.DeadlineCell;
import com.mplaska.projectmanagementapp.controllers.table.FinishedCell;
import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.coredata.TaskElement;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TasksViewController extends MainController {
    public static final String TASK_MODIFICATION_MODE_FXML = "task-modification-mode.fxml";
    public static final String SUBTASKS_VIEW_FXML_PATH = "subtasks-view.fxml";

    public TasksViewController() {
        super(ManagerFacade.getInstance().getTaskStorage(), TASK_MODIFICATION_MODE_FXML);
    }

    @Override
    public void initialize() {
        ManagerFacade.getInstance().getTaskStorage().addObserver(this);

        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("detailedDescription"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("priority"));
        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("deadline"));
        table.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("assignedProject"));
        table.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("finished"));

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
        ((TableColumn<TaskElement, Boolean>) table.getColumns().get(5)).setCellFactory(column -> new FinishedCell());

        FilteredList<Task> filteredList = new FilteredList<>(FXCollections.observableList(ManagerFacade.getInstance().getTaskStorage().getList()), p -> true);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(task -> {
                // If filter text is empty, display all
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return task.getName().contains(lowerCaseFilter) ||
                        task.getDetailedDescription().contains(lowerCaseFilter) ||
                        Integer.toString(task.getPriority()).contains(lowerCaseFilter) ||
                        task.getDeadline().toString().contains(lowerCaseFilter) ||
                        task.getAssignedProject().getName().contains(lowerCaseFilter) ||
                        task.isFinished().toString().contains(lowerCaseFilter);
            });
        });

        SortedList<TaskElement> sortedData = new SortedList<>(filteredList);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }
    
    @FXML
    protected void openAddTaskMode() throws IOException {
        super.openAddMode("Adding task");
    }

    @FXML
    protected void openEditTaskMode() throws IOException {
        super.openEditMode("Editing task");
    }

    @FXML
    protected void deleteTasks() {
        if (super.isImpossibleToDeleteElements()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Do you want to delete tasks?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            for (int i = selectedRowsOfTable.size() - 1; i >= 0; i--) {
                ManagerFacade.getInstance().deleteTask(selectedRowsOfTable.get(i));
            }
        }
    }

    @FXML
    protected void goToSubtasks() throws IOException {
        if (isIncorrectSelectionOfTableRow(ManagerFacade.getInstance().getTaskStorage().getList())) {
            return;
        }
        ((Stage) taskViewButton.getScene().getWindow()).close();
        openNewWindow(SUBTASKS_VIEW_FXML_PATH, APP_WINDOW_TITLE, true);
    }
}