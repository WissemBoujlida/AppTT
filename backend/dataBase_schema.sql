CREATE DATABASE DB_TT;
USE DB_TT;

CREATE TABLE CLIENTS_TABLE (
	CIN INT PRIMARY KEY, 
	FIRST_NAME VARCHAR(32) NOT NULL,
	LAST_NAME VARCHAR(32) NOT NULL,
	EMAIL VARCHAR(256) NOT NULL,
	PHONE INT NOT NULL,
	USERNAME VARCHAR(32) NOT NULL UNIQUE,
	PASSWORD VARCHAR(32) NOT NULL
	);

ALTER TABLE CLIENTS_TABLE ADD CONSTRAINT CHK_PWD1 CHECK (REGEXP_LIKE (PASSWORD, '[[:LOWER:]]'));
ALTER TABLE CLIENTS_TABLE ADD CONSTRAINT CHK_PWD2 CHECK (REGEXP_LIKE (PASSWORD, '[[:UPPER:]]'));
ALTER TABLE CLIENTS_TABLE ADD CONSTRAINT CHK_PWD3 CHECK (REGEXP_LIKE (PASSWORD, '[[:DIGIT:]]'));
ALTER TABLE CLIENTS_TABLE ADD CONSTRAINT CHK_PWD4 CHECK (LENGTH(PASSWORD)>=8);
ALTER TABLE CLIENTS_TABLE ADD CONSTRAINT CHK_EMAIL CHECK (EMAIL LIKE '%@%.%');
ALTER TABLE CLIENTS_TABLE ADD CONSTRAINT CHK_FNAME CHECK (NOT(REGEXP_LIKE(FIRST_NAME,'[^a-zA-Z]')));
ALTER TABLE CLIENTS_TABLE ADD CONSTRAINT CHK_LNAME CHECK (NOT(REGEXP_LIKE(LAST_NAME,'[^a-zA-Z]')));

INSERT INTO CLIENTS_TABLE VALUES
(14018568, 'wissem', 'boujlida', 'wissemboujlida@gmail.com', 20666369, 'wissem404', 'Wissem123'),
(14010609, 'sana', 'boujlida', 'sanaboujlida@gmail.com', 54717555, 'sana14', 'Sana1234');

CREATE TABLE BILLS_TABLE(
	CLIENT_CIN INT NOT NULL, 
	REFERENCE INT PRIMARY KEY, 
	TYPE VARCHAR(32) NOT NULL, 
	BILLING_DATE DATE NOT NULL, 
	COST FLOAT NOT NULL, 
	STATE INT NOT NULL
	);

ALTER TABLE BILLS_TABLE ADD CONSTRAINT FK_CLIENTS_BILLS FOREIGN KEY(CLIENT_CIN) REFERENCES CLIENTS_TABLE(CIN);
ALTER TABLE BILLS_TABLE ADD CONSTRAINT CHK_STATE CHECK (STATE = 0 OR STATE = 1);

INSERT INTO BILLS_TABLE VALUES
(14018568, 103, 'Internet', '2019-08-29', 19.5, 0),
(14018568, 107, 'Mobile', '2021-04-11', 79.9, 0),
(14018568, 123, 'Internet', '2020-07-17', 79.5, 1),
(14018568, 140, 'Internet', '2022-01-02', 39.5, 1),
(14018568, 147, 'Internet', '2022-02-04', 39.5, 0),
(14018568, 159, 'Mobile', '2020-11-05', 50.75, 0),
(14010609, 163, 'Internet', '2016-03-09', 145, 1),
(14010609, 182, 'Internet', '2020-04-24', 79.5, 1),
(14018568, 233, 'Fixe', '2020-09-03', 119.5, 1),
(14018568, 369, 'Fixe', '2019-09-05', 40.25, 1),
(14010609, 381, 'Internet', '2020-08-30', 129.75, 0),
(14010609, 456,	'Internet', '2022-05-19', 47, 0),
(14010609, 497,	'Fixe', '2017-11-18', 90.75, 1),
(14010609, 654, 'Mobile', '2020-02-22', 49.5, 0),
(14018568, 753, 'Fixe', '2019-10-16', 25, 1),
(14010609, 759, 'Mobile', '2017-10-11', 47.5, 1),
(14010609, 789, 'Internet', '2022-01-14', 39.5, 1),
(14018568, 888, 'Fixe', '2021-03-08', 39.5, 0),
(14010609, 957, 'Fixe', '2017-09-15', 80, 0),
(14010609, 987, 'Fixe', '2022-03-18', 41, 1);


CREATE TABLE OFFERS_TABLE(
	NAME VARCHAR(32) PRIMARY KEY NOT NULL,
	TYPE VARCHAR(32) NOT NULL,
	BRIEF_DESCRIPTION VARCHAR(256) NOT NULL,
	DESCRIPTION VARCHAR(256) NOT NULL,
	IMAGE VARCHAR(32) NOT NULL
);

INSERT INTO OFFERS_TABLE VALUES
('WAFFI', 'Internet', 'Choisissez votre option WAFFI et bénéficiez jusqu’à 25 Go d’internet Mobile et 100 min d’appels à partager dans votre flotte allant jusqu’à 5 membres', 'http://192.168.1.12/App_TT/offers/waffi/waffi.html','waffi'),
('LA FIBRE RAPIDO', 'Internet','Le Très Haut Débit arrive chez vous ! Soyez les premiers à profiter et à bénéficier d’une technologie exceptionnelle', 'http://192.168.1.12/App_TT/offers/rapido/rapido.html','rapido'),
('TRANKIL', 'Mobile', 'Avec l’offre Trankil bénéficiez du prix minute le moins cher du marché et appelez tous les opérateurs en Tunisie à seulement 35 millimes la minute.', 'http://192.168.1.12/App_TT/offers/trankil/trankil.html','trankil'),
('JAWEK', 'Mobile', 'Avec Jawek, combinez l’option de votre choix selon votre budget !', 'http://192.168.1.12/App_TT/offers/jawek/jawek.html','jawek'),
('PASSE ETUDIANT', 'Mobile','Pass Branché toujours Connecté ','http://192.168.1.12/App_TT/offers/passetudiant/passEtudiant.html','passetudiant'),
('FORFIX', 'Fixe', 'L’illimité vers tous les numéros fixes en Tunisie à partir de 15 DT/mois','http://192.168.1.12/App_TT/offers/forfix/forfix.html','forfix'),
('MONFIXE', 'Fixe', 'Communiquez en toute liberté avec votre fixe postpayé','http://192.168.1.12/App_TT/offers/monfixe/monfixe.html','monfixe'),
('MOBIRIF', 'Fixe', 'Définissez à l’avance votre seuil de consommation téléphonique par l’acquisition de la carte de recharge fixe qui vous convient.','http://192.168.1.12/App_TT/offers/mobirif/mobirif.html','mobirif');
