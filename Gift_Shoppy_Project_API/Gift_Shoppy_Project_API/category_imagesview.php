<?php
    include('connect.php');

    $cid=$_REQUEST["data"];
    
    $select="SELECT * from category_image where c_id='$cid'";
   // $select="select * from Category_Images";
    $result=mysqli_query($con,$select);
 
    $response= array();
         
    while ($row = mysqli_fetch_array($result))
    {
        $value = array();
        $value["id"] = $row["id"];
        $value["c_id"] = $row["c_id"];
        $value["product_name"] = $row["product_name"];
        $value["product_price"] = $row["product_price"];
        $value["product_img"] = $row["product_img"];
        $value["product_des"] = $row["product_des"];
        
    
        array_push($response, $value);
    }
    echo json_encode($response);
?>
