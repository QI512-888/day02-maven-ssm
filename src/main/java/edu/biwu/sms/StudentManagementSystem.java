package edu.biwu.sms;

import java.time.LocalDate;
import java.util.Random;

/**
 * 学生管理系统类
 *  需求: 添加一个随机点名功能,最终返回随机点到的学生对象
 *  students
 *    0    1     2   3     4      5
 *   小新  小明  天天  null null  null
 *  获取存储的元素个数: 3 => [0,2]
 */
public class StudentManagementSystem {
    /*定义一个Student类型的数组*/
    private Student[] students;

    /*专门用来记录存储的元素个数*/
    private int count;

    public StudentManagementSystem(int capacity) {
        //capacity:代表数组容量
        //将来传递一个整数,就代表开辟的数组空间
        this.students = new Student[capacity];
    }

    /**
     *
     * @return 随机取到的学生
     */
    public Student randomRollCall() {
        int randomIndex = new Random().nextInt(count);
        return students[randomIndex];
    }

    /**
     *
     * @param student 学生对象
     * @return 学生的邮箱校验结果
     */
    public boolean validateStudentEmail(Student student) {
        //1.健壮性判断,防空处理
        if (student == null || student.getEmail() == null) {
            return false;
        } else {
            //2.邮箱匹配判断
            return student.getEmail().matches("\\w+@\\w+\\.\\w+");
        }
    }


