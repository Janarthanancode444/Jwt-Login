package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.entity.Teacher;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
    List<Teacher> findByEmail(String email);

    List<Teacher> findByPhone(String phone);

    //@Query("SELECT t.name as teacherName,s.name as teacherSchool,sec.name as teacherHandlingSections,t.id as teacherId from Teacher t join School s on s.id=t.school_id join Section sec on sec.teacher_id=t.id join Standard st on st.school_id=s.id join Subject sub on sub.standard_id=st.id where s.name=:name AND sub.name = :subject AND ( (:isLesserThan = true AND st.name < :range) OR (:isLesserThan = false AND st.name > :range) )")
    //@Query("select s.name as schoolName,sub.name as subjectName,std.name as standardName FROM School s ,Standard std ,Subject sub  WHERE s.name = :school AND sub.name = :subject AND ( (:isLesserThan = true AND std.name < :range) OR (:isLesserThan = false AND std.name > :range) )")
    @Query(value = "SELECT t.name AS teacherName,s.name AS teacherSchool,sec.section AS teacherHandlingSection,t.id AS teacherId FROM Teacher t JOIN School s ON s.id = t.school_id JOIN Section sec ON sec.teacher_id= t.id JOIN Standard st ON st.id = sec.standard_id JOIN Subject sub ON sub.standard_id = st.id WHERE s.name = :school AND sub.name = :subject AND ((:isLesserThan = true AND st.name < :range)  OR (:isLesserThan = false AND st.name > :range))",nativeQuery = true)
    List<Tuple> search(@Param("school") final String school, @Param("subject") final String subject, @Param("range") final String range, @Param("isLesserThan") final Boolean isLesserThan);
}
