package org.hope6537.note.design.flyweight.exam;

/**
 * 我们要共享的目标对象——报考信息
 */
public class SignInfo {


    private String id;

    private String location;

    private String subject;

    private String postAddress;

    @Override
    public String toString() {
        return "SignInfo{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", subject='" + subject + '\'' +
                ", postAddress='" + postAddress + '\'' +
                '}';
    }

    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
