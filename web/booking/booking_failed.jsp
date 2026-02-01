<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% String username = (String) session.getAttribute("username"); 
    if(username == null || username.trim().isEmpty()){
        session.setAttribute("Error", "Login is required to access the Booking");
        response.sendRedirect("/RailwayBookingSystem/index.jsp");
        return;
}%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Failed</title>
        <meta http-equiv="refresh" content="3;URL=/RailwayBookingSystem/booking/booking.jsp">
        
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Roboto+Slab:wght@100..900&family=Roboto:ital,wght@0,100..900;1,100..900&family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap');

            body{
                margin: 0;
                border: 0;
                font-family: poppins;
            }

            #failed_block{
                height:80vh;
                display: flex;
                justify-content: center;
                align-items: center;
                flex-direction: column;
            }

            #failed_img{
                height: 50px;
                width: 50px;
                position:relative;
                top:-80px;
                transition: transform 0.5s;
            }

            .zoomed {
                transform: scale(3);

            }

            #booking_msg{
                position:relative;
                top:-20px;
            }
        </style>
        
    </head>
    
    <body>
        
        <%@include file="../nav/nav.jsp" %>
        
        <div id="failed_block">
            <svg id = "failed_img" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                <path fill="#ff4848" d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 
                12-5.373 12-12-5.373-12-12-12zm5.5 16.084l-1.403 1.416-4.09-4.096-4.102 4.096-1.405-1.405 
                4.093-4.092-4.093-4.098 1.405-1.405 4.088 4.089 4.091-4.089 
                1.416 1.403-4.092 4.087 4.092 4.094z"/>
             </svg>


            <h1 id="booking_msg">Booking Failed</h1>
        </div>
        <!--<div hidden id="booking_details">Details</div>-->

        <script>
            window.addEventListener("load", function (){
                setTimeout(() => document.getElementById("failed_img").classList.add("zoomed"), 1000);
            });
        </script>
    </body>
</html>
