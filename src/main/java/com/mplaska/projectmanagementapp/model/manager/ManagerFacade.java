package com.mplaska.projectmanagementapp.model.manager;


import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.coredata.Subtask;
import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.coredata.TaskElement;
import com.mplaska.projectmanagementapp.model.database.datastorage.ListStorage;
import com.mplaska.projectmanagementapp.model.database.datastorage.datamanager.ProjectStorageManager;
import com.mplaska.projectmanagementapp.model.database.datastorage.datamanager.SubtaskManager;
import com.mplaska.projectmanagementapp.model.database.datastorage.datamanager.TaskStorageManager;
import com.mplaska.projectmanagementapp.model.inputdata.ProjectInputData;
import com.mplaska.projectmanagementapp.model.inputdata.SubtaskInputData;
import com.mplaska.projectmanagementapp.model.inputdata.TaskInputData;
import com.mplaska.projectmanagementapp.model.timer.CurrentTime;

import java.time.LocalDate;
import java.util.ArrayList;

public class ManagerFacade {
    private static ManagerFacade INSTANCE;

    private final TaskStorageManager taskManager;
    private final ProjectStorageManager projectManager;
    private final SubtaskManager subtaskManager;

    private ManagerFacade() {
        taskManager = new TaskStorageManager();
        projectManager = new ProjectStorageManager();
        subtaskManager = new SubtaskManager();
    }

    public static ManagerFacade getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ManagerFacade();
        }
        return INSTANCE;
    }

    public ListStorage<Task> getTaskStorage() {
        return taskManager.getTaskStorage();
    }

    public ListStorage<Project> getProjectStorage() {
        return projectManager.getProjectStorage();
    }


    public Project createEmptyProject() {
        return projectManager.createEmptyProject();
    }
    public Task createTaskIfPossible(TaskInputData taskInputData) {
        return taskManager.createTaskIfPossible(taskInputData);
    }

    public Project createProjectIfPossible(ProjectInputData projectInputData) {
        return projectManager.createProjectIfPossible(projectInputData);
    }

    public Subtask createSubtaskIfPossible(SubtaskInputData subtaskInputData) {
        return subtaskManager.createSubtaskIfPossible(subtaskInputData);
    }

    public void addTask(Task task) {
        taskManager.addTask(task);
    }

    public void editTask(int index, Task newTask) {
        taskManager.editTask(index, newTask);
    }
    public void deleteTask(int index) {
        taskManager.deleteTask(index);
    }

    public void addProject(Project project) {
        projectManager.addProject(project);
    }

    public void editProject(int index, Project newProject) {
        projectManager.editProject(index, newProject, taskManager.getTaskStorage());
    }

    public void deleteProject(int index) {
        projectManager.deleteProject(index, taskManager.getTaskStorage());
    }

    public void addSubtask(Task operatedTask, Subtask subtask) {
        subtaskManager.addSubtask(operatedTask, subtask);
    }

    public void editSubtask(Task operatedTask, int index, Subtask newSubtask) {
        subtaskManager.editSubtask(operatedTask, index, newSubtask);
    }

    public void deleteSubtask(Task operatedTask, int index) {
        subtaskManager.deleteSubtask(operatedTask, index);
    }

    public void evaluateSubtask(Task operatedTask, Subtask subtask, int newGrade) {
        subtaskManager.evaluateSubtask(operatedTask, subtask, newGrade);
    }

    public void updateStateOfElementIfCompletionStatusHasChanged(ArrayList<? extends TaskElement> elementsList) {
        for (TaskElement taskElement : elementsList) {
            taskElement.operationsIfCompletionStatusHasChanged();
        }
    }

    public void changeElementCompletionStatus(TaskElement element) {
        element.setFinished(!element.isFinished(), CurrentTime.getCurrentSystemTime());
    }
}
