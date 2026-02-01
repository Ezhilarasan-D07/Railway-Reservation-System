import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Date;

import java.io.IOException;

@WebServlet("/BookingURL")
public class Booking extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        HttpSession session = request.getSession();
        
        String passenger_name = request.getParameter("passenger_name") != null && !request.getParameter("passenger_name").trim().isEmpty() 
                ? request.getParameter("passenger_name") 
                : (String) session.getAttribute("prefer_name") ;
        
        String agestr = request.getParameter("age") ;
        Integer age = agestr != null && !agestr.trim().isEmpty() 
                ? Integer.valueOf(agestr)
                : (Integer) session.getAttribute("prefer_age");
        
        String email = request.getParameter("email") != null && !request.getParameter("email").trim().isEmpty() 
                ? request.getParameter("email") 
                : (String) session.getAttribute("prefer_email") ;
        
        String berth;
            if(request.getParameter("berth") != null && !request.getParameter("berth").trim().isEmpty()){
                berth = request.getParameter("berth");
            }else if(request.getParameter("selected_berth") != null && !request.getParameter("selected_berth").trim().isEmpty()){
                berth = request.getParameter("selected_berth");
            }else{
                berth = (String) session.getAttribute("prefer_berth");
            }
 
        String date = request.getParameter("date");
        Date sqldate = date != null && !date.trim().isEmpty() 
                ? Date.valueOf(date) 
                : (Date) session.getAttribute("prefer_date");
        
        String from = request.getParameter("from") != null && !request.getParameter("from").trim().isEmpty() 
                ? request.getParameter("from") 
                : (String) session.getAttribute("prefer_from");
        
        String to = request.getParameter("to") != null && !request.getParameter("to").trim().isEmpty() 
                ? request.getParameter("to")
                : (String) session.getAttribute("prefer_to");
        
        String status = request.getParameter("selected_status") != null && !request.getParameter("selected_status").trim().isEmpty()
                ? request.getParameter("selected_status")
                : null;
        
        int seat_no = 0;
        int Upper_count = 0;
        int Middle_count = 0;
        int Lower_count =  0;
        
        String url = "jdbc:mysql://localhost:3306/train_booking?useSSL=false&allowPublicKeyRetrieval=true";
        String name = "root";
        String pass = "1234";
        
        String passenger_name_valid = "^[A-Z](?=.*[a-z])[A-Za-z\\d]{4,}$";
        
        if (passenger_name == null || age == null || email == null || berth == null || sqldate == null || from == null || to == null) {
            session.setAttribute("Error", "Some required fields are missing.");
            response.sendRedirect("/RailwayBookingSystem/booking/booking.jsp");
            return;
        }
        
        if(!passenger_name.matches(passenger_name_valid) || !(age >1 && age <=100)){
            request.getSession().setAttribute("Error", "Enter valid details");
            response.sendRedirect("/RailwayBookingSystem/booking/booking.jsp");
            return;
        }
        
        try(Connection con = DriverManager.getConnection(url, name, pass);
            Statement st = con.createStatement()) {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            session.setAttribute("prefer_name", passenger_name);
            session.setAttribute("prefer_age", age);
            session.setAttribute("prefer_email", email);
            session.setAttribute("prefer_berth", berth);
            session.setAttribute("prefer_date", sqldate);
            session.setAttribute("prefer_from", from);
            session.setAttribute("prefer_to", to);
            
            ResultSet rs = null;
            int cl = 0, rl = 0, wl = 0;

            ResultSet confirm_limit = st.executeQuery("SELECT COUNT(status) FROM booking WHERE status='confirmed'");
            if(confirm_limit.next()){
                cl = confirm_limit.getInt(1);
            }
            
            ResultSet RAC_limit = st.executeQuery("SELECT COUNT(status) FROM booking WHERE status='RAC'");
            if(RAC_limit.next()){
                rl = RAC_limit.getInt(1);
            }

            ResultSet waiting_limit = st.executeQuery("SELECT COUNT(status) FROM booking WHERE status='waiting'");
            if(waiting_limit.next()){
                wl = waiting_limit.getInt(1);
            }
            
            if(cl < 6){
                
                switch(berth)
                {
                    case "Upper" -> {
                        rs = st.executeQuery("SELECT COUNT(berth) FROM booking WHERE berth = 'Upper' AND status = 'confirmed'");

                        if(rs.next()) Upper_count = rs.getInt(1);

                        switch(Upper_count){
                            case 0 -> seat_no = 1;
                            case 1 -> seat_no = 4;
                            default -> {
                                System.out.print("Upper limit exceeded");
                                response.sendRedirect("/RailwayBookingSystem/booking_preferenceURL");
                                return;
                            }
                        }
                    }
                    case "Middle" -> {
                        rs = st.executeQuery("SELECT COUNT(berth) FROM booking WHERE berth = 'Middle' AND status = 'confirmed'");

                        if(rs.next()) Middle_count = rs.getInt(1);

                        switch(Middle_count){
                            case 0 -> seat_no = 2;
                            case 1 -> seat_no = 5;
                            default -> {
                                System.out.print("Middle limit exceeded");
                                response.sendRedirect("/RailwayBookingSystem/booking_preferenceURL");
                                return;
                            }
                        }
                    }
                    case "Lower" -> {
                        rs = st.executeQuery("SELECT COUNT(berth) FROM booking WHERE berth = 'Lower' AND status = 'confirmed'");

                        if(rs.next()) Lower_count = rs.getInt(1);

                        switch(Lower_count){
                            case 0 -> seat_no = 3;
                            case 1 -> seat_no = 6;
                            default -> {
                                System.out.print("Lower limit exceeded");
                                response.sendRedirect("/RailwayBookingSystem/booking_preferenceURL");
                                return;
                            }
                        }
                    }
                    
                }
                status = "confirmed";
                
            }   else if(rl < 1){
                if(status == null){
                    response.sendRedirect("/RailwayBookingSystem/booking/booking_RAC.jsp");
                    return;
                }
                seat_no = 7;
                
            }   else if(rl < 2){
                if(status == null){
                    response.sendRedirect("/RailwayBookingSystem/booking/booking_RAC.jsp");
                    return;
                }else{
                    seat_no = 8;
                }
                
            }   else if(wl <1){
                if(status == null){
                    response.sendRedirect("/RailwayBookingSystem/booking/booking_waiting.jsp");
                    return;
                }else{
                    seat_no = 9;
                }
            }else{
                response.sendRedirect("/RailwayBookingSystem/booking/booking_failed.jsp");
                return;
            }
            
            PreparedStatement prest = con.prepareStatement("INSERT INTO booking (passenger_name, age, email, berth, seat_no, date, departure, destination, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            prest.setString(1, passenger_name);
            prest.setInt(2, age);
            prest.setString(3, email);
            prest.setString(4, berth);
            prest.setInt(5, seat_no);
            prest.setDate(6, sqldate);
            prest.setString(7, from);
            prest.setString(8, to);
            prest.setString(9, status);
            
            int condition = prest.executeUpdate();
            
            if(condition != 0){
                System.out.print("Executed");
                
                session.setAttribute("seat", seat_no);
                session.setAttribute("email", email);
                
                response.sendRedirect("/RailwayBookingSystem/PDFURL");
                
                session.removeAttribute("prefer_name");
                session.removeAttribute("prefer_age");
                session.removeAttribute("prefer_email");
                session.removeAttribute("prefer_berth");
                session.removeAttribute("prefer_date");
                session.removeAttribute("prefer_from");
                session.removeAttribute("prefer_to");
                
            }else{
                System.out.print("Not Executed");
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
