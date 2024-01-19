<?php

    include('connect.php');
    
    $aname = $_POST["admin_name"];
    $aemail = $_POST["admin_email"];
    $aphone = $_POST["admin_phone"];
    $apass = $_POST["admin_pass"];
    

    if($aname=="" && $aemail=="" && $aphone=="" && $apass=="")
    {
        echo '0';
    }
    else
    {
        $sql ="insert into giftshop_admin_login (admin_name,admin_email,admin_phone,admin_pass) values ('$aname','$aemail','$aphone','$apass')";
        mysqli_query($con,$sql);
        
    }

?>