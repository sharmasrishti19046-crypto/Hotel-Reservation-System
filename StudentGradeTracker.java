import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {

    // Calculate grade according to marks
    public static String calculateGrade(double marks) {

        if (marks >= 90) {
            return "A";
        } else if (marks >= 80) {
            return "B";
        } else if (marks >= 70) {
            return "C";
        } else if (marks >= 60) {
            return "D";
        } else if (marks >= 50) {
            return "E";
        } else {
            return "F";
        }
    }

    // Display complete student report
    public static void displayReport(ArrayList<String> names,
                                     ArrayList<Double> marks) {

        if (names.isEmpty()) {
            System.out.println("\nNo students added yet.");
            return;
        }

        double total = 0;
        double highest = marks.get(0);
        double lowest = marks.get(0);

        System.out.println("\n========== STUDENT GRADE REPORT ==========");

        for (int i = 0; i < names.size(); i++) {

            System.out.println(
                names.get(i) + " : " + marks.get(i)
                + " | Grade: " + calculateGrade(marks.get(i))
            );

            total = total + marks.get(i);

            if (marks.get(i) > highest) {
                highest = marks.get(i);
            }

            if (marks.get(i) < lowest) {
                lowest = marks.get(i);
            }
        }

        double average = total / marks.size();

        System.out.println("------------------------------------------");
        System.out.println("Average Marks : " + average);
        System.out.println("Highest Marks : " + highest);
        System.out.println("Lowest Marks  : " + lowest);
        System.out.println("==========================================");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<String> studentNames = new ArrayList<>();
        ArrayList<Double> studentMarks = new ArrayList<>();

        int choice;

        do {

            System.out.println("\n========== STUDENT GRADE TRACKER ==========");
            System.out.println("1. Add Student");
            System.out.println("2. View Student Report");
            System.out.println("3. Exit");
            System.out.println("============================================");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:

                    sc.nextLine();

                    System.out.print("Enter student name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter marks: ");
                    double marks = sc.nextDouble();

                    if (marks < 0 || marks > 100) {
                        System.out.println(
                            "Invalid marks! Please enter marks between 0 and 100."
                        );
                    } else {
                        studentNames.add(name);
                        studentMarks.add(marks);

                        System.out.println(
                            "Student added successfully!"
                        );
                    }

                    break;

                case 2:

                    displayReport(studentNames, studentMarks);
                    break;

                case 3:

                    System.out.println(
                        "\nThank you for using Student Grade Tracker!"
                    );
                    break;

                default:

                    System.out.println(
                        "Invalid choice! Please select 1, 2 or 3."
                    );
            }

        } while (choice != 3);

        sc.close();
    }
}