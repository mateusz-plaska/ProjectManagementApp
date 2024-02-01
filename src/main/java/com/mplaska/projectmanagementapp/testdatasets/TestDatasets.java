package com.mplaska.projectmanagementapp.testdatasets;


import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.coredata.Subtask;
import com.mplaska.projectmanagementapp.model.coredata.Task;
import com.mplaska.projectmanagementapp.model.manager.ManagerFacade;
import com.mplaska.projectmanagementapp.model.timer.CurrentTime;

/**
 * in ToDoListApp.java
    run tests
 */

public class TestDatasets {
    public static void test() {
        Project projekttest = new Project("projekt 1", "testowy", 3,
                CurrentTime.getCurrentSystemTime().plusMonths(1), 15, CurrentTime.getCurrentSystemTime().minusMonths(2));

        /////////////////////////////////////////////////////////////////

        Task task1 = new Task("name1", "dd1", 3, CurrentTime.getCurrentSystemTime().plusMonths(1),
                5, projekttest);

        Subtask subtask1 = new Subtask("subtask1", "d", 5,
                task1.getDeadline(), 5, "osoba 1", true,
                CurrentTime.getCurrentSystemTime().minusDays(15));

        Subtask subtask2 = new Subtask("subtask2", "d", 3,
                task1.getDeadline(), 5, "osoba 2");

        Subtask subtask3 = new Subtask("subtask3", "d", 4,
                task1.getDeadline(), 5,
                "osoba 3", true, CurrentTime.getCurrentSystemTime().minusDays(25));

        Subtask subtask4 = new Subtask("subtask4", "d", 3,
                task1.getDeadline(), 5,
                "osoba 4");

        task1.setAdditionDate(CurrentTime.getCurrentSystemTime().minusMonths(2));

        task1.getSubtasksList().getList().add(subtask1);
        task1.getSubtasksList().getList().add(subtask2);
        task1.getSubtasksList().getList().add(subtask3);
        task1.getSubtasksList().getList().add(subtask4);

        /////////////////////////////////////////////////////////////////

        Task task2 = new Task("name2", "dd2", 4,
                CurrentTime.getCurrentSystemTime().plusDays(15), 5, projekttest);

        subtask1 = new Subtask("subtask1", "d", 5,
                task2.getDeadline(), 5, "osoba 1", true,
                CurrentTime.getCurrentSystemTime().minusDays(3));

        subtask2 = new Subtask("subtask2", "d", 3,
                task2.getDeadline(), 5, "osoba 2");

        subtask3 = new Subtask("subtask3", "d", 3,
                task2.getDeadline(), 5, "osoba 3");

        subtask4 = new Subtask("subtask4", "d", 4,
                task2.getDeadline(), 5, "osoba 4");

        task2.setAdditionDate(CurrentTime.getCurrentSystemTime().minusMonths(2));

        task2.getSubtasksList().getList().add(subtask1);
        task2.getSubtasksList().getList().add(subtask2);
        task2.getSubtasksList().getList().add(subtask3);
        task2.getSubtasksList().getList().add(subtask4);

        /////////////////////////////////////////////////////////////////

        Task task3 = new Task("name3", "dd3", 4, CurrentTime.getCurrentSystemTime().minusMonths(1), 5, projekttest);;

        subtask1 = new Subtask("subtask1", "d", 5,
                task3.getDeadline(), 5, "osoba 1", true,
                CurrentTime.getCurrentSystemTime().minusDays(45));

        subtask2 = new Subtask("subtask2", "d", 3,
                task3.getDeadline(), 5, "osoba 2", true,
                CurrentTime.getCurrentSystemTime().minusDays(50));

        task3.setFinished(true, CurrentTime.getCurrentSystemTime().minusDays(50));

        task3.setAdditionDate(CurrentTime.getCurrentSystemTime().minusMonths(2));

        task3.getSubtasksList().getList().add(subtask1);
        task3.getSubtasksList().getList().add(subtask2);

        /////////////////////////////////////////////////////////////////

        ManagerFacade.getInstance().addProject(projekttest);
        ManagerFacade.getInstance().addTask(task1);
        ManagerFacade.getInstance().addTask(task2);
        ManagerFacade.getInstance().addTask(task3);
    }

