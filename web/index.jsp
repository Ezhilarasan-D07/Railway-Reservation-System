<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Railway Reservation</title>
    <link rel="stylesheet" href="index_style.css">
    
</head>
<body>
    <%@include file="nav/nav.jsp"%>
    
    <h1>Welcome to Railway Ticket Reservation System</h1>
    <h4>"<span>Get started </span>— book tickets, manage reservations and more..."</h4>
    <form action="registration/registration.jsp">
        <input type = "submit" value = "Create Account">
    </form>
    
    <div id="message-bg">
        <div id="message-box">
            <span id="x" onclick="closeMessage()">&times;</span><br>
            <h2 id="message-heading">Message</h2>
            <p id="message-error"></p>
            <!--Login is required to access the menu-->
            <button id="message-close-button" onclick="closeMessage()">Close</button>
        </div>
    </div>
    
    <script>
        window.onload = function(){
            let errorMessage = '<%= session.getAttribute("loginError")!= null ? session.getAttribute("loginError") : "" %>';
            
            if(errorMessage){
                document.getElementById("message-bg").style.display = "block";
                document.getElementById("message-error").textContent = errorMessage;
            }
        };
        
        function closeMessage(){
            document.getElementById("message-bg").style.display = "none";
        }
    </script>
    
    <% session.removeAttribute("loginError"); %>
    
</body>
</html>
