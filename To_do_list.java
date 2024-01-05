import java.util.ArrayList;
import java.util.Scanner;

class Task {
    String descrip;
    boolean completed;

    Task(String description) {
        this.descrip = description;
        this.completed = false;
    }
}

public class To_do_list {
    public static ArrayList<Task> tasks = new ArrayList<>();
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    markTaskAsCompleted();
                    break;
                case 3:
                    displayTasks();
                    break;
                case 4:
                    System.out.println("Exiting the To-Do List Manager. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }

    public static void displayMenu() {
        System.out.println("\nTo-Do List Manager Menu:");
        System.out.println("1. Add Task");
        System.out.println("2. Mark Task as Completed");
        System.out.println("3. Display Tasks");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    public static void addTask() {
        System.out.print("Enter task description: ");
        String description = sc.nextLine();
        Task newTask = new Task(description);
        tasks.add(newTask);
        System.out.println("Task added: " + description);
    }

    public static void markTaskAsCompleted() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to mark as completed.");
            return;
        }

        System.out.println("Tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println((i + 1) + ". " + task.descrip + " - " + (task.completed ? "Completed" : "Pending"));
        }

        System.out.print("Enter the task number to mark as completed: ");
        int taskNumber = sc.nextInt();
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task selectedTask = tasks.get(taskNumber - 1);
            selectedTask.completed = true;
            System.out.println("Task marked as completed: " + selectedTask.descrip);
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public static void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to display.");
            return;
        }

        System.out.println("Tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println((i + 1) + ". " + task.descrip + " - " + (task.completed ? "Completed" : "Pending"));
        }
    }
}
