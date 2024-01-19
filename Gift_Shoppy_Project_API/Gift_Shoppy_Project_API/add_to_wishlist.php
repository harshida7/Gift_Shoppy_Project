<?php

    include('connect.php');
    
    $c_id = $_POST["c_id"];
    $pname = $_POST["product_name"];
    $pprice = $_POST["product_price"];
    $pimage = $_POST["product_image"];
    $pdes = $_POST["product_des"];


    if($c_id=="" && $pname=="" && $pprice=="" && $pimage=="" && $pdes=="")
    {
        echo '0';
    }
    else
    {
        $sql ="insert into gift_add_wishlist (c_id,product_name,product_price,product_image,product_des) values ('$c_id','$pname','$pprice','$pimage','$pdes')";
        mysqli_query($con,$sql);
        
    }

?>