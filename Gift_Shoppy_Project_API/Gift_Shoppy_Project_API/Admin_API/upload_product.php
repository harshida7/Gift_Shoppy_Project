<?php 
	
//importing dbDetails file 
include 'connect.php';	

//this is our upload folder 
$upload_path = 'image/';

//Getting the server ip 
$server_ip = gethostbyname(gethostname());

//creating the upload url 
//$upload_url = 'http://'.$server_ip.'/animal/'.$upload_path; 

$upload_url = 'https://'.$_SERVER['SERVER_NAME'] . "/Gift_Shoppy_Project_API/Admin_API/" . $upload_path;
	
	
//getting name from the request 
$cid = $_POST['c_id'];
$pname = $_POST['product_name'];
$pprice = $_POST['product_price'];
$pdes = $_POST['product_des'];
  

//getting file info from the request 
$fileinfo = pathinfo($_FILES["product_img"]["product_name"]);

//getting the file extension 
$extension = $fileinfo["extension"];

//file url to store in the database 
$file_url = $upload_url . $pname . '.' .$extension;

//file path to upload in the server 
$file_path = $upload_path . $pname . '.'.$extension; 
			
//saving the file 
move_uploaded_file($_FILES["product_img"]["tmp_name"],$file_path);
echo $filepath;

if($cid=="" && $pname=="" && $pprice=="" && $file_url=="" && $pdes=="")
{
       echo '0';
}
else
{
        $sql = "INSERT INTO category_image (c_id,product_name,product_price,product_img,product_des) VALUES ('$cid','$pname','$pprice','$file_url','$pdes')";
        $ex=mysqli_query($con,$sql);
}
echo $sql;
//exit;

			
//closing the connection 
mysqli_close($con);

?>