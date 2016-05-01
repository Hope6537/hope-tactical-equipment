package org.hope6537.note.design.flyweight.multi;

/**
 * 使用外部状态类来表示
 */
public class ExtrinsicState {

    private String subject;

    private String location;


    public ExtrinsicState(String subject, String location) {
        this.subject = subject;
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExtrinsicState that = (ExtrinsicState) o;

        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = subject != null ? subject.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
