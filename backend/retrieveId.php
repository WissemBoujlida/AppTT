<?php
require "DataBase.php";
$db = new DataBase();

if ($db->dbConnect()) {
    $db->getId("CLIENTS_TABLE", $_POST['username']);
} else echo "Error: Database connection";

?>