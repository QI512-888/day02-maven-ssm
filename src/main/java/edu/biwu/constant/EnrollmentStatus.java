package edu.biwu.constant;

import java.util.HashMap;
import java.util.Map;

/*创建一个代表学生学籍状态的枚举(包含在读,休学,毕业三种状态)
该枚举能够根据枚举的字符串名称获取枚举常量
例如: 根据"休学"获取到休学枚举常量*/
/*学籍状态枚举*/
public enum EnrollmentStatus {
    ENROLLED("在读"),
    SUSPENDED("休学"),
    GRADUATED("毕业");

    /*描述学籍状态*/
    private String description;

    /*将来构造key=value的键值对存入map,key代表学籍中文状态,value代表对应的常量*/
    private static Map<String, EnrollmentStatus> map = new HashMap<>();

    /*利用静态代码块,将map进行初始化,随着枚举类的加载而执行*/
    static{
        for (EnrollmentStatus es : EnrollmentStatus.values()) {
            map.put(es.toString(), es);
        }
    }

    /**
     * 对外提供方法,根据学籍的中文名称获取对应的枚举常量
     * @param desc 枚举常量的描述
     * @return 对应的枚举常量
     */
    public static EnrollmentStatus getEnumByDesc(String desc) {
        return map.get(desc);
    }



    private EnrollmentStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
