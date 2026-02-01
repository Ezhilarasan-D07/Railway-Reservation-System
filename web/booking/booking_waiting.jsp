<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Waiting</title>
        <link rel="stylesheet" href="booking_RAC_style.css">
    </head>
    <body>

        <%@include file="../nav/nav.jsp" %>

        <div class="container">
            <h2>Waiting Ticket Booking</h2>
            <p id="description">All confirmed berths are currently full.<br>Would you like to continue with a <b>waiting</b> ticket?</p>
            <form action="/RailwayBookingSystem/BookingURL" method="post">
                
                <div class="box btn">
                    <button type="submit" id="button" name="selected_status" value="waiting">Yes</button>
                </div>

                <div class="box btn">
                    <button type="button" id="button" onclick="window.location.href='/RailwayBookingSystem/booking/booking.jsp'">No</button>
                </div>
            </form>
        </div>

    </body>
</html>
