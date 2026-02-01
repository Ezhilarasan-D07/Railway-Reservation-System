<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Preference</title>

        <style>
            @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Roboto+Slab:wght@100..900&family=Roboto:ital,wght@0,100..900;1,100..900&family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap');

            body{
                margin:0;
                border:0;
            }

            .container{
                font-family:poppins;
                display:flex;
                justify-content:center;
                flex-direction: column;
                align-items: center;   
            }

            h2{
                position:relative;
                bottom:-10px;
            }

            form{
                height:33vh;
                border-radius:15px;
                display:flex;
                flex-direction:column;
                padding:40px;
                box-shadow:0px 0px 20px rgb(204, 204, 204);
                transition:box-shadow .2s;
                gap:10px;
            }

            form:hover{
                box-shadow:0px 0px 40px rgb(204, 204, 204);
            }

            .box{
                display:flex;
                flex-direction:column;
                margin-bottom: 20px;
            }

            label{
                padding-bottom:3px;
                font-weight: 500;
                letter-spacing:1px;
            }

            .berth{
                width:300px;
                padding:12px 3px 12px 15px;
                font-size:16px;
                border-radius:5px;
                background-color:#eee;
                border:none;
                outline:none;
            }

            .btn{
                position: relative;
                display:flex;
                align-items: center;
            }

            #button{
                font-size: 16px;
                border-radius:8px;
                border:none;
                background-color:rgb(103, 186, 253);
                color:white;
                padding:10px 30px;
                cursor:pointer;
            }

            #button:hover{
                background-color: rgb(51, 163, 255);
            }
        </style>
        
    </head>
    <body>

        <%@include file="../nav/nav.jsp" %>

        <div class="container">
            <h2>Booking Berth Preference</h2>
            <form action="/RailwayBookingSystem/BookingURL" method="post">
                <div class="box">
                    <label>Berth Preference:</label>
                    <select name="selected_berth" class="berth">
                        <%
                            Boolean up = (Boolean) session.getAttribute("up");
                            Boolean mp = (Boolean) session.getAttribute("mp");
                            Boolean lp = (Boolean) session.getAttribute("lp");
        
                            if(up){
                        %>
                            <option value="Upper">Upper</option>
                        <% 
                            }
                            if(mp){
                        %>
                            <option value="Middle">Middle</option>
                        <% 
                            }
                            if(lp){
                        %>
                            <option value="Lower">Lower</option>
                        <% 
                            }
                        %>
                    </select>
                </div>
                
                <div class="box btn">
                    <input type="submit" id="button" value="submit">
                </div>
            </form>
        </div>
    </body>
</html>
