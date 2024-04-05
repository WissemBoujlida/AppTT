<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['CIN']) && isset($_POST['first_name']) && isset($_POST['last_name']) && isset($_POST['email']) && isset($_POST['phone']) && isset($_POST['username']) && isset($_POST['password'])) {

    if ($db->dbConnect()) {
        $db->signUp("CLIENTS_TABLE", $_POST['CIN'], $_POST['first_name'], $_POST['last_name'], $_POST['email'], $_POST['phone'], $_POST['username'], $_POST['password']);
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
