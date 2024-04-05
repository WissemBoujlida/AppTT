<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['cin'])) {
if ($db->dbConnect()) {
    $db->getBills("BILLS_TABLE", $_POST['cin']);
} else echo "Error: Database connection";
}else echo "All fields are required";
?>