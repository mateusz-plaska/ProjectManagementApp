package com.mplaska.projectmanagementapp.coredata;

import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.coredata.Subtask;
import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.database.datastorage.ListStorage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskAmountOfCompletedSubtasksTest {
    private static Task task1;
    private static Task task2;

    @BeforeAll
    public static void setup() {
        Project project = new Project("PR_NAME", "DESC", 4,
                LocalDate.now().plusDays(21), 10, false,
                null, LocalDate.now().minusDays(5));

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

        task1 = new Task("NAME", "DESCRIPTION", 1, LocalDate.now(),
                5, true, LocalDate.now().minusDays(10),
                project, LocalDate.now().minusMonths(1), list);
        task2 = new Task("NAME", "DESCRIPTION", 1, LocalDate.now(),
                5,true, LocalDate.now().minusDays(10),
                project, LocalDate.now().minusMonths(1), list2);
    }

    @Test
    public void test1() {
        assertEquals(1, task1.amountOfCompletedSubtasks());
    }

    @Test
    public void test2() {
        assertEquals(2, task2.amountOfCompletedSubtasks());
    }
}
