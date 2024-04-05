<?php

require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $username, $password)
    {
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $this->sql = "SELECT * FROM " . $table . " WHERE USERNAME LIKE '" . $username . "'" . " And PASSWORD LIKE '" . $password ."'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
                $login = true;
            } else $login = false;

        return $login;
    }

    function getBills($table, $cin)
    { 
        $cin = $this->prepareData($cin);
        $this->sql = "SELECT REFERENCE, TYPE, BILLING_DATE, COST, STATE FROM " . $table . " WHERE CLIENT_CIN = '" . $cin . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $rows = [];
        if (mysqli_num_rows($result) != 0) {
            while ($row = mysqli_fetch_assoc($result)){
                $rows[] = $row;
            }
            $json = json_encode($rows);
            echo $json;
        }
        
    }

    function searchBill($table, $ref, $cin)
    {
        $ref = $this->prepareData($ref);
        $cin = $this->prepareData($cin);
        $this->sql = "SELECT REFERENCE, TYPE, BILLING_DATE, COST, STATE FROM " . $table . " WHERE REFERENCE LIKE '" . $ref . "'" . " AND CLIENT_CIN = '" . $cin ."'";
        $result = mysqli_query($this->connect, $this->sql);
        $rows = [];
        if (mysqli_num_rows($result) != 0) {
            while ($row = mysqli_fetch_assoc($result)){
                $rows[] = $row;
            }
            $json = json_encode($rows);
            echo $json;
    }
    }

    function getId($table, $username)
    {
        $username = $this->prepareData($username);
        $this->sql = "SELECT CIN FROM " . $table . " WHERE USERNAME LIKE '" . $username . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $rows = [];

        if (mysqli_num_rows($result) != 0) {
            while ($row = mysqli_fetch_assoc($result)){
                $rows[] = $row;
            }
            $json = json_encode($rows);
            echo $json;
    }   
    }

    function signUp($table, $CIN, $first_name, $last_name, $email, $phone, $username, $password)
    {
        $CIN = $this->prepareData($CIN);
        $first_name = $this->prepareData($first_name);
        $last_name = $this->prepareData($last_name);
        $email = $this->prepareData($email);
        $phone = $this->prepareData($phone);
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $this->sql = "INSERT INTO " . $table . " VALUES('" . $CIN . "', '" . $first_name . "', '" . $last_name . "', '" . $email . "', '" . $phone . "', '" . $username . "', '" . $password . "' )";
        try{
            $result = (mysqli_query($this->connect, $this->sql));
        }catch(Exception $e){
            echo("Error description: " . mysqli_error($this->connect));
        }

    }

    function isUnique($table, $column, $value)
    {
        $column = $this->prepareData($column);
        $value = $this->prepareData($value);
        $this->sql = "SELECT * FROM " . $table . " WHERE " . $column . "='" . $value . "'";
        $result = mysqli_query($this->connect, $this->sql);
        if (mysqli_num_rows($result) == 0) {
                $unique = true;
            } else $unique = false;

        return $unique;
    }

    function exist($table, $ref)
    {
        $ref = $this->prepareData($ref);
        $this->sql = "SELECT * FROM " . $table . " WHERE REFERENCE='" . $ref . "'";
        $result = mysqli_query($this->connect, $this->sql);
        if (mysqli_num_rows($result) != 0) {
                $exist = true;
            } else $exist = false;

        return $exist;
    }

    function getOffers($table)
    { 
        $this->sql = "SELECT * FROM " . $table;
        $result = mysqli_query($this->connect, $this->sql);
        $rows = [];
        if (mysqli_num_rows($result) != 0) {
            while ($row = mysqli_fetch_assoc($result)){
                $rows[] = $row;
            }
            $json = json_encode($rows);
            echo $json;
        }
    }

    function getFilteredOffers($table, $type)
    { 
        $type = $this->prepareData($type);
        $this->sql = "SELECT * FROM " . $table . " WHERE TYPE LIKE '" . $type . "'" ;
        $result = mysqli_query($this->connect, $this->sql);
        $rows = [];
        if (mysqli_num_rows($result) != 0) {
            while ($row = mysqli_fetch_assoc($result)){
                $rows[] = $row;
            }
            $json = json_encode($rows);
            echo $json;
        }
    }

    function searchOffer($table, $name, $type)
    { 
        $name = $this->prepareData($name);
        $type = $this->prepareData($type);
        $this->sql = "SELECT * FROM " . $table . " WHERE NAME LIKE '" . $name . "' AND TYPE LIKE '" . $type . "'" ;
        $result = mysqli_query($this->connect, $this->sql);
        $rows = [];
        if (mysqli_num_rows($result) != 0) {
            while ($row = mysqli_fetch_assoc($result)){
                $rows[] = $row;
            }
            $json = json_encode($rows);
            echo $json;
        }
    }
}

?>
