package dev.vorstu;

import dev.vorstu.entities.Group;
import dev.vorstu.entities.Student;
import dev.vorstu.entities.User;
import dev.vorstu.enums.Role;
import dev.vorstu.repositories.GroupRepository;
import dev.vorstu.repositories.StudentRepository;
import dev.vorstu.repositories.TeacherRepository;
import dev.vorstu.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    public Initializer(StudentRepository studentRepository, GroupRepository groupRepository, UserRepository userRepository, TeacherRepository teacherRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
//        Group groupVm = new Group("VM");
//        groupRepository.save(groupVm);
//        User user1 = new User("user1@mail.ru", passwordEncoder.encode("123"), Role.STUDENT);
//        userRepository.save(user1);
//        Student student1 = new Student("User1", groupVm, user1, "+7");
//        studentRepository.save(student1);
    }
}
