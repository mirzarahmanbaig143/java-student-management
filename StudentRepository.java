import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Stores students in memory and persists them to a CSV file.
 */
public class StudentRepository {
    private final List<Student> students = new ArrayList<>();
    private final File file;
    private int nextId = 1;

    public StudentRepository(String filePath) {
        this.file = new File(filePath);
        load();
    }

    public Student add(String name, String department, int semester, double cgpa) {
        Student s = new Student(nextId++, name, department, semester, cgpa);
        students.add(s);
        save();
        return s;
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public Student findById(int id) {
        for (Student s : students) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    public List<Student> searchByName(String keyword) {
        List<Student> result = new ArrayList<>();
        String k = keyword.toLowerCase();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(k)) result.add(s);
        }
        return result;
    }

    public boolean delete(int id) {
        Student s = findById(id);
        if (s == null) return false;
        students.remove(s);
        save();
        return true;
    }

    /** Call after modifying a Student returned by findById. */
    public void update() {
        save();
    }

    public List<Student> sortedByCgpa() {
        List<Student> copy = findAll();
        copy.sort(Comparator.comparingDouble(Student::getCgpa).reversed());
        return copy;
    }

    public double averageCgpa() {
        if (students.isEmpty()) return 0;
        double total = 0;
        for (Student s : students) total += s.getCgpa();
        return total / students.size();
    }

    private void load() {
        if (!file.exists()) return;
        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try {
                    Student s = Student.fromCsv(line);
                    students.add(s);
                    nextId = Math.max(nextId, s.getId() + 1);
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping bad record: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read data file: " + e.getMessage());
        }
    }

    private void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            for (Student s : students) {
                writer.write(s.toCsv());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }
}
