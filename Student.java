/**
 * Represents a single student record.
 */
public class Student {
    private final int id;
    private String name;
    private String department;
    private int semester;
    private double cgpa;

    public Student(int id, String name, String department, int semester, double cgpa) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.semester = semester;
        this.cgpa = cgpa;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public int getSemester() { return semester; }
    public double getCgpa() { return cgpa; }

    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setSemester(int semester) { this.semester = semester; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }

    /** Serialises the student as one CSV line: id,name,department,semester,cgpa */
    public String toCsv() {
        return id + "," + name.replace(",", " ") + "," + department.replace(",", " ")
                + "," + semester + "," + cgpa;
    }

    /** Rebuilds a student from a CSV line produced by {@link #toCsv()}. */
    public static Student fromCsv(String line) {
        String[] p = line.split(",");
        if (p.length != 5) {
            throw new IllegalArgumentException("Invalid record: " + line);
        }
        return new Student(Integer.parseInt(p[0].trim()), p[1].trim(), p[2].trim(),
                Integer.parseInt(p[3].trim()), Double.parseDouble(p[4].trim()));
    }

    @Override
    public String toString() {
        return String.format("| %-4d | %-22s | %-12s | %-3d | %-5.2f |",
                id, name, department, semester, cgpa);
    }
}
