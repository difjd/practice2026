package dev.vorstu;

import dev.vorstu.entities.Group;
import dev.vorstu.entities.Student;
import dev.vorstu.entities.Teacher;
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
//        Group groupAm = new Group("AM");
//        groupRepository.save(groupAm);
//        User studentUser = new User("student@test.com", passwordEncoder.encode("1234"), Role.STUDENT);
//        userRepository.save(studentUser);
//        Student student = new Student("Student Test", groupVm, studentUser, "+70000000001");
//        studentRepository.save(student);
//        User teacherUser = new User("teacher@test.com", passwordEncoder.encode("1234"), Role.TEACHER);
//        userRepository.save(teacherUser);
//        Teacher teacher = new Teacher("Teacher Test", "+70000000002", teacherUser);
//        teacher.getGroups().add(groupVm);
//        teacherRepository.save(teacher);
//        User adminUser = new User("admin@test.com", passwordEncoder.encode("1234"), Role.ADMIN);
//        userRepository.save(adminUser);

    }
}
