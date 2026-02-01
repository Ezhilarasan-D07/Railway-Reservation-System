let valid={
    username:false,
    email:false,
    password:false,
    confirm_password:false
};

function conditions(textbox, messagebox)
{
    let errors = [];

    let input = document.getElementById(textbox).value;
    let text_field = document.getElementById(textbox);
    let message_field = document.getElementById(messagebox);

    if(input === null || input.trim() === "")
    {
        message_field.style.visibility="visible";
        message_field.innerHTML = "This Field should not be blank";
    }
    else
    {
        if(textbox === "username")
        {
            if(/[^A-Za-z\d]/.test(input)){
                style('no', text_field, message_field);
                message_field.innerHTML = "Special characters not allowed";
                valid.username = false;
            }else{
                if(!/[A-Z]/.test(input)) errors.push("Upper");
                if(!/[a-z]/.test(input)) errors.push("Lower");
                if(!/\d/.test(input)) errors.push("Digit");

                if(errors.length > 0){
                    style('no', text_field, message_field);
                    message_field.innerHTML =  "Must have atleast 1(" + errors.join(", ")+ ")";
                    valid.username=false;
                }else if(input.length < 5){
                    style('no', text_field, message_field);
                    message_field.innerHTML =  "minimum 5 characters";
                    valid.username=false;
                }else{
                    style('yes', text_field, message_field);
                    valid.username=true;
                }
            }
        }
        else if(textbox === "password")
        {
            if(!/[A-Z]/.test(input)) errors.push("Upper");
            if(!/[a-z]/.test(input)) errors.push("Lower");
            if(!/\d/.test(input)) errors.push("Digit");
            if(!/[^A-Za-z\d]/.test(input)) errors.push("Symbol");

            if(errors.length > 0){
                style('no', text_field, message_field);
                message_field.innerHTML =  "Must have atleast 1(" + errors.join(", ")+ ")";
                valid.password=false;
            }else if(input.length < 6){
                    style('no', text_field, message_field);
                    message_field.innerHTML =  "minimum 6 characters";
                    valid.password=false;
            }else{
                style('yes', text_field, message_field);
                valid.password=true;
            }
        }
        else if(textbox === "email")
        {
            if (/^\S+@\S+\.\S+$/.test(input)){
                style('yes', text_field, message_field);
                valid.email=true;
            } else {
                style('no', text_field, message_field);
                message_field.innerHTML = "Invalid email";
                valid.email=false;
            }
        }else if(textbox === "confirm_password"){
            let pass = document.getElementById("password").value;

            if(!/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[^A-Za-z\d]).{6,}$/.test(input)){
                style('no', text_field, message_field);
                message_field.innerHTML = "Password Condition Not Satisfied";
                valid.confirm_password=false;
            }else{
                if(pass === input){
                    style('yes', text_field, message_field);
                    valid.confirm_password=true;

                }else{
                    style('no', text_field, message_field);
                    message_field.innerHTML = "Password Not Matching";
                    valid.confirm_password=false;
                }
            }
        }
    }
}

function style(value, text_field, message_field)
{
    if(value === 'yes'){
        text_field.style.cssText = "border:solid 2px rgba(0, 211, 0, 0.811); padding:10.5px 1.5px 10.5px 13.5px";
        message_field.innerHTML=null;
    }else if(value === 'no'){
        text_field.style.border="none";
        message_field.style.visibility = "visible";
    }
}

function validation(event){
    if(valid.username && valid.email && valid.password && valid.confirm_password){
        return true;
    }else{
        event.preventDefault(); // Stop form submission
        alert("Please enter the correct details before submitting.");
        return false;
    }
}