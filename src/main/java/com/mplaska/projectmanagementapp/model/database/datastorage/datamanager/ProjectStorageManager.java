package com.mplaska.projectmanagementapp.model.database.datastorage.datamanager;

import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.database.datastorage.ListStorage;
import com.mplaska.projectmanagementapp.model.inputcreatingobject.CreatingObjectFromInput;
import com.mplaska.projectmanagementapp.model.inputdata.ProjectInputData;
import com.mplaska.projectmanagementapp.model.inputvalidator.InputValidator;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectStorageManager {
    private final ListStorage<Project> projectStorage;
    private final InputValidator inputValidator;

    public ProjectStorageManager() {
        projectStorage = new ListStorage<>();
        inputValidator = new InputValidator();
    }

    public ListStorage<Project> getProjectStorage() {
        return projectStorage;
    }

    public Project createEmptyProject() {
        return new Project("none", "", 0,
                LocalDate.of(0, 1, 2), -5,
                LocalDate.of(0,1,1));
    }

    public Project createProjectIfPossible(ProjectInputData projectInputData) {
        try {
            inputValidator.checkIfProjectInputIsValidate(projectInputData);

            Project newProject = CreatingObjectFromInput.createProject(projectInputData);
            inputValidator.checkIfTaskElementExists(newProject, new ArrayList<>(projectStorage.getList()));

            return newProject;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
            return null;
        }
    }

    public void addProject(Project project) {
        projectStorage.add(project);
    }

    public void editProject(int index, Project newProject, ListStorage<Task> taskStorage) {
        Project editedProject = projectStorage.getList().get(index);

        for (int i = 0; i < taskStorage.getList().size(); ++i) {
            if (taskStorage.getList().get(i).getAssignedProject().isEqual(editedProject)) {
                taskStorage.getList().get(i).setAssignedProject(newProject);
            }
        }

        projectStorage.edit(index, newProject);
    }

    public void deleteProject(int index, ListStorage<Task> taskStorage) {
        Project deletedProject = projectStorage.getList().get(index);

        for (int j = taskStorage.getList().size() - 1; j >= 0; j--) {
            if (taskStorage.getList().get(j).getAssignedProject().isEqual(deletedProject)) {
                taskStorage.remove(j);
            }
        }

        projectStorage.remove(index);
    }
}
