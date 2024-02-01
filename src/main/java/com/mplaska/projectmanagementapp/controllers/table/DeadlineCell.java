package com.mplaska.projectmanagementapp.controllers.table;

import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.coredata.TaskElement;
import com.mplaska.projectmanagementapp.model.timer.CurrentTime;
import javafx.scene.control.TableCell;

import java.time.LocalDate;

public class DeadlineCell extends TableCell<TaskElement, LocalDate> {

    @Override
    protected void updateItem(LocalDate deadline, boolean empty) {
        super.updateItem(deadline, empty);

        if (deadline == null || empty) {
            setText(null);
            setStyle("");
        } else {
            int daysSinceWhenToRemind = getTableView().getItems().get(getIndex()).getAmountOfDaysSinceWhenToRemind();
            LocalDate currentSystemTime = CurrentTime.getCurrentSystemTime();

            if (currentSystemTime.isAfter(deadline)) {
                setStyle("-fx-background-color: rgb(128,128,128);");
            } else if (currentSystemTime.isAfter(deadline.minusDays(daysSinceWhenToRemind))) {
                setStyle("-fx-background-color: rgb(255,0,0);");
            } else {
                setStyle(getTableView().getStyle());
            }

            setText(deadline.toString());
        }
    }
}
