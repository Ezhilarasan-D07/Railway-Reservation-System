<!--
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- Redirect to login if username not found --%>
<%
    String username = (String) session.getAttribute("username");
    if (username == null || username.trim().isEmpty()) {
        session.setAttribute("loginError", "Login required");
        response.sendRedirect("/RailwayBookingSystem/index.jsp");
        return;
    }

    // Redirect if no booking found
    if (session.getAttribute("display_id") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
-->

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Booking Details</title>
    <link rel="stylesheet" href="booking_details.css">
</head>
<body>
    <!--<%@include file="../nav/nav.jsp" %>-->
    <h1 id="booking_heading">Booking Details</h1>

    <div id="booking_details_block">

        <!-- LEFT: Ticket Details -->
        <div id="ticket_details_block">
            <div id="ticket_details">
                <h2 id="ticket_details_heading">Ticket Details</h2>
                <div><span class="details">ID :</span> ${display_id}</div>
                <div><span class="details">Name :</span> ${display_name}</div>
                <div><span class="details">Age :</span> ${display_age}</div>
                <div><span class="details">Email :</span> ${display_email}</div>
                <div><span class="details">Preferred Berth :</span> ${display_berth}</div>
                <div><span class="details">Seat No :</span> ${display_seat_no}</div>
                <div><span class="details">Date :</span> ${display_date}</div>
                <div><span class="details">Departure :</span> ${display_departure}</div>
                <div><span class="details">Destination :</span> ${display_destination}</div>
                <div><span class="details">Status :</span> <span id="status">${display_status}</span></div>
            </div>
        </div>

        <!-- CENTER LINE -->
        <div id="line"></div>

        <!-- RIGHT: QR & PDF -->
        <div id="QR_block">
            <h2 id="QR_details_heading">QR & Ticket</h2>
            <div id="QR">
                <img src="/RailwayBookingSystem/pdfs/${display_id}.png" alt="QR Code" width="200" height="200"/>
                <br>
                <a href="/RailwayBookingSystem/pdfs/${display_id}.pdf" download>Download Ticket PDF</a>
            </div>
            <br><br>
            <div id="back_block">
                <svg id="back_logo" fill="#007bff" height="30px" width="30px" version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="800px" height="800px" viewBox="-27.32 -27.32 302.96 302.96" xml:space="preserve" transform="rotate(-45)" stroke="#007bff" stroke-width="14.89932">
                    <g id="SVGRepo_bgCarrier" stroke-width="0"/>
                    <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"/>
                    <g id="SVGRepo_iconCarrier"> <g> <path d="M36.366,36.366c-48.488,48.488-48.488,127.102,0,175.59s127.103,48.488,175.59,0c48.488-48.488,48.488-127.102,0-175.59 S84.854-12.122,36.366,36.366z M180.295,205.997l-88.148-88.148v75.298h-40v-141h141v40h-75.298l88.148,88.148L180.295,205.997z"/> </g> </g>
                </svg>
                <a id="back" href="/RailwayBookingSystem/menu/menu.jsp">Back to menu</a>
            </div>
        </div>
        
    </div>

</body>
</html>
    
