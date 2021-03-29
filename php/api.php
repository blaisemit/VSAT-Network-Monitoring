<?php 
SESSION_START();

require "conn.php";



$user_name = $_SESSION["user"];
$user_pass = $_SESSION["pass"];

//$user_name = "demo";
//$user_pass = "";


$mysql_qry = "select * from users where username like '$user_name' and password like '$user_pass';";
$result = mysqli_query($conn ,$mysql_qry);
if(mysqli_num_rows($result) > 0) {
echo "login success";

include "restapi.php";
//$_SESSION["success"]= $mysql_username;

}
else {
echo "Username or Password error try again";
}

SESSION_DESTROY();

?>