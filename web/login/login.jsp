<!-- <%@page contentType="text/html" pageEncoding="UTF-8"%> -->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>login</title>
    <link rel="stylesheet" href="login_style.css">
</head>
<body>
    <%@include file="../nav/nav.jsp" %>

    <div class="container">
        <h2>Login</h2>
        <form action="/RailwayBookingSystem/LogInURL" method="post">
            <div class="box">
                <label>User Name </label>
                <input type="text" name="username" class="textbox">
            </div>
            
            <div class="box">
                <label>Password </label>
                <input type="password" name="password" class="textbox">
            </div>
            
            <div class="box btn">
                <input type="submit" id="button" value="login">
            </div>
        </form>
    </div>

</body>
</html>