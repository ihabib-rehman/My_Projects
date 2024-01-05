import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class User {
    String username;
    String password;
    UserRole role;

    User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

enum UserRole {
    STUDENT,
    TEACHER
}

class Exam {
    String title;
    int duration;
    List<Question> questions;

    Exam(String title, int duration) {
        this.title = title;
        this.duration = duration;
        this.questions = new ArrayList<>();
    }

    void addQuestion(Question question) {
        questions.add(question);
    }
}

abstract class Question {
    String text;

    Question(String text) {
        this.text = text;
    }

    abstract boolean isCorrect(String response);
}

class MultipleChoiceQuestion extends Question {
    List<String> options;
    int correctOption;

    MultipleChoiceQuestion(String text, List<String> options, int correctOption) {
        super(text);
        this.options = options;
        this.correctOption = correctOption;
    }

    @Override
    boolean isCorrect(String response) {
        int selectedOption = Integer.parseInt(response);
        return selectedOption == correctOption;
    }
}

class ShortAnswerQuestion extends Question {
    String correctAnswer;

    ShortAnswerQuestion(String text, String correctAnswer) {
        super(text);
        this.correctAnswer = correctAnswer;
    }

    @Override
    boolean isCorrect(String response) {
        return response.equalsIgnoreCase(correctAnswer);
    }
}

public class Exam_Platform {
    private static Map<String, User> users = new HashMap<>();
    private static List<Exam> exams = new ArrayList<>();
    private static User currentUser;

    public static void main(String[] args) {
        // Sample data for testing
        users.put("teacher1", new User("teacher1", "teacherpass", UserRole.TEACHER));
        users.put("student1", new User("student1", "studentpass", UserRole.STUDENT));

        exams.add(new Exam("Java Basics Exam", 60));
        exams.get(0).addQuestion(new MultipleChoiceQuestion("What is Java?", List.of("A programming language", "A type of coffee", "An island in Indonesia"), 1));
        exams.get(0).addQuestion(new ShortAnswerQuestion("What does JVM stand for?", "Java Virtual Machine"));

        Scanner scanner = new Scanner(System.in);

        // User Authentication
        currentUser = authenticateUser(scanner);

        // Main Menu
        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    if (currentUser.role == UserRole.STUDENT) {
                        takeExam(scanner);
                    } else {
                        createExam(scanner);
                    }
                    break;
                case 2:
                    viewExamResults();
                    break;
                case 3:
                    System.out.println("Exiting the Online Exam System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }

    private static User authenticateUser(Scanner scanner) {
        while (true) {
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            User user = users.get(username);
            if (user != null && user.password.equals(password)) {
                System.out.println("Authentication successful. Welcome, " + user.username + "!");
                return user;
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. " + (currentUser.role == UserRole.STUDENT ? "Take Exam" : "Create Exam"));
        System.out.println("2. View Exam Results");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private static void takeExam(Scanner scanner) {
        System.out.println("\nAvailable Exams:");
        for (int i = 0; i < exams.size(); i++) {
            System.out.println((i + 1) + ". " + exams.get(i).title);
        }

        System.out.print("Choose an exam to take: ");
        int examNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (examNumber > 0 && examNumber <= exams.size()) {
            Exam selectedExam = exams.get(examNumber - 1);
            System.out.println("Taking " + selectedExam.title);

            // Simulate exam-taking process
            // In a real system, you would implement a timer, record responses, and handle submission.
            for (Question question : selectedExam.questions) {
                System.out.println("\nQuestion: " + question.text);
                if (question instanceof MultipleChoiceQuestion) {
                    MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
                    System.out.println("Options:");
                    for (int i = 0; i < mcq.options.size(); i++) {
                        System.out.println((i + 1) + ". " + mcq.options.get(i));
                    }
                }
                System.out.print("Your response: ");
                String response = scanner.nextLine();
                System.out.println("Your response is " + (question.isCorrect(response) ? "correct!" : "incorrect."));
            }
        } else {
            System.out.println("Invalid exam number.");
        }
    }

    private static void createExam(Scanner scanner) {
        System.out.print("Enter the title of the new exam: ");
        String title = scanner.nextLine();
        System.out.print("Enter the duration of the new exam (in minutes): ");
        int duration = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Exam newExam = new Exam(title, duration);

        while (true) {
            System.out.println("\nAdd Questions to the Exam:");
            System.out.println("1. Multiple-Choice Question");
            System.out.println("2. Short Answer Question");
            System.out.println("3. Finish Exam Creation");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter the text of the multiple-choice question: ");
                    String mcqText = scanner.nextLine();
                    System.out.println("Enter the options (comma-separated): ");
                    List<String> mcqOptions = List.of(scanner.nextLine().split(","));
                    System.out.print("Enter the correct option number: ");
                    int correctOption = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    newExam.addQuestion(new MultipleChoiceQuestion(mcqText, mcqOptions, correctOption));
                    break;
                case 2:
                    System.out.print("Enter the text of the short answer question: ");
                    String saqText = scanner.nextLine();
                    System.out.print("Enter the correct answer: ");
                    String correctAnswer = scanner.nextLine();

                    newExam.addQuestion(new ShortAnswerQuestion(saqText, correctAnswer));
                    break;
                case 3:
                    exams.add(newExam);
                    System.out.println("Exam creation completed.");
                    return;
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }

    private static void viewExamResults() {
        if (currentUser.role == UserRole.STUDENT) {
            System.out.println("\nYour Exam Results:");
            // In a real system, you would fetch and display the results of the current user.
        } else {
            System.out.println("\nTeacher View: Exam Results");
            for (Exam exam : exams) {
                System.out.println("\nExam: " + exam.title);
                // In a real system, you would fetch and display results for each student.
            }
        }
    }
}
