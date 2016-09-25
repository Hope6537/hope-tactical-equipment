package org.hope6537.dataobject;

/**
 * 实体DO
 * Created by hope6537 by Code Generator
 */
public class ParentDo extends BasicDo {

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
     * 地址
     */
    private String address;

    /**
     * 邮件
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    public ParentDo() {

    }

    public ParentDo(String name, String sex, String tel, String address, String email, String password) {

        this.name = name;
        this.sex = sex;
        this.tel = tel;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    