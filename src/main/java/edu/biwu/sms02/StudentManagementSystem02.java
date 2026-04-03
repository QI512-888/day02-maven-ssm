package edu.biwu.sms02;

import edu.biwu.sms.MiddleStudent;
import edu.biwu.sms.PrimaryStudent;
import edu.biwu.sms.Student;
import edu.biwu.constant.EnrollmentStatus;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

/*
  1.添加两个方法:
   a.能够获取指定的学生对象的任意属性的值
   b.能够设置指定的学生对象的任意属性的值
 */
//使用Collection和泛型重构学生信息管理系统
public class StudentManagementSystem02 {
    /*定义一个集合存储元素*/
    private ArrayList<Student> students = new ArrayList<>();


    /**
     *
     * @param student  指定的学生对象
     * @param propertyName 指定的学生对象的属性
     * @param value 指定的属性值
     *
     */
    public void setPropertyValue(Student student,String propertyName, Object value) throws NoSuchFieldException, IllegalAccessException {
        //1.获取当前对象代表的字节码
        Class<?> cls = student.getClass();
        System.out.println(cls);

        Field field = null;
        while (cls != null) {//如果cls为null,说明已经找到Object类
            try {
                field = cls.getDeclaredField(propertyName);//先获取本字节码中是否有该属性
                break;//如果获取到指定属性,就立马终止循环
            } catch (NoSuchFieldException e) {
                //如果执行catch说明在当前字节码中没有找到对应的属性,此时获取父类的字节码
                cls = cls.getSuperclass();
            }
        }
        //当循环执行完毕会出现两种情况
        //1.找到,field被赋值,通过break中断的循环
        //2.没找到,从当前字节码开始=>找父类的字节码=>...=>一直找到Object类字节码都没找到
        if (field==null) {
            throw new NoSuchFieldException("整个继承体系都找不到该属性:" + propertyName);
        }


        //2.跳过java的权限检查
        field.setAccessible(true);

        //3.设置指定的学生对象的属性值
        field.set(student, value);
    }

    /**
     *
     * @param student 学生对象
     * @param propertyName 指定的属性名
     * @return 该学生对象对应的属性值
     */
    public Object getPropertyValue(Student student,String propertyName) throws NoSuchFieldException, IllegalAccessException {
        //1.获取当前对象代表的字节码
        Class<?> cls = student.getClass();
        System.out.println(cls);

        Field field = null;
        while (cls != null) {//如果cls为null,说明已经找到Object类
            try {
                field = cls.getDeclaredField(propertyName);//先获取本字节码中是否有该属性
                break;//如果获取到指定属性,就立马终止循环
            } catch (NoSuchFieldException e) {
                //如果执行catch说明在当前字节码中没有找到对应的属性,此时获取父类的字节码
                cls = cls.getSuperclass();
            }
        }
         //当循环执行完毕会出现两种情况
        //1.找到,field被赋值,通过break中断的循环
        //2.没找到,从当前字节码开始=>找父类的字节码=>...=>一直找到Object类字节码都没找到
        if (field==null) {
            throw new NoSuchFieldException("整个继承体系都找不到该属性:" + propertyName);
        }

        //2.跳过java的权限检查
        field.setAccessible(true);

        //3.获取指定的学生对象的属性值
        return field.get(student);
    }



    /**
     * @param resultPath 指定写入的目的地
     */
    public void writeRankedStudentsToFile(String resultPath) throws IOException {
        //1.先按照总分从高到低排名
        sortByTotalScore();

        //2.再将排好序的学生信息写入到resultPath指定文件中
        BufferedWriter bw = new BufferedWriter(new FileWriter(resultPath));
        for (Student student : students) {
            bw.write(student.toString());
            bw.newLine();//每写一个学生信息,就换一次行,最终形成一行一条学生信息
        }
        //3.关流释放资源
        bw.close();
    }

