package edu.biwu.sms;

import edu.biwu.constant.EnrollmentStatus;

import java.time.LocalDate;

/*小学生类*/
public class PrimaryStudent extends Student {
    public PrimaryStudent(Integer studentId, String name, Integer chineseScore, Integer mathScore, LocalDate enrollmentDate, String email, EnrollmentStatus enrollmentStatus) {
        super(studentId, name, chineseScore, mathScore, enrollmentDate, email, enrollmentStatus);
    }

    public PrimaryStudent(Integer studentId, String name, Integer chineseScore, Integer mathScore, LocalDate enrollmentDate, String email) {
        super(studentId, name, chineseScore, mathScore, enrollmentDate, email);
    }

    public PrimaryStudent(Integer studentId, String name, Integer chineseScore, Integer mathScore, LocalDate enrollmentDate) {
        super(studentId, name, chineseScore, mathScore, enrollmentDate);
    }

    public PrimaryStudent(Integer studentId, String name, Integer chineseScore, Integer mathScore) {
        super(studentId, name, chineseScore, mathScore);
    }
}