    /**
     * 如果返回值有多个,我们可以考虑将多个返回值封装到一个容器中
     * 然后返回该容器
     * @param date 指定日期
     * @return 装满了满足条件的学生信息数组
     */
    public Student[] getStudentsEnrolledBefore(String date){
         //1.定义一个数组,该数组专门用来存放满足条件的学生信息
        Student[] temps = new Student[students.length];

        //2.遍历students中的每个学生,逐个判断
        //如果该学生的入学日期在指定日期之前,我们就将该学生存入temps数组中
        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
            //if (student != null && student.getEnrollmentDate() != null
            //        && student.getEnrollmentDate().isBefore(LocalDate.parse(date))
            //        || student.getEnrollmentDate().isEqual(LocalDate.parse(date))) { //LocalDate.parse(date)如果不指定默认按照
            //                                                                         //年-月-日的格式解析
            //    addStudent(temps, student);//将满足条件的当前学生添加到temps
            //}
            if (student != null && student.getEnrollmentDate() != null) {
                if (student.getEnrollmentDate().isBefore(LocalDate.parse(date))
                        ||student.getEnrollmentDate().isEqual(LocalDate.parse(date))) {

                    addStudent(temps, student);//将满足条件的当前学生添加到temps
                }
            }
        }
        return temps;
    }

    /**
     * 学生信息管理系统能够查询指定日期之后(包含指定日期)入学的学生信息
     * @param date 指定的日期
     * @return 装满了满足条件的学生信息数组
     */
    public Student[] getStudentsEnrolledAfter(String date){
        //1.定义一个数组,该数组专门用来存放满足条件的学生信息
        Student[] temps = new Student[students.length];

        //2.遍历students中的每个学生,逐个判断
        //如果该学生的入学日期在指定日期之前,我们就将该学生存入temps数组中
        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
            //if (student != null && student.getEnrollmentDate() != null
            //        && student.getEnrollmentDate().isAfter(LocalDate.parse(date))
            //        || student.getEnrollmentDate().isEqual(LocalDate.parse(date))) { //LocalDate.parse(date)如果不指定默认按照
            //    //年-月-日的格式解析
            //    addStudent(temps, student);//将满足条件的当前学生添加到temps
            //}
            if (student != null && student.getEnrollmentDate() != null) {
                if (student.getEnrollmentDate().isAfter(LocalDate.parse(date))
                        ||student.getEnrollmentDate().isEqual(LocalDate.parse(date))) {

                    addStudent(temps, student);//将满足条件的当前学生添加到temps
                }
            }
        }
        return temps;
    }

    /**
     * 判断系统中是否存在该学生(要求如果系统中存在该学生的姓名,我们就认为该学生在系统存在)
     */
    public boolean contains(Student stu) {
        if (stu == null) {
            return false;
        }
        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
            if (student != null && student.equals(stu)) { //student.equals(stu)一旦结果为true,代表该学生的姓名在系统中存在
                 return true;
            }
        }
        return false; //系统中(数组中)所有的学生姓名和该学生均不相同,我们认为该学生在系统中不存在
    }

    /**
     *
     * @param students 指定一个数组
     * @param student 向指定的数组中添加元素
     */
    public void addStudent(Student[] students,Student student) {
        if(validateStudentEmail(student)) {
            for (int i = 0; i < students.length; i++) {
                //如果数组中的元素为null,代表该位置还没有存储学生对象,此位置可以使用
                if (students[i] == null) {
                    students[i] = student;
                    count++;//存储学生对象的同时,记录系统中已存入的学生个数
                    break;//一旦将学生信息存储到数组的某个位置,就直接中断循环(没必要在找空位)
                }
            }
        }else{
            System.out.println("学号:" + student.getStudentId() + ", 姓名:" + student.getName()
                    + ", 邮箱:" + student.getEmail() + ", 不符合邮箱格式添加失败!");
        }
    }


    /**
     * 添加学生
     *
     * @param student 将来接收学生对象(小学生对象或中学生对象),对象中封装着学生的信息(学号,姓名....)
     */
    public void addStudent(Student student) {
        addStudent(students, student);
    }
    /**
     * 展示指定的学生数组中的学生信息
     */
    public void showAllStudents(Student[] students) {
        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
            if (student != null) { //如果数组中取出的不是null,在进行信息展示,防止空指针异常
                System.out.println((i + 1) + " " + student.toString());
            }
        }
    }

    /**
     * 展示所有学生信息
     */
    public void showAllStudents() {
        showAllStudents(students);
    }

    /**
     * @param subject 指定科目,例如: "语文"
     * @return 指定科目最高成绩的学生信息
     */
    public Student findStudentWithHighestScore(String subject) {
        int highestScore = 0;
        Student studentWithHighestScore = null;
        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
            if (student != null) {
                int score = getScoreBySubject(student, subject); //用score存储数组中当前学生的某一门成绩
                if (score > highestScore) {  //语文成绩: 77 95 90 71
                    //遍历第一个学生 highestScore= 77 studentWithHighestScore=第一个学生
                    highestScore = score; //遍历第二个学生 highestScore =95 studentWithHighestScore=第二个学生
                    studentWithHighestScore = student;
                }
            }
        }

        return studentWithHighestScore;//最终返回最高成绩的学生信息
    }

    /**
     * @param subject 指定科目,例如: "语文"
     * @return 指定科目最低成绩的学生信息
     */
    public Student findStudentWithLowestScore(String subject) {
        int lowestScore = Integer.MAX_VALUE;//Integer.MAX_VALUE指的是整数变量的最大值 2^31 - 1
        Student studentWithLowestScore = null;
        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
            if (student != null) {
                int score = getScoreBySubject(student, subject); //用score存储数组中当前学生的某一门成绩
                if (score < lowestScore) {  //语文成绩: 77 95 90 71
                    //遍历第一个学生 lowestScore= 77 studentWithLowestScore=第一个学生
                    lowestScore = score; //遍历第二个学生  score(95) > lowestScore(77)
                    //遍历第三个学生 score(90)  > lowestScore(77)
                    //遍历第四个学生 score (71) < lowestScore(77) lowestScore=71 studentWithLowestScore=第四个学生
                    studentWithLowestScore = student;
                }
            }
        }

        return studentWithLowestScore;//最终返回最高成绩的学生信息
    }

    /**
     * @param subject 指定科目 例如:数学
     * @return 所有学生指定科目的平均成绩
     */
    public int getAverageScoreBySubject(String subject) {
        int totalScore = 0;//对所有学生的某一门成绩累加求和
        int count = 0; // 统计数组已存储的学生对象个数
        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
            if (student != null) {
                int score = getScoreBySubject(student, subject);
                totalScore += score;
                count++;
            }
        }
        return count == 0 ? 0 : totalScore / count;//count=0代表数组中一个学生都没存,总成绩直接0
    }

    /**
     * 根据每个学生的总分数进行成绩从
     */
    public void sortByTotalScore() {
        for (int i = 0; i < students.length - 1; i++) {
            for (int j = 0; j < students.length - i - 1; j++) {
                if (students[j] != null && students[j + 1] != null) {
                    if (students[j].getTotalScore() < students[j + 1].getTotalScore()) {
                        Student temp = students[j];
                        students[j] = students[j + 1];
                        students[j + 1] = temp;
                    }
                }
            }
        }
    }

    /**
     * @param studentId 学生学号
     * @return 根据学生学号, 查询到的学生信息(封装到学生对象中)
     */
    public Student findStudentById(int studentId) {
        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
            if (student != null && student.getStudentId() == studentId) {//如果当前学生存在,并且当前学生的学号等于要查找的学号,直接返回该学生
                return student;
            }
        }
        return null;//如果程序能执行到该位置,说明找不到学号为studentId的学生,返回null
    }

    /**
     * @param student 学生对象
     * @param subject 科目名称
     * @return 当前学生指定科目的成绩
     */
    private int getScoreBySubject(Student student, String subject) {
       /* switch (subject) {
            case "语文":
                return student.getChineseScore();
            case "数学":
                return student.getMathScore();
            case "历史":
                if (student instanceof MiddleStudent) {
                    MiddleStudent ms = (MiddleStudent) student;
                    return ms.getHistoryScore();
                } else {
                    return 0;
                }
            case "物理":
                if (student instanceof MiddleStudent) {
                    MiddleStudent ms = (MiddleStudent) student;
                    return ms.getPhysicsScore();
                } else {
                    return 0;
                }
            default:
                return 0;
        }*/
        switch (subject) {
            case "语文" -> {
                return student.getChineseScore();
            }
            case "数学" -> {
                return student.getMathScore();
            }
            case "历史" -> {
                if (student instanceof MiddleStudent middleStudent) {
                    return middleStudent.getHistoryScore();
                }
            }
            case "物理" -> {
                if (student instanceof MiddleStudent middleStudent) {
                    return middleStudent.getPhysicsScore();
                }
            }
            //default -> {
            //    return 0;
            //}
        }
        return 0; //传递的subject不属于 语文,数学,历史,物理中的其中一种,直接返回0;
    }

}