    /**
     *
     * @param filePath 数据源地址,例如:小学生信息.txt的路径
     */
    public void loadStudentsFromFile(String filePath) throws IOException {
        //1.先利用FileReader关联指定的文件路径
        //再利用BufferedReader进行包装,为了使用readLine()方法
        //File file = new File(filePath);
        //BufferedReader br = new BufferedReader(new FileReader(file));
        InputStream is = StudentManagementSystem02.class.getClassLoader()
                .getResourceAsStream(filePath);

        //利用转换流将字节流转换成字符流,在包装成缓冲流
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        //2.逐行读取指定的文件
        String line;
        while ((line = br.readLine()) != null) {
            //例如:第一行:20241316,小新,77,81,2013-10-12,233434@qq.com
            //data={20241316, 小新, 77, 81, 2013-10-12, 233434@qq.com }
            //小学生和中学生通用属性数据封装
            String[] data = line.split(",");
            Integer studentId = Integer.parseInt(data[0]);//解析出学号
            String name = data[1];
            Integer chineseScore = Integer.parseInt(data[2]);
            Integer mathScore = Integer.parseInt(data[3]);
            LocalDate enrollmentDate = LocalDate.parse(data[4]);
            String email = data[5];
            EnrollmentStatus enrollmentStatus = EnrollmentStatus.getEnumByDesc(data[6]);//根据中文学籍状态名称获取对应的枚举常量
            if (filePath.equals("中学生信息02.txt")) {
                //封装中学生的特有信息: 历史成绩和物理成绩
                //例如某个中学生: 20241316,大新,97,81,2013-10-12,233434123@qq.com,100,98
                Integer historyScore = Integer.parseInt(data[7]);
                Integer physicsScore = Integer.parseInt(data[8]);
                MiddleStudent middleStudent = new MiddleStudent(studentId, name, chineseScore, mathScore,
                        enrollmentDate, email, enrollmentStatus, historyScore, physicsScore);
                students.add(middleStudent);
            } else if(filePath.equals("小学生信息02.txt")) {
                PrimaryStudent primaryStudent = new PrimaryStudent(studentId, name, chineseScore, mathScore,
                        enrollmentDate, email,enrollmentStatus);
                students.add(primaryStudent);
            }
        }
        //最后关流释放资源
        br.close();

    }


    /**
     *
     * @param student 当前学生(将来头像图片的路径与该学生绑定)
     * @param localFilePath 模拟从本地选的一张图片
     * @param serverPath 模拟上传到服务器
     */
    public void uploadStudentAvatar(Student student,String localFilePath, String serverPath) throws IOException {
        //1.利用FileInputStream关联读的文件,然后再利用BuffredInputStream包装FileInputStream,目的提高读效率
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(localFilePath));


        //2.将上传的图片进行名称的唯一性处理(不会发生同名图片覆盖)
        // D:/img/abc.jpg
        // 例如: abc.jpg ==> 保存到服务器(名称唯一) ===> asdfdf-123123-sdfasdf.jpg
        //   再次上传abc.jpg =====保存到服务器(名称唯一)======> sdkfhdjfh-323434-dsfkdf.jpg
        String fileName = new File(localFilePath).getName();//获取文件的名称:例如abc.jpg
        String extension = fileName.substring(fileName.lastIndexOf("."));//截取文件的后缀名 .jpg
        String newFileName = UUID.randomUUID().toString() + extension; // 例如: asdfdf-123123-sdfasdf.jpg


