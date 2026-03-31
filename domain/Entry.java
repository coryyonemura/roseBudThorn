package domain;

import java.io.Serializable;
import java.util.Date;

public class Entry implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private String rose;
    private String bud;
    private String thorn;
    private Date date;

    public Entry(int id, int userId, String rose, String bud, String thorn, Date date) {
        this.id = id;
        this.userId = userId;
        this.rose = rose;
        this.bud = bud;
        this.thorn = thorn;
        this.date = date;
    }

    public int getId() {
        return this.id;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getRose() {
        return this.rose;
    }
    
    public String getBud() {
        return this.bud;
    }

    public String getThorn() {
        return this.thorn;
    }

    public Date getDate() {
        return this.date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRose(String rose) {
        this.rose = rose;
    }

    public void setBud(String bud) {
        this.bud = bud;
    }

    public void setThorn(String thorn) {
        this.thorn = thorn;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
