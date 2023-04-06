package fr.aelion.streamer.services;

import fr.aelion.streamer.dto.AddStudentDto;
import fr.aelion.streamer.dto.SimpleStudentDto;
import fr.aelion.streamer.dto.SimpleStudentProjection;
import fr.aelion.streamer.entities.Student;
import fr.aelion.streamer.entities.User;
import fr.aelion.streamer.repositories.StudentRepository;
import fr.aelion.streamer.services.exceptions.EmailAlreadyExistsException;
import fr.aelion.streamer.services.exceptions.LoginAlreadyExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Student> findAll() {
        List<Student> students = repository.findAll();
        return students;
    }

    public List<SimpleStudentDto> findSimpleStudents() {
        return repository.findAll()
                .stream()
                .map(s -> {
                    SimpleStudentDto dto = new SimpleStudentDto();
                    dto.setId(s.getId());
                    dto.setLastName(s.getLastName());
                    dto.setFirstName(s.getFirstName());
                    dto.setEmail(s.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<SimpleStudentProjection> fromProjection() {
        return repository.getSimpleStudents();
    }

    public Student add(AddStudentDto student) throws Exception {
       Student anyStudent = repository.findByEmail(student.getEmail());
        if (anyStudent != null) {
            throw new EmailAlreadyExistsException("Email " + student.getEmail() + " already exists");
        }
        anyStudent = repository.findByLogin(student.getLogin());
        if (anyStudent != null) {
            throw new LoginAlreadyExistsException("Login " + student.getLogin() + " already exists");
        }
        Student newStudent = modelMapper.map(student, Student.class);
        newStudent = (Student) repository.save(newStudent);

        return newStudent;
    }

    public void update(Student student) throws Exception {
        try {
            repository.save(student);
        } catch(Exception e) {
            throw new Exception("Something went wrong while updating Student");
        }
    }
    public Student findOne(int id) {
        return repository.findById(id)
                .map(s -> s)
                .orElseThrow();
    }

    public void delete(int id) {
        try {
            var student = this.findOne(id);
            repository.delete(student);
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    public Set<Integer> multipleDelete(Set<Integer> ids) {
        var nonDeletedIds = new HashSet<Integer>();
        ids.stream()
                .forEach(i -> {
                    try {
                        repository.delete(this.findOne(i));
                    } catch(NoSuchElementException e) {
                        nonDeletedIds.add(i);
                    } catch (Exception e) {
                        nonDeletedIds.add(i);
                    }
                });
        return nonDeletedIds;
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        Optional<Student> stud = repository.findByLoginAndPassword(login, password);
        User value = new User();
        value.setPassword(stud.get().getPassword());
        value.setLogin(stud.get().getLogin());
        value.setId(stud.get().getId());
        Optional<User> user = Optional.of(value);
        return user;
    }
}
