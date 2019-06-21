import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lucas
 */
public class loginService {
    Connection con = null;
    public loginService(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(loginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/popobase","root","");
        } catch (SQLException ex) {
            Logger.getLogger(loginService.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    public boolean login(String user, String pass){
        PreparedStatement statement;
        try {
            statement = con.prepareStatement("SELECT username, password FROM user WHERE username = ? AND password = ?");
            statement.setString(1, user);
            statement.setString(2, pass);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(loginService.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return false;
    }
    
}
