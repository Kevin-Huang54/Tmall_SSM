package pojo;

public class PropertyValue {
    private int id;
    private int ptid; //外键property的id
    private Property property;
    private int pid; //外键product的id
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPtid() {
        return ptid;
    }

    public void setPtid(int ptid) {
        this.ptid = ptid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
