package com.mplaska.projectmanagementapp.coredata;

import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.coredata.Subtask;
import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.database.datastorage.ListStorage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaskIsEqualTest {

    private static Task task;
    private static Task compTask1T, compTask2T, compTask3T, compTask4T, compTask5T, compTask9T;
    private static Task compTask6F, compTask7F, compTask8F, compTask10F, compTask11F, compTask12F;
    @BeforeAll
    public static void setup() {
        Project project = new Project("PR_NAME", "DESC", 4, LocalDate.now().plusDays(21), 10,
         /*changable*/       false, null,
                LocalDate.now().minusDays(5));

        Project otherPrChangePossible = new Project("PR_NAME", "DESC", 4, LocalDate.now().plusDays(21), 10,
                true, LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(5));

        Project otherPrImpossibleChanges = new Project("PR_NAME", "DEC", 4, LocalDate.now().plusDays(21), 10,
                /*changable*/       false, null,
                LocalDate.now().minusDays(5));

        Project otherPrImpossibleChangesV2 = new Project("PR_NAME", "DESC", 4, LocalDate.now().plusDays(21), 10,
                /*changable*/       false, null,
                LocalDate.now().minusDays(2));

        Project otherPrImpossibleChangesV3 = new Project("PR_NAME", "DESC", 2, LocalDate.now().plusDays(21), 10,
                /*changable*/       true, null,
                LocalDate.now().minusDays(5));

        ListStorage<Subtask> list = new ListStorage<>();
        list.add(new Subtask("name", "des",3, LocalDate.now().plusDays(3),
                1, "ja", false, null));
        list.add(new Subtask("ab", "cd", 1, LocalDate.now(), 2,
                "ktos", true, null));

        ListStorage<Subtask> list2 = new ListStorage<>();
        list2.add(new Subtask("name", "des",3, LocalDate.now().plusDays(3),
                1, "ja2", true, null));
        list2.add(new Subtask("ab", "cd", 1, LocalDate.now(), 2,
                "ktos", true, null));

        task = new Task("NAME", "DESCRIPTION", 1, LocalDate.now(), 5,
         /*changable*/       true, LocalDate.now().minusDays(10),
                project,
         /*changable*/       LocalDate.now().minusMonths(1),
                list);


        compTask1T = new Task("NAME", "DESCRIPTION", 1, LocalDate.now(), 5,
                /*changable*/       false, LocalDate.now().minusDays(10),
                project,
                /*changable*/       LocalDate.now().minusMonths(1),
                list);


        compTask2T = new Task("NAME", "DESCRIPTION", 1, LocalDate.now(), 5,
                /*changable*/       true, null,
                project,
                /*changable*/       LocalDate.now().minusMonths(1),
                list);
        compTask3T = new Task("NAME", "DESCRIPTION", 1, LocalDate.now(), 5,
                /*changable*/       false, null,
                project,
                /*changable*/       LocalDate.now().minusMonths(1),
                list);

        compTask4T = new Task("NAME", "DESCRIPTION", 1, LocalDate.now(), 5,
                /*changable*/       true, LocalDate.now().minusDays(10),
                project,
                /*changable*/       LocalDate.now().minusDays(5),
                list);

        compTask5T = new Task("NAME", "DESCRIPTION", 1, LocalDate.now(), 5,
                /*changable*/       true, LocalDate.now().minusDays(10),
                project,
                /*changable*/       null,
                list);


        compTask6F = new Task("NAME", "DESCRPTION", 1, LocalDate.now(), 5,
                /*changable*/       true, LocalDate.now().minusDays(10),
                project,
                /*changable*/       LocalDate.now().minusMonths(1),
                list);

        compTask7F = new Task("NAME", "DESCRIPTION", 1, LocalDate.now(), 2,
                /*changable*/       true, LocalDate.now().minusDays(10),
                project,
                /*changable*/       LocalDate.now().minusMonths(1),
                list);

        compTask8F = new Task("NAME", "DESCRIPTION", 1, LocalDate.now(), 5,
                /*changable*/       true, LocalDate.now().minusDays(10),
                project,
                /*changable*/       LocalDate.now().minusMonths(1),
                list2);

        compTask9T = new Task("NAME", "DESCRIPTION", 1, LocalDate.now(), 5,
                /*changable*/       true, LocalDate.now().minusDays(10),
                otherPrChangePossible,
                /*changable*/       LocalDate.now().minusMonths(1),
                list);

        compTask10F = new Task("NAME", "DESCRIPTION", 1, LocalDate.now(), 5,
                /*changable*/       true, LocalDate.now().minusDays(10),
                otherPrImpossibleChanges,
                /*changable*/       LocalDate.now().minusMonths(1),
                list);

        compTask11F = new Task("NAME", "DESCRIPTION", 1, LocalDate.now(), 5,
                /*changable*/       true, LocalDate.now().minusDays(10),
                otherPrImpossibleChangesV2,
                /*changable*/       LocalDate.now().minusMonths(1),
                list);

        compTask12F = new Task("NAME", "DESCRIPTION", 1, LocalDate.now(), 5,
                /*changable*/       true, LocalDate.now().minusDays(10),
                otherPrImpossibleChangesV3,
                /*changable*/       LocalDate.now().minusMonths(1),
                list);
    }
    @Test
    public void test_comparisonWithOneself() {
        assertTrue(task.isEqual(task));
    }

    @Test
    public void test1_diffFinished() {
        assertTrue(task.isEqual(compTask1T));
    }

    @Test
    public void test2_diffCompletionDateNull() {
        assertTrue(task.isEqual(compTask2T));
    }

    @Test
    public void test3_diffFinishedAndCompletionDateNull() {
        assertTrue(task.isEqual(compTask3T));
    }

    @Test
    public void test4_diffAdditionDate() {
        assertTrue(task.isEqual(compTask4T));
    }

    @Test
    public void test5_diffAdditionDateNull() {
        assertTrue(task.isEqual(compTask5T));
    }

    @Test
    public void test6_diffDescription() {
        assertFalse(task.isEqual(compTask6F));
    }

    @Test
    public void test7_diffDaysToRemind() {
        assertFalse(task.isEqual(compTask7F));
    }

    @Test
    public void test8_diffSubtasksList() {
        assertFalse(task.isEqual(compTask8F));
    }

    @Test
    public void test9_diffInProject_completionDate() {
        assertTrue(task.isEqual(compTask9T));
    }

    @Test
    public void test10_diffInProject_description() {
        assertFalse(task.isEqual(compTask10F));
    }
    @Test
    public void test11_diffInProject_startDate() {
        assertFalse(task.isEqual(compTask11F));
    }
    @Test
    public void test12_diffInProject_priority() {
        assertFalse(task.isEqual(compTask12F));
    }
}
