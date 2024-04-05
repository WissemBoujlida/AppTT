<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['type'])) {
if ($db->dbConnect()) {
    $db->getFilteredOffers("OFFERS_TABLE", $_POST['type']);
} else echo "Error: Database connection";
}else echo "All fields are required";
?>