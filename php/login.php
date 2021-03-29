<?php 
SESSION_START();

require "conn.php";


$user_name = $_POST["username"];
$user_pass = $_POST["password"];

//$user_name = "demo";
//$user_pass = "";

$user = $user_name;
$pass = $user_pass;



$mysql_qry = "select * from users where username like '$user_name' and password like '$user_pass';";
$result = mysqli_query($conn ,$mysql_qry);
if(mysqli_num_rows($result) > 0) {
echo "login success";

$_SESSION["user"]= $user;
$_SESSION["pass"]= $pass;

}
else {
echo "Username or Password error try again";
}
?>