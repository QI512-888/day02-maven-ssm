package edu.biwu.sms02;

import java.io.IOException;


public class Demo02 {
    public static void main(String[] args) throws IOException {
        //method01();
        //method02();
        StudentManagementSystem02 sms = new StudentManagementSystem02();

        //1.从中学生信息.txt种加载中学生信息到系统
        sms.loadStudentsFromFile("day23-IO-01/中学生信息.txt");

        //2.将中学生信息按照总分从高到低排名后,写入一个 总分排名.txt
        sms.writeRankedStudentsToFile("day23-IO-01/总分排名.txt");
    }

    private static void method02() throws IOException {
        StudentManagementSystem02 sms = new StudentManagementSystem02();

        //1.从中学生信息.txt种加载中学生信息到系统
        sms.loadStudentsFromFile("day23-IO-01/中学生信息.txt");

        //2.展示学生
        sms.showAllStudents();

        //3.先按照总分从高到低排序
        sms.sortByTotalScore();

        System.out.println("================名次从高到低排名=================");
        //4.展示排完序的学生
        sms.showAllStudents();
    }

    private static void method01() throws IOException {
        StudentManagementSystem02 sms = new StudentManagementSystem02();

        //1.从小学生信息.txt种加载小学生信息到系统
        sms.loadStudentsFromFile("day23-IO-01/小学生信息.txt");

        //2.展示学生
        sms.showAllStudents();
    }


}
