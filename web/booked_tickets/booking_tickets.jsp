<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<%
    String url = "jdbc:mysql://localhost:3306/train_booking?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    String name = "root";
    String pass = "1234";

    Connection con = null;
    PreparedStatement prest = null;
    ResultSet rs = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
        con = DriverManager.getConnection(url, name, pass);
        prest = con.prepareStatement("SELECT * FROM booking WHERE status != 'cancelled'");
        rs = prest.executeQuery();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Booked Tickets</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        .container {
            width: 90%;
            margin: 30px auto;
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }

        table.ticket-table {
            width: 100%;
            border-collapse: collapse;
            text-align: center;
        }

        table.ticket-table th,
        table.ticket-table td {
            padding: 10px;
            border-bottom: 1px solid #ccc;
        }

        table.ticket-table th {
            background-color: #f2f2f2;
            color: #333;
        }

        #status {
            font-weight: bold;
            color: #f34848;
        }
    </style>
</head>
<body>
    <%@ include file="../nav/nav.jsp" %>
    <div class="container">
        <h2>Booked Tickets</h2>
        <table class="ticket-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Age</th>
                    <th>Email</th>
                    <th>Preferred Berth</th>
                    <th>Seat No</th>
                    <th>Date</th>
                    <th>Departure</th>
                    <th>Destination</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <%
                    while (rs.next()) {
                %>
                <tr>
                    <td><%= rs.getInt("id") %></td>
                    <td><%= rs.getString("passenger_name") %></td>
                    <td><%= rs.getInt("age") %></td>
                    <td><%= rs.getString("email") %></td>
                    <td><%= rs.getString("berth") %></td>
                    <td><%= rs.getInt("seat_no") %></td>
                    <td><%= rs.getString("date") %></td>
                    <td><%= rs.getString("departure") %></td>
                    <td><%= rs.getString("destination") %></td>
                    <td id="status"><%= rs.getString("status") %></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>

<%
    } catch (Exception e) {
        out.println("<p>Error: " + e.getMessage() + "</p>");
    } finally {
        if (rs != null) rs.close();
        if (prest != null) prest.close();
        if (con != null) con.close();
    }
%>
