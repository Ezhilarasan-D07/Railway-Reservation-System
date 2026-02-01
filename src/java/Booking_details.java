import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/booking_detailsURL")
public class Booking_details extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        
        String url = "jdbc:mysql://localhost:3306/train_booking?useSSL = false&allowPublicKeyRetrieval=true";
        String name = "root";
        String pass = "1234";
        
        try{
            Connection con = DriverManager.getConnection(url, name, pass);
            PreparedStatement prest = con.prepareStatement("SELECT * FROM booking WHERE seat_no = ?");
            
            HttpSession session = request.getSession();
            int seat = (int) session.getAttribute("seat");
            prest.setInt(1, seat);
            
            ResultSet rs = prest.executeQuery();
            
            if(rs.next()){
                
                session.setAttribute("display_id", rs.getInt("id"));
                session.setAttribute("display_name", rs.getString("passenger_name"));
                session.setAttribute("display_age", rs.getInt("age"));
                session.setAttribute("display_email", rs.getString("email"));
                session.setAttribute("display_berth", rs.getString("berth"));
                session.setAttribute("display_seat_no", rs.getInt("seat_no"));
                session.setAttribute("display_date", rs.getString("date"));
                session.setAttribute("display_departure", rs.getString("departure"));
                session.setAttribute("display_destination", rs.getString("destination"));
                session.setAttribute("display_status", rs.getString("status"));
                
            }   response.sendRedirect("/RailwayBookingSystem/booking_details/booking_details.jsp");
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
