<?php
    
    include('connect.php');
    
    $id = $_POST["id"];
    $fnm = $_POST["first_name"];
    $lnm = $_POST["last_name"];
    $gender = $_POST["gender"];
    $email = $_POST["email"];
    $phone = $_POST["phone"];
    $password = $_POST["password"];
    
    
    $sql =
    "update user_signup set first_name='$fnm', last_name='$lnm', gender='$gender', email='$email', phone='$phone', password='$password' where id = '$id'";
    
    if(mysqli_query($con,$sql))
    {
        echo 'updated Succesfully';
    }
    else
    {
        echo 'something went wrong';
    }

?>