import java.util.List;
import java.util.Scanner;

/**
 * Student Management System - a menu-driven Java console application.
 */
public class Main {
    private static final Scanner SC = new Scanner(System.in);
    private static final String HEADER =
            "+------+------------------------+--------------+-----+-------+\n"
          + "| ID   | Name                   | Department   | Sem | CGPA  |\n"
          + "+------+------------------------+--------------+-----+-------+";
    private static final String FOOTER =
            "+------+------------------------+--------------+-----+-------+";

    public static void main(String[] args) {
        StudentRepository repo = new StudentRepository("students.csv");
        boolean running = true;

        System.out.println("=== STUDENT MANAGEMENT SYSTEM ===");
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ", 0, 8);
            System.out.println();
            switch (choice) {
                case 1: addStudent(repo); break;
                case 2: printTable(repo.findAll()); break;
                case 3: searchStudent(repo); break;
                case 4: updateStudent(repo); break;
                case 5: deleteStudent(repo); break;
                case 6: printTable(repo.sortedByCgpa()); break;
                case 7: showStatistics(repo); break;
                case 0: running = false; break;
                default: System.out.println("Please choose a valid option.");
            }
        }
        System.out.println("Thank you for using the Student Management System. Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n----------------- MENU -----------------");
        System.out.println(" 1. Add student");
        System.out.println(" 2. View all students");
        System.out.println(" 3. Search student by name");
        System.out.println(" 4. Update student");
        System.out.println(" 5. Delete student");
        System.out.println(" 6. Rank students by CGPA");
        System.out.println(" 7. View statistics");
        System.out.println(" 0. Exit");
        System.out.println("----------------------------------------");
    }

    private static void addStudent(StudentRepository repo) {
        String name = readText("Name: ");
        String dept = readText("Department: ");
        int sem = readInt("Semester (1-8): ", 1, 8);
        double cgpa = readDouble("CGPA (0-10): ", 0, 10);
        Student s = repo.add(name, dept, sem, cgpa);
        System.out.println("Student added successfully with ID " + s.getId() + ".");
    }

    private static void searchStudent(StudentRepository repo) {
        String keyword = readText("Enter name to search: ");
        printTable(repo.searchByName(keyword));
    }

    private static void updateStudent(StudentRepository repo) {
        int id = readInt("Enter student ID to update: ", 1, Integer.MAX_VALUE);
        Student s = repo.findById(id);
        if (s == null) {
            System.out.println("No student found with ID " + id + ".");
            return;
        }
        System.out.println("Leave a field blank to keep its current value.");
        String name = readOptional("Name [" + s.getName() + "]: ");
        if (!name.isEmpty()) s.setName(name);
        String dept = readOptional("Department [" + s.getDepartment() + "]: ");
        if (!dept.isEmpty()) s.setDepartment(dept);
        String sem = readOptional("Semester [" + s.getSemester() + "]: ");
        if (!sem.isEmpty()) {
            try {
                int v = Integer.parseInt(sem);
                if (v >= 1 && v <= 8) s.setSemester(v);
                else System.out.println("Semester must be 1-8. Kept old value.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Kept old value.");
            }
        }
        String cg = readOptional("CGPA [" + s.getCgpa() + "]: ");
        if (!cg.isEmpty()) {
            try {
                double v = Double.parseDouble(cg);
                if (v >= 0 && v <= 10) s.setCgpa(v);
                else System.out.println("CGPA must be 0-10. Kept old value.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Kept old value.");
            }
        }
        repo.update();
        System.out.println("Student updated successfully.");
    }

    private static void deleteStudent(StudentRepository repo) {
        int id = readInt("Enter student ID to delete: ", 1, Integer.MAX_VALUE);
        System.out.println(repo.delete(id) ? "Student deleted." : "No student found with ID " + id + ".");
    }

    private static void showStatistics(StudentRepository repo) {
        List<Student> ranked = repo.sortedByCgpa();
        if (ranked.isEmpty()) {
            System.out.println("No data available.");
            return;
        }
        System.out.println("Total students : " + ranked.size());
        System.out.printf("Average CGPA   : %.2f%n", repo.averageCgpa());
        Student top = ranked.get(0);
        System.out.println("Top performer  : " + top.getName() + " (" + top.getCgpa() + ")");
    }

    private static void printTable(List<Student> list) {
        if (list.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        System.out.println(HEADER);
        for (Student s : list) System.out.println(s);
        System.out.println(FOOTER);
    }

    // ---------- input helpers with validation ----------

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String v = SC.nextLine().trim();
            if (!v.isEmpty()) return v;
            System.out.println("Input cannot be empty.");
        }
    }

    private static String readOptional(String prompt) {
        System.out.print(prompt);
        return SC.nextLine().trim();
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(SC.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException ignored) { }
            System.out.println("Enter a whole number between " + min + " and " + max + ".");
        }
    }

    private static double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(SC.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException ignored) { }
            System.out.println("Enter a number between " + min + " and " + max + ".");
        }
    }
}
