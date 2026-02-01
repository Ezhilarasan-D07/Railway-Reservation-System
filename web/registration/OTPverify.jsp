<html>
    <head>
        <title>OTP</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/RailwayBookingSystem/registration/OTPverify.css">
    </head>
    <body>
        
        <%@include file="../nav/nav.jsp" %>
        
        <div>
            <form action="/RailwayBookingSystem/RegistrationURL" method="post">
                <div class="container">
                    <h3>Enter the OTP sent to your mail</h3>
                    <input type="text" class="otp" name="a" id="" maxlength="1" inputmode="numeric" pattern="[0-9]*">
                    <input type="text" class="otp" name="b" id="" maxlength="1" inputmode="numeric" pattern="[0-9]*">
                    <input type="text" class="otp" name="c" id="" maxlength="1" inputmode="numeric" pattern="[0-9]*">
                    <input type="text" class="otp" name="d" id="" maxlength="1" inputmode="numeric" pattern="[0-9]*">
                    <input type="text" class="otp" name="e" id="" maxlength="1" inputmode="numeric" pattern="[0-9]*">
                    <input type="text" class="otp" name="f" id="" maxlength="1" inputmode="numeric" pattern="[0-9]*">
                    <input type="submit" id="otp_button" value = "Verify OTP">
                </div>
            </form>
        </div>
        
        <div id="message-bg" style="display:none">
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
        
        const inputs = document.querySelectorAll('.otp');

        inputs.forEach((input, index) => {
            input.addEventListener('input', () => {
                const next = inputs[index + 1];
                if (input.value.length === 1 && next) {
                    next.focus();
                }
            });

            input.addEventListener('keydown', (e) => {
                if (e.key === "Backspace" && input.value === "" && index > 0) {
                    inputs[index - 1].focus();
                }
            });
        });
    </script>
    
    <% session.removeAttribute("Error"); %>
    </body>
</html>
