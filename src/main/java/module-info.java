module com.mplaska.projectmanagementapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mplaska.projectmanagementapp to javafx.fxml;
    opens com.mplaska.projectmanagementapp.model.coredata to javafx.base;
    exports com.mplaska.projectmanagementapp;
    exports com.mplaska.projectmanagementapp.controllers;
    opens com.mplaska.projectmanagementapp.controllers to javafx.fxml;
    exports com.mplaska.projectmanagementapp.model.coredata;
    exports com.mplaska.projectmanagementapp.model.database.datastorage;
    exports com.mplaska.projectmanagementapp.model.observerprincipleinterface;
    exports com.mplaska.projectmanagementapp.charts;
    exports com.mplaska.projectmanagementapp.controllers.table;
    opens com.mplaska.projectmanagementapp.controllers.table to javafx.fxml;
    exports com.mplaska.projectmanagementapp.controllers.chart;
    opens com.mplaska.projectmanagementapp.controllers.chart to javafx.fxml;
    exports com.mplaska.projectmanagementapp.controllers._subtasks;
    opens com.mplaska.projectmanagementapp.controllers._subtasks to javafx.fxml;
    exports com.mplaska.projectmanagementapp.controllers._tasks;
    opens com.mplaska.projectmanagementapp.controllers._tasks to javafx.fxml;
    exports com.mplaska.projectmanagementapp.controllers._projects;
    opens com.mplaska.projectmanagementapp.controllers._projects to javafx.fxml;
}