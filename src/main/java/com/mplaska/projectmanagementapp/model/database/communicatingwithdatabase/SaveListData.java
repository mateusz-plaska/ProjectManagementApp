package com.mplaska.projectmanagementapp.model.database.communicatingwithdatabase;


import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import javafx.scene.control.Alert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class SaveListData {

    public static void save() {
        try (ObjectOutputStream taskFileWriter = new ObjectOutputStream(new FileOutputStream(FilePaths.getTaskFilePath()));
             ObjectOutputStream projectFileWriter = new ObjectOutputStream(new FileOutputStream(FilePaths.getProjectFilePath()))) {
            taskFileWriter.writeObject(ManagerFacade.getInstance().getTaskStorage());
            projectFileWriter.writeObject(ManagerFacade.getInstance().getProjectStorage());
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Incorrect connection with database - changes has not been saved");

            alert.showAndWait();
        }
    }
}
