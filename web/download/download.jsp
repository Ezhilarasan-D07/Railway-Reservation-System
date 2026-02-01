<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Download</title>
    <link rel="stylesheet" href="/RailwayBookingSystem/download/download_style.css">
</head>
<body>
    
    <%@include file="../nav/nav.jsp" %>
    
    <div class="container">
        <h2>Ticket Download</h2>
        <form action="/RailwayBookingSystem/DownloadURL" method="post">
            <div class="box">
                <label>Passenger ID :</label>
                <input type="text" name="download_id" id="passenger_id">
            </div>
            <div class="box subbox">
                <input type="submit" id="cancel" value="Download">
            </div>
        </form>
    </div>
    
    <div id="message-bg">
        <div id="message-box">
            <span id="x" onclick="closeMessage()">&times;</span>
            <h2 id="message-heading">Message</h2><br>
            <p id="message-error"></p>
            <!--Login is required to access the menu-->
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