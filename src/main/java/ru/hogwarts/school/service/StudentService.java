package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.exception.EntranceAgesAreWrongException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.exception.StudentWithoutFacultyException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final RecordMapper recordMapper;

    public StudentService(StudentRepository studentRepository,
                          RecordMapper recordMapper) {
        this.studentRepository = studentRepository;
        this.recordMapper = recordMapper;
    }

    public StudentRecord addStudent(StudentRecord studentRecord) {
        return recordMapper.toRecord(studentRepository.save(recordMapper.toEntity(studentRecord)));
    }

    public StudentRecord findStudent(long id) {
        return studentRepository.findById(id)
                .map(recordMapper::toRecord)
                .orElseThrow(StudentNotFoundException::new);
    }
    public StudentRecord editStudent(StudentRecord studentRecord) {
        Student oldStudent = recordMapper.toEntity(findStudent(studentRecord.getId()));
        oldStudent.setName(studentRecord.getName());
        oldStudent.setAge(studentRecord.getAge());
        return recordMapper.toRecord(studentRepository.save(oldStudent));
    }

    public StudentRecord deleteStudent(long id) {
        Student student = recordMapper.toEntity(findStudent(id));
        studentRepository.delete(student);
        return recordMapper.toRecord(student);
    }

    public List<StudentRecord> findByAgeBetween(int minAge, int maxAge) {
        if (minAge <= 0 || maxAge <= 0 || minAge > maxAge) {
            throw new EntranceAgesAreWrongException();
        }
        return studentRepository.findStudentsByAgeBetween(minAge, maxAge).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public FacultyRecord getStudentFaculty(long id) {
        if (findStudent(id).getFaculty() == null) {
            throw new StudentWithoutFacultyException();
        }
        return findStudent(id).getFaculty();
    }
}
