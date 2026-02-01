<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking</title>
    <link rel="stylesheet" href="/RailwayBookingSystem/booking/booking_style.css">
    <script src="/RailwayBookingSystem/booking/booking_script.js"></script>
    
    <% String username = (String) session.getAttribute("username"); 
        if(username == null || username.trim().isEmpty()){
            session.setAttribute("Error", "Login is required to access the Booking");
            response.sendRedirect("/RailwayBookingSystem/index.jsp");
    }%>
    
</head>

<body>
    <%@include file="../nav/nav.jsp" %>
    
    <div class="container">
        <h2>Booking</h2>
        <form action="/RailwayBookingSystem/BookingURL" method="post" onsubmit="return validation()">
            <div class="box">
                <label>Passenger's Name :</label>
                <input type="text" id="passenger_name" class="textbox" name="passenger_name" onkeyup="conditions('passenger_name', 'passenger_error')">
                <p class="error" id="passenger_error"></p>
            </div>
            
            <div class="box">
                <label>Age :</label>
                <input type="number" min="1" max="150" id="age" class="textbox" name="age" onkeyup="conditions('age', 'ageerror')">
                <p class="error" id="ageerror"></p>
            </div>
            
            <div class="box">
                <label>Email :</label>
                <input type="email" id="email" class="textbox" name="email" onkeyup="conditions('email', 'emailerror')">
                <p class="error" id="emailerror"></p>
            </div>
            
            <div class="dateberthbox">
                <div id="berthbox">
                    <label>Berth<br> Preference :</label>
                        <select class="berth" id="berth" name="berth" onchange="conditions('berth', 'bertherror')">
                            <option value="">--Choose--</option>
                            <option value="Upper">Upper</option>
                            <option value="Middle">Middle</option>
                            <option value="Lower">Lower</option>
                        </select>
                        <p class="error" id="bertherror"></p>
                </div>
                
                <div id="datebox">
                    <label><br>Travel Date :</label>
                    <input type="date" class="travel_date" id="travel_date" name="date" onchange="conditions('travel_date', 'travel_dateerror')">
                    <p class="error" id="travel_dateerror"></p>
                </div>
            </div>
            
            <div class="fromtobox">
                <div id="frombox">
                    <label>Departure :</label>
                    <select class="from" id="from" name="from" onchange="conditions('from', 'fromerror')">
                        <option value="">--Location--</option>
                        <option value="Chennai">Chennai</option>
                        <option value="Madurai">Madurai</option>
                        <option value="Coimbatore">Coimbatore</option>
                        <option value="Ooty">Ooty</option>
                    </select>
                    <p class="error" id="fromerror"></p>
                </div>
                
                <div id="tobox">
                    <label>Destination :</label>
                    <select class="to" id="to" name="to" onchange="conditions('to', 'toerror')">
                        <option value="">--Location--</option>
                        <option value="Chennai">Chennai</option>
                        <option value="Madurai">Madurai</option>
                        <option value="Coimbatore">Coimbatore</option>
                        <option value="Ooty">Ooty</option>
                    </select>
                    <p class="error" id="toerror"></p>
                </div>
            </div>
            
            <div class="box btn">
                <input type="submit" id="button" value="register">
            </div>
        </form>
    </div>

    <div id="message-bg">
        <div id="message-box">
            <span id="x" onclick="closeMessage()">&times;</span>
            <h2 id="message-heading">Message</h2>
            <p id="message-error"></p>
            <button id="message-close-button" onclick="closeMessage()">Close</button>
        </div>
    </div>
    <script>
        window.onload = function(){
            let errorMessage = '<%= session.getAttribute("Error")!= null ? session.getAttribute("Error") : "" %>';
            
            if(errorMessage){
                document.getElementById("message-bg").style.display = "block";
                document.getElementById("message-error").textContent = errorMessage;
            }
        };
        
        function closeMessage(){
            document.getElementById("message-bg").style.display = "none";
        }
    </script>
    
    <% session.removeAttribute("Error"); %>

</body>
</html>