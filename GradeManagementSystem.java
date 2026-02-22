import java.util.Scanner;

public class GradeManagementSystem {
    private static String[] studentNames;
    private static double[][] studentMarks;
    private static final int MAX_STUDENTS = 100;
    private static final int SUBJECT_COUNT = 5;
    private static int studentCount = 0;
    private static Scanner scanner = new Scanner(System.in);

    private static String[] subjects = {
        "Mathematics", "Science", "English", "History", "Computer"
    };

    public static void main(String[] args) {
        initializeArrays();
        boolean running = true;

        while (running) {
            System.out.println("\n=== GRADE MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Student Marks");
            System.out.println("2. View All Students");
            System.out.println("3. Calculate Averages");
            System.out.println("4. Find Top Performer");
            System.out.println("5. Generate Report");
            System.out.println("6. Search Student");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = getValidInt(1, 7);

            switch (choice) {
                case 1: addStudentMarks(); break;
                case 2: viewAllStudents(); break;
                case 3: calculateAverages(); break;
                case 4: findTopPerformer(); break;
                case 5: generateReport(); break;
                case 6: searchStudent(); break;
                case 7:
                    running = false;
                    System.out.println("Thank you for using the system!");
                    break;
            }
        }
        scanner.close();
    }

    private static void initializeArrays() {
        studentNames = new String[MAX_STUDENTS];
        studentMarks = new double[MAX_STUDENTS][SUBJECT_COUNT];
    }

    private static void addStudentMarks() {
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Maximum student limit reached!");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        studentNames[studentCount] = name;

        for (int i = 0; i < SUBJECT_COUNT; i++) {
            System.out.print(subjects[i] + ": ");
            studentMarks[studentCount][i] = getValidMark();
        }

        studentCount++;
        System.out.println("Student added successfully!");
    }

    private static void viewAllStudents() {
        if (studentCount == 0) {
            System.out.println("No students found!");
            return;
        }

        for (int i = 0; i < studentCount; i++) {
            System.out.print("\nName: " + studentNames[i]);
            for (int j = 0; j < SUBJECT_COUNT; j++) {
                System.out.print(" | " + subjects[j] + ": " + studentMarks[i][j]);
            }
            System.out.printf(" | Average: %.2f\n", calculateStudentAverage(i));
        }
    }

    private static double calculateStudentAverage(int index) {
        double sum = 0;
        for (int i = 0; i < SUBJECT_COUNT; i++)
            sum += studentMarks[index][i];
        return sum / SUBJECT_COUNT;
    }

    private static void calculateAverages() {
        for (int i = 0; i < studentCount; i++) {
            double avg = calculateStudentAverage(i);
            System.out.printf("%s → Average: %.2f | Grade: %s\n",
                    studentNames[i], avg, getGrade(avg));
        }
    }

    private static void findTopPerformer() {
        if (studentCount == 0) return;

        double highest = 0;
        int topIndex = 0;

        for (int i = 0; i < studentCount; i++) {
            double avg = calculateStudentAverage(i);
            if (avg > highest) {
                highest = avg;
                topIndex = i;
            }
        }

        System.out.println("Top Performer: " + studentNames[topIndex] +
                " | Average: " + highest);
    }

    private static void searchStudent() {
        System.out.print("Enter student name to search: ");
        String name = scanner.nextLine();

        for (int i = 0; i < studentCount; i++) {
            if (studentNames[i].equalsIgnoreCase(name)) {
                System.out.println("Student Found!");
                System.out.printf("Average: %.2f | Grade: %s\n",
                        calculateStudentAverage(i),
                        getGrade(calculateStudentAverage(i)));
                return;
            }
        }
        System.out.println("Student not found!");
    }

    private static void generateReport() {
        if (studentCount == 0) return;

        System.out.println("\n=== PERFORMANCE REPORT ===");
        System.out.println("Total Students: " + studentCount);

        // Subject averages
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            double sum = 0;
            for (int j = 0; j < studentCount; j++)
                sum += studentMarks[j][i];
            System.out.printf("%s Average: %.2f\n",
                    subjects[i], sum / studentCount);
        }

        // Grade distribution
        int A=0,B=0,C=0,D=0,F=0;
        for (int i = 0; i < studentCount; i++) {
            double avg = calculateStudentAverage(i);
            String grade = getGrade(avg);
            switch (grade) {
                case "A": case "A+": A++; break;
                case "B": B++; break;
                case "C": C++; break;
                case "D": D++; break;
                case "F": F++; break;
            }
        }

        System.out.println("\nGrade Distribution:");
        System.out.println("A: " + A);
        System.out.println("B: " + B);
        System.out.println("C: " + C);
        System.out.println("D: " + D);
        System.out.println("F: " + F);
    }

    private static String getGrade(double avg) {
        if (avg >= 90) return "A+";
        else if (avg >= 80) return "A";
        else if (avg >= 70) return "B";
        else if (avg >= 60) return "C";
        else if (avg >= 50) return "D";
        else return "F";
    }

    private static int getValidInt(int min, int max) {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value >= min && value <= max)
                    return value;
                else
                    System.out.print("Enter between " + min + " and " + max + ": ");
            } catch (Exception e) {
                System.out.print("Invalid input! Enter number: ");
                scanner.nextLine();
            }
        }
    }

    private static double getValidMark() {
        while (true) {
            try {
                double mark = scanner.nextDouble();
                scanner.nextLine();
                if (mark >= 0 && mark <= 100)
                    return mark;
                else
                    System.out.print("Marks must be 0-100. Re-enter: ");
            } catch (Exception e) {
                System.out.print("Invalid input! Enter number: ");
                scanner.nextLine();
            }
        }
    }
}