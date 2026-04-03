package edu.biwu.sms;

import java.time.LocalDate;

/**
 * 测试
 */
public class Demo {
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem(6);
        //1.添加学生
        sms.addStudent(new PrimaryStudent(20241316,"小新",77,81
                , LocalDate.parse("2013-10-12"),"233434@qq.com"));
        sms.addStudent(new PrimaryStudent(20241327,"小明",95,88
                , LocalDate.parse("2014-11-12"),"233534@qq.com"));
        sms.addStudent(new PrimaryStudent(20241333,"花花",90,63
                , LocalDate.parse("2023-10-15"),"233434234344"));
        sms.addStudent(new PrimaryStudent(20241335,"天天",71,83
                , LocalDate.parse("2024-12-12"),"123123323@qq.com"));

        sms.showAllStudents();
        System.out.println("-------------------");
        System.out.println(sms.randomRollCall());

    }

}
