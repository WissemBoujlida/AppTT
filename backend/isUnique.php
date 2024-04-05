<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['column']) && isset($_POST['value'])) {
    if ($db->dbConnect()) {
        if ($db->isUnique("CLIENTS_TABLE", $_POST['column'], $_POST['value'])) {
            echo "UNIQUE";
        } else echo "USED";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>