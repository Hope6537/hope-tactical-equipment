package org.hope6537.note.design.flyweight.exam;

/**
 * 带对象池的报考信息
 */
public class SignInfo4Pool extends SignInfo {

    /**
     * 定义一个对象池提取的KEY值
     */
    private String key;

    /**
     * 构造函数获得相同标志
     */
    public SignInfo4Pool(String key) {
        this.key = key;
        String[] strings = key.split(",");
        super.setLocation(strings[1].split(":")[1]);
        super.setSubject(strings[0].split(":")[1]);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
