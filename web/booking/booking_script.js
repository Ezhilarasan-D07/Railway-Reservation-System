let valid={
    passenger_name:false,
    age:false,
    email:false,
    berth:false,
    travel_date:false,
    from:false,
    to:false
};

function conditions(textbox, messagebox)
{
    let errors = [];

    let input = document.getElementById(textbox).value;
    let text_field = document.getElementById(textbox);
    let message_field = document.getElementById(messagebox);

    if(textbox === "travel_date")
    {
        if(input === null || input.trim() === "")
        {
            style('no', text_field, message_field);
            message_field.innerHTML = "Choose date";
            valid.travel_date=false;
        }else{
            style('yes', text_field, message_field);
            valid.travel_date=true;
        }
    }

    if(textbox === "berth")
    {
        if(input === null || input.trim() === "")
        {
            style('no', text_field, message_field);
            message_field.innerHTML = "Choose Berth";
            valid.berth=false;
        }else{
            style('yes', text_field, message_field);
            valid.berth=true;
        }
    }
    else if(textbox === "from" || textbox === "to")
    {
        let val = textbox === "from" ? "from" : "to"; 
        if(input === null || input.trim() === "")
        {
            style('no', text_field, message_field);
            message_field.innerHTML = "Choose Location";
            valid[val]= false;
        }else{
            style('yes', text_field, message_field);
            valid[val]=true;
        }
        
        let selector = textbox === "from" ? "#to option" :"#from option";
        let oppoptions = document.querySelectorAll(selector);

        oppoptions.forEach(option => {
            if(option.value === input){
                option.hidden = true;
            }else{
                option.hidden = false;
            }
            
            if(option.value === ""){
                option.hidden = false;
            }
        });
    }
    else if(input === null || input.trim() === "")
    {
        style('no', text_field, message_field);
        message_field.innerHTML = "This Field should not be blank";
    }
    else
    {
        if(textbox === "passenger_name")
        {
            if(/[^A-Za-z\d]/.test(input)){
                style('no', text_field, message_field);
                message_field.innerHTML = "Special characters not allowed";
                valid.passenger_name = false;
            }else{
                if(!/^[A-Z]/.test(input)) errors.push("Upper begining");
                if(!/[a-z]/.test(input)) errors.push("Lower");

                if(errors.length > 0){
                    style('no', text_field, message_field);
                    message_field.innerHTML =  "Must have atleast 1(" + errors.join(", ")+ ")";
                    valid.passenger_name=false;
                }else if(input.length < 5){
                    style('no', text_field, message_field);
                    message_field.innerHTML =  "minimum 5 characters";
                    valid.passenger_name=false;
                }else{
                    style('yes', text_field, message_field);
                    valid.passenger_name=true;
                }
            }
        }
        else if(textbox === "age")
        {
            if (input > 0 && input <= 100){
                style('yes', text_field, message_field);
                valid.age=true;
            } else {
                style('no', text_field, message_field);
                message_field.innerHTML = "Invalid Age";
                valid.age=false;
            }
        }else if(textbox === "email")
        {
            if (/^\S+@\S+\.\S+$/.test(input)){
                style('yes', text_field, message_field);
                valid.email=true;
            } else {
                style('no', text_field, message_field);
                message_field.innerHTML = "Invalid email";
                valid.email=false;
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
        text_field.style.cssText = "border: 2px solid red; padding:10.5px 1.5px 10.5px 13.5px";
        message_field.style.visibility = "visible";
    }
}

function validation(){
    if(valid.passenger_name && valid.age && valid.email && valid.berth && valid.travel_date && valid.from && valid.to){      
        return true;
    }else{
        alert("Please enter the correct details before submitting.");
        return false;
    }
}