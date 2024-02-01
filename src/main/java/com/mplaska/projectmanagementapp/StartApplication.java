package com.mplaska.projectmanagementapp;

import com.mplaska.projectmanagementapp.controllers.MainController;
import com.mplaska.projectmanagementapp.model.database.communicatingwithdatabase.LoadListData;
import com.mplaska.projectmanagementapp.model.database.communicatingwithdatabase.SaveListData;
import com.mplaska.projectmanagementapp.model.timer.CurrentTime;
import com.mplaska.projectmanagementapp.testdatasets.TestDatasets;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {
    private static final CurrentTime appTimer = new CurrentTime();

    public static void run() {
        appTimer.startTimer();
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        LoadListData.load();
        MainController.openNewWindow(MainController.TASKS_VIEW_FXML_PATH, MainController.APP_WINDOW_TITLE, true);
    }

    @Override
    public void stop() {
        SaveListData.save();
        System.exit(0);
    }

    public static void main(String[] args) {
        TestDatasets.test();
        TestDatasets.testIsBeforeStart();
        TestDatasets.testIsAfterDeadline();
        StartApplication.run();
    }
}