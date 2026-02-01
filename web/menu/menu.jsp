<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Menu</title>
    <link rel="stylesheet" href="/RailwayBookingSystem/menu/menu.css">
    
    <% String username = (String) session.getAttribute("username"); 
        if(username == null || username.trim().isEmpty()){
            session.setAttribute("loginError", "Login is required to access the menu");
            response.sendRedirect("/RailwayBookingSystem/index.jsp");
    }%>

</head>
<body>
    
    <%@include file="../nav/nav.jsp" %>
    
    <div class="container">
        <h2>Booking</h2>
        <div class="box">
            <div class="menu menu1">
                <!-- <img class="option_logo" src="img\book_ticket.png"> -->
                <span>Book Ticket</span>
                <p>Book your Train Tickets here</p>
                <button onclick="window.location.href='/RailwayBookingSystem/booking/booking.jsp'">Book Now</button>
            </div>

            <div class="menu menu2">
                <!-- <img class="option_logo" src="img\cancel_ticket.png"> -->
                <span>Cancel Ticket</span><br>
                <p>Cancel Your Booked Tickets</p>
                <button onclick="window.location.href='/RailwayBookingSystem/cancelling/cancelling.jsp'">Cancel</button>
            </div>

            <div class="menu menu3">
                <span>Booked Tickets</span>
                <p>View your Booked Ticket Details<br>(Confirmed, RAC, Waiting)</p>
                <button onclick="window.location.href='/RailwayBookingSystem/booked_tickets/booking_tickets.jsp'">View</button>
            </div>

            <div class="menu menu4"><span>Booking History</span>
                <p>View Your Booking History</p>
                <button onclick="window.location.href='/RailwayBookingSystem/booking_history/booking_history.jsp'">View</button>
            </div>

            <div class="menu menu5"><span>Download Ticket</span>
                <p>Download Your Booked Ticket</p>
                <button onclick="window.location.href='/RailwayBookingSystem/download/download.jsp'">Download</button>
            </div>
        </div>
    </div>
</body>
</html>
