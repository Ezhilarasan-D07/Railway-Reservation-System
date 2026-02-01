package Mail;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Random;
import java.io.IOException;

@WebServlet("/OTPverifyURL")
public class OTPverify extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
        {
        HttpSession session = request.getSession();
        
        session.setAttribute("temp_username", request.getParameter("username"));
        session.setAttribute("temp_password", request.getParameter("password"));
        session.setAttribute("temp_email", request.getParameter("email").replaceAll("\\s+", "").trim());
        
        String username = (String) session.getAttribute("temp_username");
        String password = (String) session.getAttribute("temp_password");
        String email = (String) session.getAttribute("temp_email");
        
        String username_valid = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{5,}$";
        String password_valid = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{6,}$";
        String email_valid = "^\\S+@\\S+\\.\\S+$";
        
        if(!username.matches(username_valid) || !password.matches(password_valid) || !email.matches(email_valid)){
            request.getSession().setAttribute("Error", "Enter valid details");
            response.sendRedirect("/RailwayBookingSystem/registration/registration.jsp");
            return;
        }
        
        String OTP = generateOTP();
        session.setAttribute("generated_otp", OTP);
        boolean mail = MailReq.sendEmail(email, OTP);
        
        if(mail){
            response.sendRedirect("/RailwayBookingSystem/registration/OTPverify.jsp");
        }else{
            response.sendRedirect("/RailwayBookingSystem/registration/registration.jsp");
            request.getSession().setAttribute("Error", "Enter valid email");
        }
    }
    
    private static String generateOTP(){
            Random random = new Random();
            int otp = 100000 +  random.nextInt(900000); // 6-digit OTP
            return String.valueOf(otp);
    }
}
