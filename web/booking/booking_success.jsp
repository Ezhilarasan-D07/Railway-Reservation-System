<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% String username = (String) session.getAttribute("username"); 
    if(username == null || username.trim().isEmpty()){
        session.setAttribute("loginError", "Login is required to access the Booking");
        response.sendRedirect("/RailwayBookingSystem/index.jsp");
        return;
}%>

 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Success</title>
        
        <meta http-equiv="refresh" content="3;URL=/RailwayBookingSystem/booking_detailsURL">
        
            
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Roboto+Slab:wght@100..900&family=Roboto:ital,wght@0,100..900;1,100..900&family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap');

            body{
                margin: 0;
                border: 0;
                font-family: poppins;
            }

            #success_block{
                height:80vh;
                display: flex;
                justify-content: center;
                align-items: center;
                flex-direction: column;
            }

            #success_img{
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
        
        <div id="success_block">
            <svg id="success_img" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                <path fill="rgb(103, 186, 253)" d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 
                12-5.373 12-12-5.373-12-12-12zm-1.959 17l-4.5-4.319 1.395-1.435 
                3.08 2.937 7.021-7.183 1.422 1.409-8.418 8.591z"/>
            </svg>
            <h1 id="booking_msg">Booking Successful</h1>
        </div>
        <!--<div hidden id="booking_details">Details</div>-->

        
        <script>
            window.addEventListener("load", function (){
                setTimeout(() => document.getElementById("success_img").classList.add("zoomed"), 1000);
            });
        </script>
    </body>
</html>
