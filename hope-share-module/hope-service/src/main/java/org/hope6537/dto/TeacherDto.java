package org.hope6537.dto;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class TeacherDto extends BasicDto {

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 电话
     */
    private String tel;

    /**
     * 描述
     */
    private String des;

    /**
     * 教师等级
     */
    private String level;

    /**
     * 邮件
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    public TeacherDto() {

    }

    public TeacherDto(String name, String sex, String tel, String des, String level, String email, String password) {

        this.name = name;
        this.sex = sex;
        this.tel = tel;
        this.des = des;
        this.level = level;
        this.email = email;
        this.password = password;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
    