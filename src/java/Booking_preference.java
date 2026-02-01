import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

@WebServlet("/booking_preferenceURL")
public class Booking_preference extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = "jdbc:mysql://localhost:3306/train_booking?useSSL = false&allowPublicKeyRetrieval=true";
        String name = "root";
        String pass = "1234";

        int upper_count = 0;
        int middle_count = 0;
        int lower_count = 0;
        
        try(Connection con = DriverManager.getConnection(url, name, pass);){
            Statement st = con.createStatement();
            
            ResultSet upper_rs = st.executeQuery("SELECT COUNT(berth) FROM booking WHERE berth = 'Upper' AND status = 'confirmed'");
            if(upper_rs.next()) upper_count = upper_rs.getInt(1);
            
            ResultSet middle_rs = st.executeQuery("SELECT COUNT(berth) FROM booking WHERE berth = 'Middle' AND status = 'confirmed'");
            if(middle_rs.next()) middle_count = middle_rs.getInt(1);
            
            ResultSet lower_rs = st.executeQuery("SELECT COUNT(berth) FROM booking WHERE berth = 'Lower' AND status = 'confirmed'");
            if(lower_rs.next()) lower_count = lower_rs.getInt(1);
            
            HttpSession session = request.getSession();
            session.setAttribute("up", upper_count < 2);
            session.setAttribute("mp", middle_count < 2);
            session.setAttribute("lp", lower_count < 2);
            
            response.sendRedirect("/RailwayBookingSystem/booking/booking_preference.jsp");
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}