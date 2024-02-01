package com.mplaska.projectmanagementapp.controllers.table;

import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.coredata.TaskElement;
import com.mplaska.projectmanagementapp.model.timer.CurrentTime;
import javafx.scene.control.TableCell;

import java.time.LocalDate;

public class FinishedCell extends TableCell<TaskElement, Boolean> {

    @Override
    protected void updateItem(Boolean finished, boolean empty) {
        super.updateItem(finished, empty);

        if (finished == null || empty) {
            setText(null);
            setStyle("");
        } else {
            if (finished) {
                setStyle("-fx-background-color: rgb(0,255,0)");
            } else {
                setStyle(getTableView().getStyle());
            }
            setText(finished.toString());
        }
    }
}
