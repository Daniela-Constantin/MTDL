package Domaine;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Visits {
    private static int nr = 2;
    
    private int code;
    private String highschool;
    private String date;
    private String time;
    private String hs_representative;
    private List<Users> students = new ArrayList<>(); 
    private Users prof;

    public Visits() {
    }

    public Visits(String highschool, String date, String time, String hs_representative, Users prof) {
        this.highschool = highschool;
        this.date = date;
        this.time = time;
        this.hs_representative = hs_representative;
        this.prof = prof;
    }
    
    public Visits(int code, String highschool, String date, String time) {
        this.code = code;
        this.highschool = highschool;
        this.date = date;
        this.time = time;
    }
    
    public Visits(String highschool, String date, String time) {
        this.code = nr++;
        this.highschool = highschool;
        this.date = date;
        this.time = time;
    }
    
    public void addStudent(Users s){
        students.add(s);
    }

    public int getCode() {
        return code;
    }

    public String getHighschool() {
        return highschool;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Visits{" + "code=" + code + ", highschool=" + highschool + ", date=" + date + ", time=" + time + ", hs_representative=" + hs_representative + ", students=" + students + ", prof=" + prof + '}';
    }
    
    
}
