<?php
require "DataBase.php";
$db = new DataBase();

if ($db->dbConnect()) {
    $db->searchBill("BILLS_TABLE", $_POST['ref'], $_POST['cin']);
} else echo "Error: Database connection";
?>