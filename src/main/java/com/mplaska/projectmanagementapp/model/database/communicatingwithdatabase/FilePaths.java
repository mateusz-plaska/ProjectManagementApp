package com.mplaska.projectmanagementapp.model.database.communicatingwithdatabase;

public class FilePaths {
    private final static String taskFilePath = "C:\\Users\\plask\\Desktop\\PSiO\\Projekt\\ProjectManagementApp\\TasksDatabase.bin";
    private final static String projectFilePath = "C:\\Users\\plask\\Desktop\\PSiO\\Projekt\\ProjectManagementApp\\ProjectsDatabase.bin";

    public static String getTaskFilePath() {
        return taskFilePath;
    }

    public static String getProjectFilePath() {
        return projectFilePath;
    }
}
