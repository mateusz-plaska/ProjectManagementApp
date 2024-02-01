package com.mplaska.projectmanagementapp.controllers.chart;

import com.mplaska.projectmanagementapp.charts.ChartStrategy;
import com.mplaska.projectmanagementapp.controllers._projects.ProjectsViewController;
import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ChartController {
    private static ChartStrategy chartStrategy;

    @FXML
    protected Label projectNameField;
    @FXML
    protected Label completedTasksField;
    @FXML
    protected Label percentValueField;
    @FXML
    protected ProgressBar progressBar;
    @FXML
    protected VBox bottomVBox;
    @FXML
    protected BorderPane pane;

    public static void setChartStrategy(ChartStrategy chartStrategy) {
        ChartController.chartStrategy = chartStrategy;
    }

    @FXML
    public void initialize() {
        createTopPanel();
        pane.setCenter(chartStrategy.generateChart());
        createBottomPanel();
    }

    private void createTopPanel() {
        Project selectedProject = ManagerFacade.getInstance().getProjectStorage().getList()
                .get(ProjectsViewController.getSelectedRowsOfTable().get(0));

        projectNameField.setText(selectedProject.getName());

        completedTasksField.setText(selectedProject.amountOfCompletedTasksAndAllTasks().getKey() + "/" +
                selectedProject.amountOfCompletedTasksAndAllTasks().getValue());

        double progress = 0.0;
        if (selectedProject.amountOfCompletedTasksAndAllTasks().getValue() != 0) {
            progress = (double) selectedProject.amountOfCompletedTasksAndAllTasks().getKey() /
                    selectedProject.amountOfCompletedTasksAndAllTasks().getValue();
        }
        progressBar.setProgress(progress);


        percentValueField.setText(BigDecimal.valueOf(progress * 100).setScale(2, RoundingMode.HALF_UP) + "%");
    }

    private void createBottomPanel() {
        ArrayList<String> info = chartStrategy.getInfo();
        for (String i : info) {
            Label infoLabel = new Label(i);
            infoLabel.setStyle("-fx-font-weight: bold");
            bottomVBox.getChildren().add(infoLabel);
        }
    }
}
