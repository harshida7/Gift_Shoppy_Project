<?php
include('connect.php');

$phn = $_POST['phone'];
$pwd = $_POST['password']; 


$sql="select * from user_signup where phone='$phn'";

$r=mysqli_query($con,$sql);

$response["result"]=array();

while($row=mysqli_fetch_array($r))
{
        $value=array();
    
        $value["id"] = $row["id"];
        $value["first_name"] = $row["first_name"];
        $value["last_name"] = $row["last_name"];
        $value["gender"] = $row["gender"];
        $value["email"] = $row["email"];
        $value["phone"] = $row["phone"];
        $value["password"] = $row["password"];
     

        array_push($response["result"],$value);
}

echo json_encode($response);
mysqli_close($con);
 
?>