import java.sql.*;
import java.nio.file.*;
import java.io.*;
import java.util.Properties;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import jakarta.mail.*;
import jakarta.mail.internet.*;

@WebServlet("/PDFURL")
public class PDF extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/train_booking?useSSL = false&allowPublicKeyRetrieval=true";
        String name = "root";
        String pass = "1234";

        HttpSession session = request.getSession();
        Object seatAttr = session.getAttribute("seat");
        Object emailAttr = session.getAttribute("email");

        if (seatAttr == null || emailAttr == null) {
            response.getWriter().println("Missing session data. Please book a ticket first.");
            return;
        }

        int seat = (int) seatAttr;
        String recipientEmail = (String) emailAttr;

        int id = 0, age = 0 , seat_no = 0;
        String passenger_name = null, berth = null, date = null, departure = null, destination = null, status = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Updated driver
            Connection con = DriverManager.getConnection(url, name, pass);

            PreparedStatement prest = con.prepareStatement("SELECT * FROM booking WHERE seat_no = ?");
            prest.setInt(1, seat);

            ResultSet rs = prest.executeQuery();

            if (!rs.next()) {
                response.getWriter().println("No booking found for seat number: " + seat);
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

        } catch(Exception ex) {
            ex.printStackTrace();
            response.getWriter().println("Database error: " + ex.getMessage());
            return;
        }

        // Paths
        String pdfDirPath = getServletContext().getRealPath("/pdfs");
        Files.createDirectories(Paths.get(pdfDirPath));

        String pdfPath = pdfDirPath + "/" + id + ".pdf";
        String qrPath = pdfDirPath + "/" + id + ".png";
        String qrText = "http://192.168.1.7:8080/RailwayBookingSystem/pdfs/" + id + ".pdf";
        // ezhil's wifi -  String qrText = "http://192.168.1.9:8080/RailwayBookingSystem/pdfs/" + id + ".pdf";

        byte[] qrImageBytes = Qr.generateQRCodeImage(qrText, 300, 300);
        if (qrImageBytes == null) {
            response.getWriter().println("QR code generation failed.");
            return;
        }

        Files.write(Paths.get(qrPath), qrImageBytes);  // Save QR

        try {
            
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);

            // Background
            String imagePath = getServletContext().getRealPath("/Resources/Train Ticket.png");
            if (Files.exists(Paths.get(imagePath))) {
                Image bg = new Image(ImageDataFactory.create(imagePath));
                float pageWidth = PageSize.A4.getWidth();
                float pageHeight = PageSize.A4.getHeight();
                float imageWidth = 500;
                float imageHeight = 250;
                float offsetX = (pageWidth - imageWidth) / 2;
                float offsetY = pageHeight * 0.75f - (imageHeight / 2);

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

                // QR code on top of layout
                Image qrCode = new Image(ImageDataFactory.create(qrImageBytes));
                qrCode.setFixedPosition(offsetX + 420, offsetY - 20);
                qrCode.scaleToFit(100, 100);
                // document.add(qrCode);
            } else {
                document.add(new Paragraph("Ticket background image not found."));
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("PDF generation error: " + e.getMessage());
            return;
        }

        // Email with PDF
        boolean emailSuccess = sendEmailWithAttachment(recipientEmail, Files.readAllBytes(Paths.get(pdfPath)));

        // Set display attributes
        session.setAttribute("display_id", id);
        session.setAttribute("display_name", passenger_name);
        session.setAttribute("display_age", age);
        session.setAttribute("display_email", recipientEmail);
        session.setAttribute("display_berth", berth);
        session.setAttribute("display_seat_no", seat_no);
        session.setAttribute("display_date", date);
        session.setAttribute("display_departure", departure);
        session.setAttribute("display_destination", destination);
        session.setAttribute("display_status", status);

        // Forward to booking success
        RequestDispatcher dispatcher = request.getRequestDispatcher("booking/booking_success.jsp");
        dispatcher.forward(request, response);
    }

    private boolean sendEmailWithAttachment(String recipientEmail, byte[] pdfBytes) {
        String senderEmail = "railwayreservation95@gmail.com";
        String senderPassword = "lbqu yhaa rbyg jdtz";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Your Train Ticket");

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Dear passenger,\n\nPlease find your train ticket attached.\n\nRegards,\nRailway Booking System");

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setFileName("Train_Ticket.pdf");
            attachmentPart.setContent(pdfBytes, "application/pdf");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Email sent successfully.");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
