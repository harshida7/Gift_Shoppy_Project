<?php

    include('connect.php');
    
    $fname = $_POST["first_name"];
    $lname = $_POST["last_name"];
    $gender = $_POST["gender"];
    $email = $_POST["email"];
    $phone = $_POST["phone"];
    $password = $_POST["password"];


    if($fname=="" && $lname=="" && $gender=="" && $email=="" && $phone=="" && $password=="")
    {
        echo '0';
    }
    else
    {
        $sql ="insert into user_signup (first_name,last_name,gender,email,phone,password) values ('$fname','$lname','$gender','$email','$phone','$password')";
        mysqli_query($con,$sql);
        
    }

?>