package fr.aelion.streamer.services;

import fr.aelion.streamer.dto.AddStudentDto;
import fr.aelion.streamer.dto.SimpleStudentDto;
import fr.aelion.streamer.dto.SimpleStudentProjection;
import fr.aelion.streamer.dto.conceptorDtos.StudentDto;
import fr.aelion.streamer.entities.Student;
import fr.aelion.streamer.enumFolder.MemberType;
import fr.aelion.streamer.repositories.StudentRepository;
import fr.aelion.streamer.services.exceptions.EmailAlreadyExistsException;
import fr.aelion.streamer.services.exceptions.LoginAlreadyExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
@CrossOrigin
@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<StudentDto> findAll() {
        List<Student> students = repository.findAll();
        List<StudentDto> studentsDTO = students.stream().map(s->{
            StudentDto studentDto = modelMapper.map(s, StudentDto.class);
            return studentDto;
        }).toList();
        return studentsDTO;
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
        newStudent.setRole(MemberType.STUDENT);
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

    @CrossOrigin
    public Optional<Student> findByLoginAndPassword(String login, String password) {
        return repository.findByLoginAndPassword(login, password);
    }

    public String recovery(String login, String email) {
        Optional<Student> memberOpt = repository.findByLoginAndEmail(login, email);
        if(!memberOpt.isEmpty()){
            String password = passwordGenerator();
            Student member = memberOpt.get();
            member.setPassword(password);
            repository.save(member);
            return password;
        }
        return null;
    }

    private String passwordGenerator() {
        SecureRandom randomGen = new SecureRandom();
        randomGen.setSeed(randomGen.generateSeed(8));
        StringBuilder res = new StringBuilder();
        while (res.length()<10){
            Character c = Character.valueOf( (char) (randomGen.nextInt()%26+65));
            if (Character.isLetterOrDigit(c)){
                res.append(c);
            }
        }
        return res.toString();
    }
}
