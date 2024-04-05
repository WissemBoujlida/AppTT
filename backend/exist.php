<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['ref'])) {
    if ($db->dbConnect()) {
        if ($db->exist("BILLS_TABLE", $_POST['ref'])) {
            echo "YES";
        } else echo "NO";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>