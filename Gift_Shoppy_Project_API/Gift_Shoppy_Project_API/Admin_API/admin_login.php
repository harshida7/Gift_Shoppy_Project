<?php
    
    include('connect.php');
    
    $phn=$_REQUEST["admin_email"];
    $pass=$_REQUEST["admin_pass"];
    
    $sql="select * from giftshop_admin_login where admin_email='$phn' and admin_pass ='$pass'";
    
    
    $ex=mysqli_query($con,$sql);
    
    $no=mysqli_num_rows($ex);
    
    
    if($no>0)
    {
    $fet=mysqli_fetch_object($ex);
    echo json_encode(['code'=>200]);
    }
    else
    {
    echo "0";
    }

?>