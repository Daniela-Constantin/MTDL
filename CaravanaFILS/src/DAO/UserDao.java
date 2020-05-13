package DAO;

import Domaine.Users;
import Domaine.Visits;
import SINGLETON.JDBCSingleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class UserDao implements Dao<Users>{
    
    Connection conn;
    JDBCSingleton jdbc = JDBCSingleton.getInstance();

    private List<Users> users = new ArrayList<>();
    private List<Visits> visits = new ArrayList<>();
    
    public UserDao() throws SQLException {
         
        conn = jdbc.getConnection();
    }

    
    @Override
    public Optional<Users> get(int id) {
        return Optional.ofNullable(users.get(id));
    }
    
    @Override
    public Users getByUsr(String usr) {
        String sql = "select * from users where username = ?";
        Users u = null;
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,usr);
            ResultSet rset = pstmt.executeQuery();
            if(rset.next())
            {
                u = new Users(rset.getInt("id"),rset.getString("account_type"), rset.getString("first_name"), rset.getString("last_name"), rset.getString("email"), rset.getString("tel"), rset.getString("username"), rset.getString("password"));
            }
            
            rset.close();
            pstmt.close();
            //conn.close();
        }catch (SQLException ex){
           Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex); 
        }
        return u;
    }
    
    public Users getById(int id) {
        String sql = "select * from users where ID = ?";
        Users u = null;
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet rset = pstmt.executeQuery();
            if(rset.next())
            {
                u = new Users(id,rset.getString("account_type"), rset.getString("first_name"), rset.getString("last_name"), rset.getString("email"), rset.getString("tel"), rset.getString("username"), rset.getString("password"));
            }
            
            rset.close();
            pstmt.close();
            //conn.close();
        }catch (SQLException ex){
           Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex); 
        }
        return u;
    }
    
    public Visits getVisitByCode(int code) {
        String sql = "select * from visits where code = ?";
        Visits v = null;
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,code);
            ResultSet rset = pstmt.executeQuery();
            if(rset.next())
            {
                v = new Visits(code,rset.getString("highschool"), rset.getString("date"), rset.getString("time"));
            }
            
            rset.close();
            pstmt.close();
            //conn.close();
        }catch (SQLException ex){
           Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex); 
        }
        return v;
    }

    @Override
    public List<Users> getAll() {
        List<Users> users1 = new ArrayList<>();
        try{
            Statement stmt = conn.createStatement();
            String strSelect = "select * from users";
            ResultSet rset = stmt.executeQuery(strSelect);
            
            while(rset.next())
            {
                Users u = new Users(rset.getString("account_type"), rset.getString("first_name"), rset.getString("last_name"), rset.getString("email"), rset.getString("tel"), rset.getString("username"), rset.getString("password"));
                users1.add(u);
            }
            
            rset.close();
            stmt.close();
            //conn.close();
        }catch (SQLException ex){
           Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex); 
        }
        return users1;
    }
    
    @Override
    public void getAllStudentEnroll() {
        List<Users> users1 = new ArrayList<>();
        List<Visits> visits1 = new ArrayList<>();
        try{
            Statement stmt = conn.createStatement();
            String strSelect = "select * from stud_visit";
            ResultSet rset = stmt.executeQuery(strSelect);
            
            while(rset.next())
            {
                int c = rset.getInt("code_visit");
                int i = rset.getInt("id_stud");
                Users u = this.getById(i);
                Visits v = this.getVisitByCode(c);
                users1.add(u);
                visits1.add(v);
            }
            
            rset.close();
            stmt.close();
            
            for(int nr=0; nr<users1.size(); nr++){
                System.out.println(users1.get(nr).toString());
                System.out.println(visits1.get(nr).toString());
                System.out.println();
            }
            //conn.close();
        }catch (SQLException ex){
           Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }

    @Override
    public void save(Users u) {
        try{
            PreparedStatement stmt = conn.prepareStatement("insert into users values(?,?,?,?,?,?,?,?)");
            stmt.setInt(1, u.getId());
            stmt.setString(2, u.getAccount_type());
            stmt.setString(3, u.getFirst_name());
            stmt.setString(4, u.getLast_name());
            stmt.setString(5, u.getEmail());
            stmt.setString(6, u.getPhone());
            stmt.setString(7, u.getUsr());
            stmt.setString(8, u.getPass());
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Account created successfully!", "Successful Sign-up", JOptionPane.PLAIN_MESSAGE);
            stmt.close();
            //conn.close();
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Invalid registration", "Unsuccessful Sign-up", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }
    
    @Override
    public void saveVisit(Visits v){
        try{
            PreparedStatement stmt = conn.prepareStatement("insert into visits values(?,?,?,?)");
            stmt.setInt(1, v.getCode());
            stmt.setString(2, v.getHighschool());
            stmt.setString(3, v.getDate());
            stmt.setString(4, v.getTime());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Visit created successfully!", "Success", JOptionPane.PLAIN_MESSAGE);
            stmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Invalid request", "Not able to create visit", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e); 
        }
    }

    @Override
    public void updatePassword(String u, String pass) {
        String query = "update users set password = ? where username = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, pass);
            stmt.setString(2, u);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Password changed successfully!", "Edit", JOptionPane.PLAIN_MESSAGE);
            stmt.close();
            //conn.close();
        }catch(SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }
    
    @Override
    public void updateFirstName(String u, String first) {
        String query = "update users set first_name = ? where username = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, first);
            stmt.setString(2, u);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "First name changed successfully!", "Edit", JOptionPane.PLAIN_MESSAGE);
            stmt.close();
            //conn.close();
        }catch(SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }
    
    @Override
    public void updateLastName(String u, String last) {
        String query = "update users set last_name = ? where username = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, last);
            stmt.setString(2, u);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Last name changed successfully!", "Edit", JOptionPane.PLAIN_MESSAGE);
            stmt.close();
            //conn.close();
        }catch(SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }
    
    @Override
    public void updateUsername(Users u, String usr) {
        String query = "update users set username = ? where username = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, usr);
            stmt.setString(2, u.getUsr());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Username changed successfully!", "Edit", JOptionPane.PLAIN_MESSAGE);
            stmt.close();
            //conn.close();
        }catch(SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }
    
    @Override
    public void updateEmail(String u, String em) {
        String query = "update users set email = ? where username = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, em);
            stmt.setString(2, u);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Email changed successfully!", "Edit", JOptionPane.PLAIN_MESSAGE);
            stmt.close();
            //conn.close();
        }catch(SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }
    
    @Override
    public void updatePhone(String u, String tel) {
        String query = "update users set tel = ? where username = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, tel);
            stmt.setString(2, u);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Telephone changed successfully!", "Edit", JOptionPane.PLAIN_MESSAGE);
            stmt.close();
            //conn.close();
        }catch(SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }

    @Override
    public void delete(String usr) {
        String query = "delete from users where username = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,usr);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "You are no longer a user in our database!", "Account deletion", JOptionPane.PLAIN_MESSAGE);
            stmt.close();
            //conn.close();
        }catch(SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }
    
    @Override
    public int get_login(String usr, String pass){
        String sql = "select * from users where username = ? and password = ?";
        int ok = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,usr);
            pstmt.setString(2,pass);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                ok = 1;
                JOptionPane.showMessageDialog(null, "Welcome " + usr, "Successful Login", JOptionPane.PLAIN_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(null, "Invalid Username/Password!", "Unsuccessful Login", JOptionPane.ERROR_MESSAGE);
            }
            pstmt.close();
            //conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ok;
    }
    
    @Override
    public void enrollVisit_stud(int code, int id){
        String sql = "insert into stud_visit values(?,?)";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, code);
            stmt.setInt(2, id);
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Student enrolled successfully!", "Successful enroll", JOptionPane.PLAIN_MESSAGE);
            stmt.close();
            //conn.close();
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Invalid request", "Unsuccessful enroll", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }
    
    @Override
        public void enrollVisit_prof(int code, int id){
        String sql = "insert into prof_visit values(?,?)";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, code);
            stmt.setInt(2, id);
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Professor enrolled successfully!", "Successful enroll", JOptionPane.PLAIN_MESSAGE);
            stmt.close();
            //conn.close();
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Invalid request", "Unsuccessful enroll", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }
    
}
