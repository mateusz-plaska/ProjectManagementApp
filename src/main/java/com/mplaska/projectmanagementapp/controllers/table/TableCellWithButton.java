package com.mplaska.projectmanagementapp.controllers.table;

import com.mplaska.projectmanagementapp.model.coredata.TaskElement;
import com.mplaska.projectmanagementapp.model.database.datastorage.ListStorage;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import com.mplaska.projectmanagementapp.model.timer.CurrentTime;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

import java.util.ArrayList;

public class TableCellWithButton extends TableCell<TaskElement, Void> {
    private final Button button;
    private final String style;

    public TableCellWithButton(ListStorage<? extends TaskElement> listInTable)  {
        this.button = new Button("Done");
        style = this.button.getStyle();
        this.button.setPrefWidth(200);
        this.button.setOnAction(event -> {
            TaskElement taskElement = getTableView().getItems().get(getIndex());

            ManagerFacade.getInstance().changeElementCompletionStatus(taskElement);
            listInTable.notifyObservers();
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            if (getTableView().getItems().get(getIndex()).isFinished()) {
                this.button.setStyle("-fx-background-color: rgb(0,255,0)");
            } else {
                this.button.setStyle(style);
            }
            setGraphic(button);
        }
    }
}

