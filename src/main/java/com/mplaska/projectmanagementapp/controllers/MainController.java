package com.mplaska.projectmanagementapp.controllers;

import com.mplaska.projectmanagementapp.StartApplication;
import com.mplaska.projectmanagementapp.model.coredata.TaskElement;
import com.mplaska.projectmanagementapp.model.database.datastorage.ListStorage;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import com.mplaska.projectmanagementapp.model.observerprincipleinterface.Observer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class MainController implements Observer {
    public final static String APP_WINDOW_TITLE = "Projects management app";
    public final static String PROJECTS_VIEW_FXML_PATH = "projects-view.fxml";
    public final static String TASKS_VIEW_FXML_PATH = "tasks-view.fxml";

    private final String MODIFICATION_MODE_FXML;

    protected static List<Integer> selectedRowsOfTable = new ArrayList<>(List.of(-1));
    private final ListStorage<? extends TaskElement> listDisplayingInTable;

    @FXML
    protected TableView<TaskElement> table;
    @FXML
    protected TextField searchBox;
    @FXML
    protected Button taskViewButton;

    protected MainController(ListStorage<? extends TaskElement> listDisplayingInTable, String modificationModeFXMLPath) {
        this.listDisplayingInTable = listDisplayingInTable;
        this.MODIFICATION_MODE_FXML = modificationModeFXMLPath;
    }

    @FXML
    protected void goToTasks() throws IOException {
        ((Stage) taskViewButton.getScene().getWindow()).close();
        openNewWindow(TASKS_VIEW_FXML_PATH, APP_WINDOW_TITLE, true);
    }

    @FXML
    protected void goToProjects() throws IOException {
        ((Stage) taskViewButton.getScene().getWindow()).close();
        openNewWindow(PROJECTS_VIEW_FXML_PATH, APP_WINDOW_TITLE, true);
    }

    public static List<Integer> getSelectedRowsOfTable() {
        return selectedRowsOfTable;
    }

    protected void setProperRowsInTable(Object newValue) {
        if (table.getSelectionModel().getSelectedIndices().size() == 1 ||
                (selectedRowsOfTable.size() == 1 && selectedRowsOfTable.get(0) == -1)) {
            selectedRowsOfTable = new ArrayList<>();
        }
        int originalRow = listDisplayingInTable.getList().indexOf(newValue);
        selectedRowsOfTable.add(originalRow);
    }

    public static void openNewWindow(String pathToViewFile, String windowTitle, boolean isWindowCloseApp) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(pathToViewFile));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(windowTitle);
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image(StartApplication.class.getResourceAsStream("/icon.png")));

        if (isWindowCloseApp) {
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                }
            });
        }
    }

    protected boolean isIncorrectSelectionOfTableRow(ArrayList<? extends TaskElement> listFromTable) {
        return selectedRowsOfTable.size() != 1 || selectedRowsOfTable.get(0) < 0
                || selectedRowsOfTable.get(0) >= listFromTable.size();
    }

    @Override
    public void update() {
        ManagerFacade.getInstance().updateStateOfElementIfCompletionStatusHasChanged(listDisplayingInTable.getList());
        loadTable();
        table.refresh();
    }

    protected void openAddMode(String windowTitle) throws IOException{
        selectedRowsOfTable = new ArrayList<>(List.of(-1));
        openNewWindow(MODIFICATION_MODE_FXML, windowTitle, false);
    }

    protected void openEditMode(String windowTitle) throws IOException {
        if (isIncorrectSelectionOfTableRow(listDisplayingInTable.getList())) {
            return;
        }
        openNewWindow(MODIFICATION_MODE_FXML, windowTitle, false);
    }

    protected boolean isImpossibleToDeleteElements() {
        if (selectedRowsOfTable.isEmpty()) {
            return true;
        }
        for (int row : selectedRowsOfTable) {
            if (row < 0 || row >= listDisplayingInTable.getList().size()) {
                return true;
            }
        }
        return false;
    }

    @FXML
    abstract public void initialize();
    abstract protected void loadTable();
}
