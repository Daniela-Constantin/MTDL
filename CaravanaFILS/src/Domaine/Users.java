package Domaine;


public class Users {
    private static int count = 5;
    
    private int id;
    private String account_type;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String usr;
    private String pass;

    public Users() {
    }

    public Users(String account_type, String first_name, String last_name, String email, String phone, String usr, String pass) {
        this.id = ++count;
        this.account_type = account_type;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.usr = usr;
        this.pass = pass;
    }
    
    public Users(int id, String account_type, String first_name, String last_name, String email, String phone, String usr, String pass) {
        this.id = id;
        this.account_type = account_type;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.usr = usr;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public String getAccount_type() {
        return account_type;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsr() {
        return usr;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public String toString() {
        return "Users{" + "count=" + count + ", id=" + id + ", account_type=" + account_type + ", first_name=" + first_name + ", last_name=" + last_name + ", email=" + email + ", phone=" + phone + '}';
    }





}




 

