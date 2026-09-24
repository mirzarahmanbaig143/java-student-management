# Student Management System (Java Console App)

A menu-driven console application written in core Java that manages student records with full CRUD operations, input validation and automatic file persistence.

## Features
- Add, view, search, update and delete student records
- Rank students by CGPA and view class statistics (total, average, top performer)
- Input validation for every field (no crashes on bad input)
- Data saved automatically to `students.csv` and reloaded on start-up

## Concepts Demonstrated
Object-oriented programming (classes, encapsulation), collections (`ArrayList`), sorting with `Comparator`, file I/O (`BufferedReader` / `BufferedWriter`), exception handling and modular code design.

## Project Structure
```
src/
 ├── Student.java             # Data model + CSV conversion
 ├── StudentRepository.java   # Storage, search, sort, persistence
 └── Main.java                # Menu and user interaction
```

## How to Run
Requires JDK 8 or later.
```bash
javac -d out src/*.java
java -cp out Main
```

## Sample Output
```
| ID   | Name                   | Department   | Sem | CGPA  |
+------+------------------------+--------------+-----+-------+
| 1    | Asha Rao               | CSE          | 3   | 8.40  |
```

## Author
Mirza Rohail Baig - B.Tech Computer Science Engineering
