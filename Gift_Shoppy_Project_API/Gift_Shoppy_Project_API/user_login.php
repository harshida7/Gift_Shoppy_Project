<?php
    
    include('connect.php');
    
    $phn=$_REQUEST["phone"];
    $pass=$_REQUEST["password"];
    
    $sql="select * from user_signup where phone='$phn' and password ='$pass'";
    
    
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