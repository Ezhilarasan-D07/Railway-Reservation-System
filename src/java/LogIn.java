import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/LogInURL")
public class LogIn extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
//        String url = "jdbc:mysql://localhost:3306/auth_database?useSSL=false";
        String url = "jdbc:mysql://localhost:3306/auth_database?useSSL=false&allowPublicKeyRetrieval=true";
        String name = "root";
        String pass = "1234";
        System.out.print("login url");
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.print("driver");
            Connection con = DriverManager.getConnection(url, name, pass);
            PreparedStatement prest = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            prest.setString(1, username);
            System.out.print("table");
            ResultSet rs = prest.executeQuery();
            
            HttpSession session = request.getSession();
            if(rs.next()){
                String hashedpassword = rs.getString("password");
                
                if(BCrypt.checkpw(password, hashedpassword)){
                    
                    session.setAttribute("username", username);
                    response.sendRedirect("/RailwayBookingSystem/menu/menu.jsp");
                    System.out.print("login successful");
                }else{
                    session.setAttribute("loginError", "Invalid Username or Password");
                    response.sendRedirect("/RailwayBookingSystem/index.jsp");
                    
                    
                }
            }else{
                session.setAttribute("loginError", "Username not found");
                response.sendRedirect("/RailwayBookingSystem/index.jsp");
                    
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
