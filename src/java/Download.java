import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.io.IOException;

@WebServlet("/DownloadURL")
public class Download extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
    {
        int id = Integer.parseInt(request.getParameter("download_id"));
        
        String passenger_name = null, berth = null, date = null, departure = null, destination = null, status = null;
        int age = 0, seat_no = 0;
        
        String url = "jdbc:mysql://localhost:3306/train_booking?useSSL=false&allowPublicKeyRetrieval=true";
        String name = "root";
        String pass = "1234";
        
        HttpSession session = request.getSession();
        
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, name, pass);
            PreparedStatement prest = con.prepareStatement("SELECT * FROM booking WHERE id = ?");
            prest.setInt(1, id);
            
            ResultSet rs = prest.executeQuery();
            
            if (!rs.next()) {
                session.setAttribute("Error", "Enter Valid Ticket ID");
                response.sendRedirect("/RailwayBookingSystem/download/download.jsp");
                return;
            }
            
            id = rs.getInt("id");
            passenger_name = rs.getString("passenger_name");
            age = rs.getInt("age");
            berth = rs.getString("berth");
            seat_no = rs.getInt("seat_no");
            date = rs.getString("date");
            departure = rs.getString("departure");
            destination = rs.getString("destination");
            status = rs.getString("status");
            
            
        }catch(Exception e){
            e.printStackTrace();
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\""+id+".pdf\"");
        
        // Enabling the file (or network stream) to add the elements
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        
        // PdfDocument creates the overall structure of the PDF.
        PdfDocument pdfDoc = new PdfDocument(writer);
        
        //allows you to add content (like paragraphs, images, tables) in a straightforward way, 
        //which then gets laid out and added to the PdfDocument.
        Document document = new Document(pdfDoc, PageSize.A4); // Portrait

        // Load image
        String imagePath = getServletContext().getRealPath("/Resources/Train Ticket.png");
        Image bg = new Image(ImageDataFactory.create(imagePath));

        float pageWidth = PageSize.A4.getWidth();      // 595
        float pageHeight = PageSize.A4.getHeight();    // 842

        float imageWidth = 500;   // Adjust this to fit well in portrait
        float imageHeight = 250;

        // Position: center X, and 25% from top on Y
        float offsetX = (pageWidth - imageWidth) / 2;
        float offsetY = pageHeight * 0.75f - (imageHeight / 2); // 25% from top

        bg.setFixedPosition(offsetX, offsetY);
        bg.scaleToFit(imageWidth, imageHeight);
        document.add(bg);

        // Left half
        document.add(new Paragraph(passenger_name).setFixedPosition(offsetX + 140, offsetY + 146, 250));
        document.add(new Paragraph(departure).setFixedPosition(offsetX + 70, offsetY + 121, 250));
        document.add(new Paragraph(destination).setFixedPosition(offsetX + 53, offsetY + 96, 250));
        document.add(new Paragraph("Economy").setFixedPosition(offsetX + 27, offsetY + 40, 250));
        document.add(new Paragraph(String.valueOf(seat_no)).setFixedPosition(offsetX + 157, offsetY + 40, 250));
        document.add(new Paragraph(date).setFixedPosition(offsetX + 277, offsetY + 40, 250));
        document.add(new Paragraph(String.valueOf(id)).setFixedPosition(offsetX + 250, offsetY + 143, 250));
        document.add(new Paragraph(berth).setFixedPosition(offsetX + 300, offsetY + 121, 250));

        // Right half
        document.add(new Paragraph(passenger_name).setFixedPosition(offsetX + 409, offsetY + 141, 250));
        document.add(new Paragraph(departure).setFixedPosition(offsetX + 408, offsetY + 116, 250));
        document.add(new Paragraph(destination).setFixedPosition(offsetX + 392, offsetY + 89, 250));
        document.add(new Paragraph("Economy").setFixedPosition(offsetX + 408, offsetY + 60, 250));
        document.add(new Paragraph(String.valueOf(seat_no)).setFixedPosition(offsetX + 365, offsetY + 15, 250));
        document.add(new Paragraph(date).setFixedPosition(offsetX + 424, offsetY + 15, 250));

        document.close();
    }
}
