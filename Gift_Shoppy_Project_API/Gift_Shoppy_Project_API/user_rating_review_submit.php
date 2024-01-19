<?php

    include('connect.php');
    
    $user_name = $_POST["user_name"];
    $u_rate = $_POST["user_rating"];
    $u_review = $_POST["user_review"];
  

    if($user_name=="" && $u_rate=="" && $u_review=="")
    {
        echo '0';
    }
    else
    {
        $sql = "insert into gift_shop_rating_review (user_name,user_rating,user_review) values ('$user_name','$u_rate','$u_review')";
        mysqli_query($con,$sql);
        
    }

?>