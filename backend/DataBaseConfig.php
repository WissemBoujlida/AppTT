<?php

class DataBaseConfig
{
    public $servername;
    public $username;
    public $password;
    public $databasename;

    public function __construct()
    {

        $this->servername = 'mysql';
        $this->username = 'root';
        $this->password = 'root';
        $this->databasename = 'DB_TT';

    }
}

?>
