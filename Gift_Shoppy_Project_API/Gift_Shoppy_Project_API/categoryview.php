<?php
    
    include('connect.php');
    
    $sql = "select * from giftshop_category_insert";
    
    $r = mysqli_query($con,$sql);
    $response = array();
    
    while($row = mysqli_fetch_array($r))
    {
        $value["id"] = $row["id"];
        $value["category_name"] = $row["category_name"];
        $value["category_img"] = $row["category_img"];
    
        array_push($response,$value);
    }
    
    echo json_encode($response);
    mysqli_close($con);
    
    
    


?>