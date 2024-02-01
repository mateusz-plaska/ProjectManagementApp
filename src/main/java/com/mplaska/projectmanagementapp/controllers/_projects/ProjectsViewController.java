package com.mplaska.projectmanagementapp.controllers._projects;

import com.mplaska.projectmanagementapp.charts.BurndownChartStrategy;
import com.mplaska.projectmanagementapp.charts.ChartStrategy;
import com.mplaska.projectmanagementapp.charts.ProgressWorkChartStrategy;
import com.mplaska.projectmanagementapp.controllers.MainController;
import com.mplaska.projectmanagementapp.controllers.chart.ChartController;
import com.mplaska.projectmanagementapp.controllers.table.DeadlineCell;
import com.mplaska.projectmanagementapp.controllers.table.FinishedCell;
import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.coredata.TaskElement;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProjectsViewController extends MainController {
    public static final String PROJECT_MODIFICATION_MODE_FXML = "project-modification-mode.fxml";

    private final Map<String, ChartStrategy> chartStrategies = new HashMap<>();
    private ChartStrategy selectedChartStrategy;

    @FXML
    protected ComboBox<String> chartOptionsBox;

    public ProjectsViewController() {
        super(ManagerFacade.getInstance().getProjectStorage(), PROJECT_MODIFICATION_MODE_FXML);
    }

    @Override
    public void initialize() {
        ManagerFacade.getInstance().getProjectStorage().addObserver(this);

        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("detailedDescription"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("priority"));
        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("startDate"));
        table.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("deadline"));
        table.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("finished"));

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setProperRowsInTable(newValue);
            }
        });

        loadTable();
        update();

        chartStrategies.put("Progress work chart", new ProgressWorkChartStrategy());
        chartStrategies.put("Burndown chart", new BurndownChartStrategy());

        chartOptionsBox.setItems(FXCollections.observableArrayList("Progress work chart", "Burndown chart"));
        chartOptionsBox.setOnAction(actionEvent -> {
            selectedChartStrategy = chartStrategies.get(chartOptionsBox.getValue());
        });
    }

    @Override
    protected void loadTable() {
        ((TableColumn<TaskElement, LocalDate>) table.getColumns().get(4)).setCellFactory(column -> new DeadlineCell());
        ((TableColumn<TaskElement, Boolean>) table.getColumns().get(5)).setCellFactory(column -> new FinishedCell());

        FilteredList<Project> filteredList = new FilteredList<>(FXCollections.observableList(ManagerFacade.getInstance().getProjectStorage().getList()), p -> true);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(project -> {
                // If filter text is empty, display all
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return project.getName().contains(lowerCaseFilter) ||
                        project.getDetailedDescription().contains(lowerCaseFilter) ||
                        Integer.toString(project.getPriority()).contains(lowerCaseFilter) ||
                        project.getStartDate().toString().contains(lowerCaseFilter) ||
                        project.getDeadline().toString().contains(lowerCaseFilter) ||
                        project.isFinished().toString().contains(lowerCaseFilter);
            });
        });

        SortedList<TaskElement> sortedData = new SortedList<>(filteredList);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }

    @FXML
    protected void openAddProjectMode() throws IOException {
        super.openAddMode("Adding project");
    }

    @FXML
    protected void openEditProjectMode() throws IOException {
        super.openEditMode("Editing project");
    }

    @FXML
    protected void deleteProjects() {
        if (isImpossibleToDeleteElements()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Do you want to delete projects?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            for (int i = selectedRowsOfTable.size() - 1; i >= 0; i--) {
                ManagerFacade.getInstance().deleteProject(selectedRowsOfTable.get(i));
            }
        }
    }

    @FXML
    protected void showChart() throws IOException {
        if (isIncorrectSelectionOfTableRow(ManagerFacade.getInstance().getProjectStorage().getList())) {
            return;
        }

        if (selectedChartStrategy != null) {
            selectedChartStrategy.setOperatedProject(ManagerFacade.getInstance().getProjectStorage().getList().get(getSelectedRowsOfTable().get(0)));
            ChartController.setChartStrategy(selectedChartStrategy);
            openNewWindow("chart.fxml", "Chart", false);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Chart option is not selected");

            alert.showAndWait();
        }
    }
}
