package Mail;

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

@WebServlet("/RegistrationURL")
public class Registration extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        HttpSession session = request.getSession();
        
        String username = (String) session.getAttribute("temp_username");
        String password = (String) session.getAttribute("temp_password");
        String email = (String) session.getAttribute("temp_email");
        String generated_otp = (String) session.getAttribute("generated_otp");
        
        String a = request.getParameter("a");
        String b = request.getParameter("b");
        String c = request.getParameter("c");
        String d = request.getParameter("d");
        String e = request.getParameter("e");
        String f = request.getParameter("f");
        
        String OTP = a + b + c + d + e + f;
        
        String username_valid = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{5,}$";
        String password_valid = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{6,}$";
        String email_valid = "^\\S+@\\S+\\.\\S+$";
        
        if(!username.matches(username_valid) || !password.matches(password_valid) || !email.matches(email_valid)){
            request.getSession().setAttribute("Error", "Enter valid details");
            response.sendRedirect("/RailwayBookingSystem/registration/registration.jsp");
            return;
        }
        
        if(OTP == null || !OTP.equals(generated_otp)){
            request.getSession().setAttribute("Error", "Enter valid OTP");
            response.sendRedirect("/RailwayBookingSystem/registration/OTPverify.jsp");
            return;
        }
        
        String url = "jdbc:mysql://localhost:3306/auth_database?useSSL=false&allowPublicKeyRetrieval=true";
        String name = "root";
        String pass = "1234";
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, name, pass);
            
            PreparedStatement checkUser = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkUser.setString(1, username);
            
            ResultSet rs = checkUser.executeQuery();

            if(rs.next()){
                session.setAttribute("Error", "Username already exist");
                response.sendRedirect("/RailwayBookingSystem/registration/registration.jsp");
                return;
            }
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            PreparedStatement prest = con.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            prest.setString(1, username);
            prest.setString(2, hashedPassword);

            int condition = prest.executeUpdate();
            
            if(condition != 0){
                session.setAttribute("username", username);
                response.sendRedirect("/RailwayBookingSystem/menu/menu.jsp");
                System.out.print("Registered");
            }
            else{
                System.out.print("Not registered");
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