        //3.利用FileOutStream关联写的文件,然后再利用BuffredOutputStream包装FileOutStream,目的提高读效率
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(serverPath + "/" + newFileName));


        //4.读写操作
        int b;
        while ((b = bis.read()) != -1) {
            bos.write(b);
        }

        //5.关流释放资源
        bis.close();
        bos.close();

        //6.将该上传的图片绑定到当前学生
        student.setAvatar(serverPath + "/" + newFileName);
    }
    /**
     *
     * @param subject  指定科目
     * @return 返回该科目下所有的成绩对应的人数
     */
    public Map<Integer, Integer> getScoreStatisticsBySubject(String subject) {
        HashMap<Integer, Integer> scoreStatistics = new HashMap<>();
        for (Student student : students) {
            int score = getScoreBySubject(student, subject);
            if (scoreStatistics.containsKey(score)) {
                scoreStatistics.put(score, scoreStatistics.get(score) + 1);
            } else {
                scoreStatistics.put(score, 1);
            }
        }
        return scoreStatistics;
    }
    /**
     * @param subject 指定科目,例如: "语文"
     * @return 指定科目最高成绩的学生信息
     */
    public Student findStudentWithHighestScore(String subject) {
        return Collections.max(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                //按指定科目的成绩比
                return getScoreBySubject(s1, subject) - getScoreBySubject(s2, subject);
            }
        });
    }

    /**
     * @param subject 指定科目,例如: "语文"
     * @return 指定科目最低成绩的学生信息
     */
    public Student findStudentWithLowestScore(String subject) {
        return Collections.min(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                //按指定科目的成绩比
                return getScoreBySubject(s1, subject) - getScoreBySubject(s2, subject);
            }
        });
    }

    /**
     * 根据每个学生的总分数进行成绩从高到低排序
     */
    public void sortByTotalScore() {
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o2.getTotalScore() - o1.getTotalScore();
            }
        });
    }

    /**
     *
     * @param number 学生的编号(从1开始)
     * @param newStudent 学生的新的信息(学生对象中封装新的信息)
     * @return 更改前的学生对象
     */
    public Student updateStudentByIndex(int number,Student newStudent) {
        if (number >= 1 && number <= students.size()) {
            return students.set(number - 1, newStudent);
        } else {
            System.out.println("不存在序号为:" + number + "的学生信息");
            return null;
        }
    }

    /**
     *
     * @return 随机取到的学生
     */

    public Student randomRollCall() {
        return students.get(new Random().nextInt(students.size()));
    }

    /**
     * 添加学生
     * @param student
     */
    public void addStudent(Student student) {
        if (student != null && validateStudentEmail(student)) {
            students.add(student);
        }else{
            System.out.println("学号:" + student.getStudentId() + ", 姓名:" + student.getName()
                    + ", 邮箱:" + student.getEmail() + ", 不符合邮箱格式添加失败!");
        }
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
     * 遍历指定容器中的学生
     * @param students 指定的学生容器
     */
    public void showAllStudents(Collection<Student> students) {
        int i = 1;
        for (Student student : students) {
            //if (student != null) { //在添加元素的时候,已经做了非空判断
            System.out.println((i++) + " " + student);
            //}
        }
    }
    /**
     * 遍历容器中学生
     */
    public void showAllStudents() {
        showAllStudents(students);
    }

    /**
     * 判断系统中是否存在该学生(要求如果系统中存在该学生的姓名,我们就认为该学生在系统存在)
     */
    public boolean contains(Student stu) {
        return students.contains(stu);
    }


    /**
     * @param studentId 学生学号
     * @return 根据学生学号, 查询到的学生信息(封装到学生对象中)
     */
    public Student findStudentById(int studentId) {
        for (Student student : students) {
            if (student.getStudentId() == studentId) {
                return student;
            }
        }
        return null;//如果程序能执行到该位置,说明找不到学号为studentId的学生,返回null
    }

    /**
     * @param subject 指定科目 例如:数学
     * @return 所有学生指定科目的平均成绩
     */
    public int getAverageScoreBySubject(String subject) {
        int totalScore = 0;//对所有学生的某一门成绩累加求和
        for (Student student : students) {
            int score = getScoreBySubject(student, subject);
            totalScore += score;
        }
        return students.size() == 0 ? 0 : totalScore / students.size();//count=0代表数组中一个学生都没存,总成绩直接0
    }

    /**
     * @param student 学生对象
     * @param subject 科目名称
     * @return 当前学生指定科目的成绩
     */
    private int getScoreBySubject(Student student, String subject) {
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
        }
        return 0; //传递的subject不属于 语文,数学,历史,物理中的其中一种,直接返回0;
    }

    /**
     *
     * @param date 指定日期
     * @return 存储了满足条件的学生信息的集合
     */
    public Collection<Student> getStudentsEnrolledBefore(String date){
        //1.定义一个集合,该集合专门用来存放满足条件的学生信息
        Collection<Student> temps = new ArrayList();

        for (Student student : students) {
            if (student.getEnrollmentDate() != null) {
                if (student.getEnrollmentDate().isBefore(LocalDate.parse(date))
                        ||student.getEnrollmentDate().isEqual(LocalDate.parse(date))) {
                    temps.add(student);
                }
            }
        }
        return temps;
    }

    /**
     *
     * @param date 指定日期
     * @return 存储了满足条件的学生信息的集合
     */
    public Collection getStudentsEnrolledAfter(String date){
        //1.定义一个集合,该集合专门用来存放满足条件的学生信息
        Collection<Student> temps = new ArrayList();

        for (Student student : students) {
            if (student.getEnrollmentDate() != null) {
                if (student.getEnrollmentDate().isAfter(LocalDate.parse(date))
                        ||student.getEnrollmentDate().isEqual(LocalDate.parse(date))) {
                    temps.add(student);
                }
            }
        }

        return temps;
    }

}