    public static void testIsAfterDeadline() {
        Project projekttest = new Project("projekt 2", "testowy", 3,
                CurrentTime.getCurrentSystemTime().minusDays(5), 15, CurrentTime.getCurrentSystemTime().minusMonths(3));

        /////////////////////////////////////////////////////////////////

        Task task1 = new Task("name1", "dd1", 3, CurrentTime.getCurrentSystemTime().minusMonths(1),
                5, projekttest);

        Subtask subtask1 = new Subtask("subtask1", "d", 5,
                task1.getDeadline(), 5, "osoba 1", true,
                CurrentTime.getCurrentSystemTime().minusDays(35));

        Subtask subtask2 = new Subtask("subtask2", "d", 3,
                task1.getDeadline(), 5, "osoba 2");

        Subtask subtask3 = new Subtask("subtask3", "d", 4,
                task1.getDeadline(), 5,
                "osoba 3", true, CurrentTime.getCurrentSystemTime().minusDays(62));

        Subtask subtask4 = new Subtask("subtask4", "d", 3,
                task1.getDeadline(), 5,
                "osoba 4");

        task1.setAdditionDate(CurrentTime.getCurrentSystemTime().minusMonths(4));

        task1.getSubtasksList().getList().add(subtask1);
        task1.getSubtasksList().getList().add(subtask2);
        task1.getSubtasksList().getList().add(subtask3);
        task1.getSubtasksList().getList().add(subtask4);

        /////////////////////////////////////////////////////////////////

        Task task2 = new Task("name2", "dd2", 4,
                CurrentTime.getCurrentSystemTime().minusDays(15), 5, projekttest);

        subtask1 = new Subtask("subtask1", "d", 5,
                task2.getDeadline(), 5, "osoba 1", true,
                CurrentTime.getCurrentSystemTime().minusDays(20));

        subtask2 = new Subtask("subtask2", "d", 3,
                task2.getDeadline(), 5, "osoba 2", true,
                CurrentTime.getCurrentSystemTime());

        subtask3 = new Subtask("subtask3", "d", 3,
                task2.getDeadline(), 5, "osoba 3", true,
                CurrentTime.getCurrentSystemTime());

        task2.setFinished(true, CurrentTime.getCurrentSystemTime());

        task2.setAdditionDate(CurrentTime.getCurrentSystemTime().minusMonths(4));

        task2.getSubtasksList().getList().add(subtask1);
        task2.getSubtasksList().getList().add(subtask2);
        task2.getSubtasksList().getList().add(subtask3);

        /////////////////////////////////////////////////////////////////

        Task task3 = new Task("name3", "dd3", 4, CurrentTime.getCurrentSystemTime().minusMonths(1), 5, projekttest);;

        subtask1 = new Subtask("subtask1", "d", 5,
                task3.getDeadline(), 5, "osoba 1", true,
                CurrentTime.getCurrentSystemTime().minusDays(45));

        subtask2 = new Subtask("subtask2", "d", 3,
                task3.getDeadline(), 5, "osoba 2", true,
                CurrentTime.getCurrentSystemTime().minusDays(50));

        task3.setFinished(true, CurrentTime.getCurrentSystemTime().minusDays(50));

        task3.setAdditionDate(CurrentTime.getCurrentSystemTime().minusMonths(4));

        task3.getSubtasksList().getList().add(subtask1);
        task3.getSubtasksList().getList().add(subtask2);

        /////////////////////////////////////////////////////////////////

        ManagerFacade.getInstance().addProject(projekttest);
        ManagerFacade.getInstance().addTask(task1);
        ManagerFacade.getInstance().addTask(task2);
        ManagerFacade.getInstance().addTask(task3);
    }

    public static void testIsBeforeStart() {
        Project projekttest = new Project("projekt 3", "testowy", 3,
                CurrentTime.getCurrentSystemTime().plusMonths(3), 15,
                CurrentTime.getCurrentSystemTime().plusDays(5));


        /////////////////////////////////////////////////////////////////

        Task task1 = new Task("name1", "dd1", 3, CurrentTime.getCurrentSystemTime().plusMonths(1),
                5, projekttest);

        Subtask subtask1 = new Subtask("subtask1", "d", 5,
                task1.getDeadline(), 5, "osoba 1", true,
                CurrentTime.getCurrentSystemTime().plusDays(20));

        Subtask subtask2 = new Subtask("subtask2", "d", 3,
                task1.getDeadline(), 5, "osoba 2");

        Subtask subtask3 = new Subtask("subtask3", "d", 4,
                task1.getDeadline(), 5,
                "osoba 3", true, CurrentTime.getCurrentSystemTime().plusDays(15));

        Subtask subtask4 = new Subtask("subtask4", "d", 3,
                task1.getDeadline(), 5,
                "osoba 4");

        task1.setAdditionDate(CurrentTime.getCurrentSystemTime());

        task1.getSubtasksList().getList().add(subtask1);
        task1.getSubtasksList().getList().add(subtask2);
        task1.getSubtasksList().getList().add(subtask3);
        task1.getSubtasksList().getList().add(subtask4);

        /////////////////////////////////////////////////////////////////

        Task task2 = new Task("name2", "dd2", 4,
                CurrentTime.getCurrentSystemTime().plusMonths(2), 5, projekttest);

        subtask1 = new Subtask("subtask1", "d", 5,
                task2.getDeadline(), 5, "osoba 1", true,
                CurrentTime.getCurrentSystemTime().plusDays(55));

        subtask2 = new Subtask("subtask2", "d", 3,
                task2.getDeadline(), 5, "osoba 2");

        subtask3 = new Subtask("subtask3", "d", 3,
                task2.getDeadline(), 5, "osoba 3");

        task2.setAdditionDate(CurrentTime.getCurrentSystemTime());

        task2.getSubtasksList().getList().add(subtask1);
        task2.getSubtasksList().getList().add(subtask2);
        task2.getSubtasksList().getList().add(subtask3);

        /////////////////////////////////////////////////////////////////

        Task task3 = new Task("name3", "dd3", 4,
                CurrentTime.getCurrentSystemTime().plusDays(80), 5, projekttest);

        subtask1 = new Subtask("subtask1", "d", 5,
                task3.getDeadline(), 5, "osoba 1", true,
                CurrentTime.getCurrentSystemTime().plusMonths(2));

        subtask2 = new Subtask("subtask2", "d", 3,
                task3.getDeadline(), 5, "osoba 2", true,
                CurrentTime.getCurrentSystemTime().plusDays(72));

        task3.setFinished(true, CurrentTime.getCurrentSystemTime().minusDays(50));

        task3.setAdditionDate(CurrentTime.getCurrentSystemTime());

        task3.getSubtasksList().getList().add(subtask1);
        task3.getSubtasksList().getList().add(subtask2);

        /////////////////////////////////////////////////////////////////

        ManagerFacade.getInstance().addProject(projekttest);
        ManagerFacade.getInstance().addTask(task1);
        ManagerFacade.getInstance().addTask(task2);
        ManagerFacade.getInstance().addTask(task3);
    }
}