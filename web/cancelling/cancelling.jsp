<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cancelling</title>
    <link rel="stylesheet" href="/RailwayBookingSystem/cancelling/cancelling_style.css">
</head>
<body>
    
    <%@include file="../nav/nav.jsp" %>
    
    <div class="container">
        <h2>Cancelling</h2>
        <form action="/RailwayBookingSystem/CancellingURL" method="post">
            <div class="box">
                <label>Passenger ID :</label>
                <input type="text" name="cancel_id" id="passenger_id">
            </div>
            <div class="box subbox">
                <input type="submit" id="cancel" value="Cancel">
            </div>
        </form>
    </div>
</body>
</html>