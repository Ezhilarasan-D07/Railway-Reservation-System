<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration</title>
    <link rel="stylesheet" href="/RailwayBookingSystem/registration/registration_style.css">
    <script src="/RailwayBookingSystem/registration/registration_script.js"></script>
    
</head>
<body>
    
    <%@include file="../nav/nav.jsp"%>

    
    <div class="container">
        <h2>Register</h2>
        <form action="/RailwayBookingSystem/OTPverifyURL" method="post" onsubmit="return validation(event)">
            <div class="box">
                <label>User Name :</label>
                <input type="text" id="username" class="textbox" name="username" onkeyup="conditions('username', 'usererror')">
                <p class="error" id="usererror"></p>
            </div>
            
            <div class="box">
                <label>Email :</label>
                <input type="email" id="email" class="textbox" name="email" onkeyup="conditions('email', 'emailerror')">
                <p class="error" id="emailerror"></p>
            </div>
            
            <div class="box">
                <label>Password :</label>
                <input type="password" id="password" class="textbox" name="password" onkeyup="conditions('password', 'passworderror')">
                <p class="error" id="passworderror"></p>
            </div>
            
            <div class="box">
                <label>Confirm Password:</label>
                <input type="password" id="confirm_password" class="textbox" name="confirm_password" onkeyup="conditions('confirm_password', 'confirm_passworderror')">
                <p class="error" id="confirm_passworderror"></p>
            </div>
            
            <div class="box cbox">
                <label class="remember">Remember me </label>
                <input type="checkbox">
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