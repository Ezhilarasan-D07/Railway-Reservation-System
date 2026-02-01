import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/CancellingURL")
public class Cancelling extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cancel_id = request.getParameter("cancel_id");
        int seat_no = 0;
        String berthOption = null;
        String status = null;

        String url = "jdbc:mysql://localhost:3306/train_booking?useSSL = false&allowPublicKeyRetrieval=true";
        String name = "root";
        String pass = "1234";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, name, pass);
            PreparedStatement cancel_prest = con.prepareStatement("SELECT * FROM booking WHERE id = ?");
            cancel_prest.setString(1, cancel_id);

            ResultSet rs = cancel_prest.executeQuery();
            while(rs.next()){
                seat_no = rs.getInt("seat_no");
                berthOption = rs.getString("berth");
                status = rs.getString("status");

                PreparedStatement delete_prest = con.prepareStatement("UPDATE booking SET status = 'cancelled' WHERE id = ?");
                delete_prest.setString(1, cancel_id);
                delete_prest.executeUpdate();

                if(status.equals("confirmed")){
                    Statement st = con.createStatement();
                    ResultSet RACrs = st.executeQuery("SELECT id FROM booking WHERE status = 'RAC' ORDER BY id ASC LIMIT 1");

                    if(RACrs.next()){
                        int racId = RACrs.getInt("id");

                        PreparedStatement racUpdate = con.prepareStatement(
                            "UPDATE booking SET status = ?, seat_no = ?, berth = ? WHERE id = ?"
                        );
                        racUpdate.setString(1, "confirmed");
                        racUpdate.setInt(2, seat_no);           // reuse Tamil's seat
                        racUpdate.setString(3, berthOption);    // reuse Tamil's berth
                        racUpdate.setInt(4, racId);
                        int RAC_condition = racUpdate.executeUpdate();

                        System.out.print("RAC updated");

                        if(RAC_condition != 0){
                            // promote one waiting to RAC
                            st.executeUpdate("UPDATE booking SET status = 'RAC' WHERE status = 'waiting' ORDER BY id ASC LIMIT 1");
                            System.out.print("waiting updated");
                        }
                    }
                }
                else if(status.equals("RAC")){
                    Statement st = con.createStatement();
                    ResultSet waiting_rs = st.executeQuery("SELECT id FROM booking WHERE status = 'waiting' ORDER BY id ASC LIMIT 1");

                    if(waiting_rs.next()){
                        int racId = waiting_rs.getInt("id");

                        PreparedStatement waitUpdate = con.prepareStatement(
                            "UPDATE booking SET status = ?, seat_no = ?, berth = ? WHERE id = ?"
                        );
                        waitUpdate.setString(1, "RAC");
                        waitUpdate.setInt(2, seat_no);           // reuse Tamil's seat
                        waitUpdate.setString(3, berthOption);    // reuse Tamil's berth
                        waitUpdate.setInt(4, racId);
                        int waiting_condition = waitUpdate.executeUpdate();
                    }
                }
            }
            System.out.println(seat_no +"\n"+ status);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}