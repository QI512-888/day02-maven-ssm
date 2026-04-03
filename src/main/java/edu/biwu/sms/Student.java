package edu.biwu.sms;

import edu.biwu.constant.EnrollmentStatus;

import java.time.LocalDate;

public class Student /*extends Object*/ {
    /*学号*/
    private Integer studentId;

    /*姓名*/
    private String name;

    /*语文成绩*/
    private Integer chineseScore;

    /*数学成绩*/
    private Integer mathScore;

    /*学生的入学日期*/
    private LocalDate enrollmentDate;

    /*学生的邮箱*/
    private String email;

    /*学生的头像*/
    private String avatar;

    /*学生的学籍状态*/
    private EnrollmentStatus enrollmentStatus;


    public Student(Integer studentId, String name, Integer chineseScore, Integer mathScore, LocalDate enrollmentDate, String email, EnrollmentStatus enrollmentStatus) {
        this(studentId, name, chineseScore, mathScore, enrollmentDate, email);
        this.enrollmentStatus = enrollmentStatus;
    }

    public Student(Integer studentId, String name, Integer chineseScore, Integer mathScore, LocalDate enrollmentDate, String email) {
        this(studentId, name, chineseScore, mathScore, enrollmentDate);
        this.email = email;
    }

    public Student(Integer studentId, String name, Integer chineseScore, Integer mathScore, LocalDate enrollmentDate) {
        this.studentId = studentId;
        this.name = name;
        this.chineseScore = chineseScore;
        this.mathScore = mathScore;
        this.enrollmentDate = enrollmentDate;
    }

    public Student(Integer studentId, String name, Integer chineseScore, Integer mathScore) {
        this(studentId, name, chineseScore, mathScore, LocalDate.now());//入学日期默认为当前年月日
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChineseScore() {
        return chineseScore;
    }

    public void setChineseScore(Integer chineseScore) {
        this.chineseScore = chineseScore;
    }

    public Integer getMathScore() {
        return mathScore;
    }

    public void setMathScore(Integer mathScore) {
        this.mathScore = mathScore;
    }

    /*求学生的总成绩*/
    public Integer getTotalScore() {
        return chineseScore + mathScore;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(EnrollmentStatus enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    /*展示学生的信息使用该方法:拼接所有的学生属性*/
    @Override
    public String toString() {
        return studentId + " " + name + " " + chineseScore + " " + mathScore
                + " " +email+" "+ enrollmentDate + " " + getTotalScore() +" "+avatar+" "+enrollmentStatus;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;

        return name.equals(student.name);
    }
}
