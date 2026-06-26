package dev.vorstu;

import dev.vorstu.entities.Student;
import dev.vorstu.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {

    private final StudentRepository studentRepository;

    public Initializer(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        studentRepository.save(new Student("User1", "VM", "+7"));
        studentRepository.save(new Student("User2", "VM", "+8"));
        studentRepository.save(new Student("User3", "AM", "+99"));
        studentRepository.save(new Student("User4", "AM", "+19"));

    }
}
