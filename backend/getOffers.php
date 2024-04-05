<?php
require "DataBase.php";
$db = new DataBase();
if ($db->dbConnect()) {
    $db->getOffers("OFFERS_TABLE");
} else echo "Error: Database connection";
?>