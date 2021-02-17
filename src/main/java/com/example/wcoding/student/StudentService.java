package com.example.wcoding.student;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
       Optional<Student> studentByEmail =  studentRepository.findStudentByEmail(student.getEmail());

       if (studentByEmail.isPresent()){
           throw new IllegalStateException("Email is already taken");
       }

       studentRepository.save(student);
       System.out.println(student);
    }

    public void deleteStudent(Long id) {
      boolean exists =  studentRepository.existsById(id);

      if(!exists){
          throw new IllegalStateException("User does not exist with the id: " + id);
      }

      studentRepository.deleteById(id);
    }

    public void updateStudent(Long studentId, String name, String email) {
        boolean exists =  studentRepository.existsById(studentId);

        if(!exists){
            throw new IllegalStateException("User does not exist with the id: " + studentId);
        }

        studentRepository.findById(studentId)
                .map(student -> {
                    student.setName(name);
                    student.setEmail(email);
                    return studentRepository.save(student);
                });
    }
}
