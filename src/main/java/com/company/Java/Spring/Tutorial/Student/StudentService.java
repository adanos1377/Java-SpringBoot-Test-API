package com.company.Java.Spring.Tutorial.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getStudent(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentbyemail=studentRepository.findStudentByEmail(student.getEmail());
        if(studentbyemail.isPresent()){
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        boolean b=studentRepository.existsById(id);
        if(!b){
            throw new IllegalArgumentException("no student with this ID");
        }
        studentRepository.deleteById(id);
    }
    @Transactional
    public void updateStudent(Long studentId, String name, String email){
        Student student= studentRepository.findById(studentId).orElseThrow(()->new IllegalStateException("student does not exist"));
        if (name!=null && name.length()>0 && !name.equals(student.getName())){
            student.setName(name);
        }
        if(email!=null && email.length()>0 && !email.equals(student.getEmail())){
            Optional<Student> studentoptional=studentRepository.findStudentByEmail(email);
            if(studentoptional.isPresent()){
                throw new IllegalStateException("emailo taken");
            }
        }
        student.setEmail(email);
    }
}
