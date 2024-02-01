package com.mplaska.projectmanagementapp.model.database.communicatingwithdatabase;

import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.database.datastorage.ListStorage;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public abstract class LoadListData {

    public static void load() {
        try (ObjectInputStream taskFileReader = new ObjectInputStream(new FileInputStream(FilePaths.getTaskFilePath()));
             ObjectInputStream projectFileReader = new ObjectInputStream(new FileInputStream(FilePaths.getProjectFilePath()))) {
            ManagerFacade.getInstance().getTaskStorage().setList(((ListStorage<Task>) taskFileReader.readObject()).getList());
            ManagerFacade.getInstance().getProjectStorage().setList(((ListStorage<Project>) projectFileReader.readObject()).getList());
        } catch (IOException | ClassNotFoundException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Incorrect connection with database - data were not read correctly and could be inconsistent");

            alert.showAndWait();
        }
    }
}
