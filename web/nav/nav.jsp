<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nav</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Roboto+Slab:wght@100..900&family=Roboto:ital,wght@0,100..900;1,100..900&family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap');

        body{
            border:0;
            margin:0;
            user-select: none;
        }

        nav{
            height:10vh;
            gap:20px;
            font-family: poppins;
            background-color: black;
            display:flex;
            justify-content:end;
            align-items: center;
            padding: 5px 50px ;
        }

        .logo_parent {
            margin-right: auto;
            border: none;
            outline: none;
            position: relative;
            left: 20px;
            display: flex;
            align-items: center; 
            height: 100%;    
        }

        .logo {
            background-color: white;
            padding: 6px;
            border-radius: 50%;
            display: block;
            height: 50px;
            width: 50px;
        }
        
        .options{
            font-size:16px;
            font-weight:300;
            letter-spacing:.7px;
            color:white;
            text-decoration:none;
            padding:5px 20px;
            border:none;
            border-radius:8px;
            margin:2px;
        }

        .options:hover{
            border:solid white 1px;
            margin: 1.2px;
        }
        
        #profile{
            display:flex;
/*            flex-direction:column;
            justify-content: center;*/
            align-items: center;
            background-color:white; 
            color:black; 
            font-weight:600; 
            border:none; 
            margin: 1.2px; 
            padding:5px 12px 5px 5px;
            
        }
        
        #profile_logo{
            padding:0px;
            height:23px;
/*            position:relative;
            top:4px;
            right:5px;*/
        }
        
    </style>
    
</head>

<body>
    <nav>
        <a href="/RailwayBookingSystem/index.jsp" class="logo_parent">
            <svg class="logo" height="50px" width="50px" version="1.1" id="_x32_" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 512 512" xml:space="preserve" fill="#000000">
            <g id="SVGRepo_bgCarrier" stroke-width="0"/>
            <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"/>
            <g id="SVGRepo_iconCarrier"> <style type="text/css"> .st0{fill:#000000;} </style> <g> <path class="st0" d="M431.665,356.848V147.207c0-48.019-38.916-86.944-86.943-86.944h-40.363l4.812-42.824h8.813 c9.435,0,17.508,5.74,20.965,13.898l16.06-6.779V24.55C348.929,10.124,334.641,0.018,317.984,0L193.999,0.009 c-16.639,0.009-30.928,10.116-37.016,24.541l16.06,6.796c3.466-8.166,11.539-13.906,20.956-13.897h8.823l4.81,42.815h-40.354 c-48.01,0-86.942,38.924-86.942,86.944v209.641c0,36.403,26.483,66.736,61.208,72.773L87.011,512h48.488l22.378-33.823h196.264 L376.519,512h48.47l-54.516-82.379C405.182,423.576,431.665,393.252,431.665,356.848z M291.621,17.44l-4.803,42.824h-61.635 l-4.819-42.815L291.621,17.44z M180.715,99.299h150.57v25.095h-150.57V99.299z M135.413,180.409 c0-10.917,8.839-19.773,19.756-19.773h201.664c10.916,0,19.773,8.856,19.773,19.773v65.96c0,10.917-8.857,19.764-19.773,19.764 H155.168c-10.916,0-19.756-8.847-19.756-19.764V180.409z M154.232,378.495c-12.739,0-23.06-10.321-23.06-23.043 c0-12.739,10.321-23.052,23.06-23.052c12.722,0,23.043,10.313,23.043,23.052C177.275,368.174,166.954,378.495,154.232,378.495z M172.421,456.19l16.844-25.461h133.471l16.844,25.461H172.421z M357.768,378.495c-12.722,0-23.043-10.321-23.043-23.043 c0-12.739,10.321-23.052,23.043-23.052c12.739,0,23.06,10.313,23.06,23.052C380.828,368.174,370.507,378.495,357.768,378.495z"/> </g> </g>
            </svg>
        </a>
        
        <%  String profile_name = (String) session.getAttribute("username"); 
            String currentPath = request.getRequestURI();
        %>
        
        <%  if(currentPath.contains("menu")){ %>
                <a href="/RailwayBookingSystem/index.jsp" class="options">Home</a>
        <% }else{ %>
                <a href="/RailwayBookingSystem/menu/menu.jsp" class="options">Menu</a> 
        <% } %>
        
        <%  if(profile_name == null || profile_name.trim().isEmpty()){ %>
                
                <a href="/RailwayBookingSystem/login/login.jsp" class="options">Login</a>
                <a href="/RailwayBookingSystem/registration/registration.jsp" class="options nouser">Register</a>
        <%  }else{ %>
                
                <a href= "/RailwayBookingSystem/LogOutURL" id="logout" class="options">Logout</a>
                <a id="profile" class="options userin"><img id='profile_logo' src='/RailwayBookingSystem/nav/profile.png'/>${username}</a>
        <%  } %>
        
    </nav>
</body>
</html>
