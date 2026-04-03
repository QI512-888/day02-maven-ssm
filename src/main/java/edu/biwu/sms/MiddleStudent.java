package edu.biwu.sms;

import edu.biwu.constant.EnrollmentStatus;

import java.time.LocalDate;

/*中学生类*/
public class MiddleStudent extends Student implements EveningStudy {
    /*历史成绩*/
    private Integer historyScore;

    /*物理成绩*/
    private Integer physicsScore;

    public MiddleStudent(Integer studentId, String name, Integer chineseScore, Integer mathScore, LocalDate enrollmentDate, String email, EnrollmentStatus enrollmentStatus, Integer historyScore, Integer physicsScore) {
        super(studentId, name, chineseScore, mathScore, enrollmentDate, email, enrollmentStatus);
        this.historyScore = historyScore;
        this.physicsScore = physicsScore;
    }

    public MiddleStudent(Integer studentId, String name, Integer chineseScore, Integer mathScore, LocalDate enrollmentDate,
                         String email, Integer historyScore, Integer physicsScore) {
        super(studentId, name, chineseScore, mathScore, enrollmentDate, email);
        this.historyScore = historyScore;
        this.physicsScore = physicsScore;
    }

    public MiddleStudent(Integer studentId, String name, Integer chineseScore, Integer mathScore,
                         Integer historyScore, Integer physicsScore) {
        super(studentId, name, chineseScore, mathScore);
        this.historyScore = historyScore;
        this.physicsScore = physicsScore;
    }

    public MiddleStudent(Integer studentId, String name, Integer chineseScore, Integer mathScore,
                         LocalDate enrollmentDate, Integer historyScore, Integer physicsScore) {
        super(studentId, name, chineseScore, mathScore, enrollmentDate);
        this.historyScore = historyScore;
        this.physicsScore = physicsScore;
    }

    public int getHistoryScore() {
        return historyScore;
    }

    public void setHistoryScore(Integer historyScore) {
        this.historyScore = historyScore;
    }

    public Integer getPhysicsScore() {
        return physicsScore;
    }

    public void setPhysicsScore(Integer physicsScore) {
        this.physicsScore = physicsScore;
    }

    /*重写上晚自习方法*/
    @Override
    public void eveningStudy() {
        System.out.println("中学生" + getName() + "正在上晚自习");
    }

    /*中学生的总成绩*/
    @Override
    public Integer getTotalScore() {
        return super.getTotalScore() + historyScore + physicsScore;
    }

    @Override
    public String toString() {
        return getStudentId() + " " + getName() + " " + getChineseScore() + " "
                + getMathScore() + " " + getHistoryScore() + " " + getPhysicsScore() + " "
                +" "+getEmail()+" "+ getEnrollmentDate() + " " + getTotalScore()+" "+getAvatar()+" "+getEnrollmentStatus();
    }
}
