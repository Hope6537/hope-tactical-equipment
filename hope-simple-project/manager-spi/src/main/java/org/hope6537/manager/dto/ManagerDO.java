package org.hope6537.manager.dto;

import org.hope6537.basic.dataobject.BasicDO;

/**
 * Created by hope6537 on 15/11/22.
 * Any Question sent to hope6537@qq.com
 */
public class ManagerDO extends BasicDO {

    private String id;

    private String name;

    private String account;

    private String password;

    public ManagerDO() {
    }

    public ManagerDO(String id) {
        this.id = id;
    }

    public ManagerDO(String name, String account, String password, String status) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return super.toString() + "ManagerDO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public String commonId() {
        return this.id;
    }
}
