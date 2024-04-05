# AppTT - Internship project

A mobile application developed  as part of my first internship with Tunisie Telecom TT, under the android platform allowing customers to view their bills, pay them, as well as consult a range of mobile, landline and internet offers.

# Technologies used to build the project

Android Studio, Java, PHP, SQL

# Requirements

For building and running the application you need:

- Java 8
- PHP 8
- MySQL 5
- Apache 2
- Android Studio

# Running the application

Step 1: Create a New Folder named `App_TT` in Apache Document root folder ( By default Apache Document root folder  is at `/var/www/html`) .

Step 2: Copy content of `backend` direcotry located in `Project Directory` to `App_TT` directory located in `/var/www/html`.

Step 3: Run `dataBase_schema.sql` to create database,  tables and insert test data.      (`dataBase_schema.sql` is at `/var/www/html/App_TT`). Open `dataBase_schema.sql` file and make sure to change the  host private ip address in Description values when inserting test data to OFFERS_TABLE.

Step 4: You can change Database Configuration within the `DataBaseConfig.php` (`DataBaseConfig.php` is at `/var/www/html/App_TT`)

Step 5: Open  the `App_TT` directory located in `project directory` using Android Studio.

Step 6: you can change the host private ip address `localhostIp` in `AppConfig` Class.

 Step 7: Run the Application

# Project Documentation

Read the project documentation in [french](https://esprittncom-my.sharepoint.com/:b:/g/personal/wissem_boujlida_esprit_tn/EeoD026NP31NtZIzqwllMa0BXbz0yQMHmkEMwGAGA3ZH_A?e=twpiDS) or [english](https://esprittncom-my.sharepoint.com/:b:/g/personal/wissem_boujlida_esprit_tn/Ee_85A4KufNFjqIew1FWCHYBfTp8bVC_NrEHQZ9Mcr9ipg?e=hYy6XR) languages.

