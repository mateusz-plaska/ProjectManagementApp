package com.mplaska.projectmanagementapp.inputvalidator;

import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.inputdata.ProjectInputData;
import com.mplaska.projectmanagementapp.model.inputdata.SubtaskInputData;
import com.mplaska.projectmanagementapp.model.inputdata.TaskInputData;
import com.mplaska.projectmanagementapp.model.inputvalidator.InputValidator;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputValidatorTest {

    private static InputValidator inputValidator;
    private static TaskInputData taskInputDataCorrect, taskInputNoName, taskInputNullData, taskInputNullReminding,
            taskInputCorrectWithEmptyProject, taskInputDeadlineAfterAssignedProjectDeadline;
    private static ProjectInputData projectInputDataCorrect, projectInputNoDescription, projectInputNullStartDate,
            projectInputStartDateEqualsDeadline, projectInputStartDateAfterDeadline;
    private static SubtaskInputData subtaskInputDataCorrect, subtaskInputNullPerson;

    @BeforeAll
    public static void setup() {
        inputValidator = new InputValidator();

        Project project = new Project("PR_NAME", "DESC", 4, LocalDate.now().plusDays(21), 10,
                /*changeable*/       false, null,
                LocalDate.now().minusDays(5));


        taskInputDataCorrect = new TaskInputData(null, "n", "d",
                3, LocalDate.now(), 5, project);

        taskInputCorrectWithEmptyProject = new TaskInputData(null, "n", "d",
                3, LocalDate.now(), 5, ManagerFacade.getInstance().createEmptyProject());
        taskInputNoName = new TaskInputData(null, "", "d",
                3, LocalDate.now(), 5, project);
        taskInputNullData = new TaskInputData(null, "n", "d",
                3, null, 5, project);
        taskInputNullReminding = new TaskInputData(null, "n", "d",
                3, LocalDate.now(), null, project);
        taskInputDeadlineAfterAssignedProjectDeadline = new TaskInputData(null,
                "n", "d", 3, project.getDeadline().plusDays(1),
                null, project);

        projectInputDataCorrect = new ProjectInputData(null, "n",
                "d", 4, LocalDate.now(), 10, LocalDate.now().minusMonths(1));

        projectInputNoDescription = new ProjectInputData(null, "n",
                "", 4, LocalDate.now(), 10, LocalDate.now().minusMonths(1));
        projectInputNullStartDate = new ProjectInputData(null, "n",
                "d", 4, LocalDate.now(), 10, null);
        projectInputStartDateEqualsDeadline = new ProjectInputData(null, "n",
                "d", 4, LocalDate.now(), 10, LocalDate.now());
        projectInputStartDateAfterDeadline = new ProjectInputData(null, "n",
                "d", 4, LocalDate.now(), 10, LocalDate.now().plusMonths(1));

        subtaskInputDataCorrect = new SubtaskInputData(null, null,
                "n", "d", 1, LocalDate.now(), 2, "sb");

        subtaskInputNullPerson = new SubtaskInputData(null, null,
                "n", "d", 1, LocalDate.now(), 2, "");
    }

    @Test
    public void testTaskCorrect() {
        assertDoesNotThrow(() -> inputValidator.checkIfTaskInputIsValidate(taskInputDataCorrect));
    }

    @Test
    public void testTaskEmptyProject() {
        assertDoesNotThrow(() -> inputValidator.checkIfTaskInputIsValidate(taskInputCorrectWithEmptyProject));
    }

    @Test
    public void testTaskNoName() {
        assertThrows(Exception.class, () -> inputValidator.checkIfTaskInputIsValidate(taskInputNoName));
    }

    @Test
    public void testTaskNullData() {
        assertThrows(Exception.class, () -> inputValidator.checkIfTaskInputIsValidate(taskInputNullData));
    }

    @Test
    public void testTaskNullReminding() {
        assertThrows(Exception.class, () -> inputValidator.checkIfTaskInputIsValidate(taskInputNullReminding));
    }

    @Test
    public void testTaskDeadlineAfterProjectsDeadline() {
        assertThrows(Exception.class, () -> inputValidator.checkIfTaskInputIsValidate(taskInputDeadlineAfterAssignedProjectDeadline));
    }

    @Test
    public void testProjectCorrect() {
        assertDoesNotThrow(() -> inputValidator.checkIfProjectInputIsValidate(projectInputDataCorrect));
    }

    @Test
    public void testProjectNoDescription() {
        assertThrows(Exception.class, () -> inputValidator.checkIfProjectInputIsValidate(projectInputNoDescription));
    }

    @Test
    public void testProjectNullStartDate() {
        assertThrows(Exception.class, () -> inputValidator.checkIfProjectInputIsValidate(projectInputNullStartDate));
    }

    @Test
    public void testProjectStartDateEqualsDeadline() {
        assertThrows(Exception.class, () -> inputValidator.checkIfProjectInputIsValidate(projectInputStartDateEqualsDeadline));
    }

    @Test
    public void testProjectStartDateAfterDeadline() {
        assertThrows(Exception.class, () -> inputValidator.checkIfProjectInputIsValidate(projectInputStartDateAfterDeadline));
    }

    @Test
    public void testSubtaskCorrect() {
        assertDoesNotThrow(() -> inputValidator.checkIfSubtaskInputIsValidate(subtaskInputDataCorrect));
    }

    @Test
    public void testSubtaskNullPerson() {
        assertThrows(Exception.class, () -> inputValidator.checkIfSubtaskInputIsValidate(subtaskInputNullPerson));
    }
}
