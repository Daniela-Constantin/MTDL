package SINGLETON;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCSingleton {
    private static JDBCSingleton jdbc;
    private static Connection connection;
    
    private JDBCSingleton() throws SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/caravanafils", "root", "Alexandra#33");
            
        }catch (ClassNotFoundException ex){
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return connection;
    }
    
    public static JDBCSingleton getInstance() throws SQLException{
        if(jdbc == null){
            jdbc = new JDBCSingleton();
        }
        else if(jdbc.getConnection().isClosed()){
            jdbc = new JDBCSingleton();
        }
        return jdbc;
    }
    

}
