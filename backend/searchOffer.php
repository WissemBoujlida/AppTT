<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['name']) && isset($_POST['type'])) {
if ($db->dbConnect()) {
    $db->searchOffer("OFFERS_TABLE", $_POST['name'], $_POST['type']);
} else echo "Error: Database connection";
}else echo "All fields are required";
?>