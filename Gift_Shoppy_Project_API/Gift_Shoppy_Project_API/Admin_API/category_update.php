<?php
    
    include('connect.php');
    
    $id = $_POST["id"];
    $cate_name = $_POST["category_name"];
    $cate_img = $_POST["category_img"];
    
    
    
    $sql ="update giftshop_category_insert set category_name='$cate_name',category_img='$cate_img' where id = '$id'";
    
    if(mysqli_query($con,$sql))
    {
        echo 'updated Succesfully';
    }
    else
    {
        echo 'something went wrong';
    }

?>