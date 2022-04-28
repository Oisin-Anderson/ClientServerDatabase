DROP TABLE IF EXISTS HOUSES;
DROP TABLE IF EXISTS CHARACTERS;

CREATE TABLE IF NOT EXISTS HOUSES (
	hid INTEGER IDENTITY NOT NULL,
	hName VARCHAR(20) NOT NULL,
	seat VARCHAR(20) NOT NULL,
	words VARCHAR(50) NOT NULL,
	PRIMARY KEY(hid));

INSERT INTO "PUBLIC"."HOUSES" ( "HID", "HNAME", "SEAT", "WORDS" ) VALUES ( 1, 'Stark', 'Winterfell', 'Winter Is Coming');
INSERT INTO "PUBLIC"."HOUSES" ( "HID", "HNAME", "SEAT", "WORDS" ) VALUES ( 2, 'Lannister', 'Casterly Rock', 'Here Me Roar');
INSERT INTO "PUBLIC"."HOUSES" ( "HID", "HNAME", "SEAT", "WORDS" ) VALUES ( 3, 'Arryn', 'The Eyrie', 'As High As Honor');
INSERT INTO "PUBLIC"."HOUSES" ( "HID", "HNAME", "SEAT", "WORDS" ) VALUES ( 4, 'Baratheon', 'Storms End', 'Ours Is The Fury');
INSERT INTO "PUBLIC"."HOUSES" ( "HID", "HNAME", "SEAT", "WORDS" ) VALUES ( 5, 'Tyrell', 'Highgarden', 'Growing Strong');
INSERT INTO "PUBLIC"."HOUSES" ( "HID", "HNAME", "SEAT", "WORDS" ) VALUES ( 6, 'Tully', 'Riverun', 'Family, Duty, Honor');


CREATE TABLE IF NOT EXISTS CHARACTERS (
	cid INTEGER NOT NULL,
	hid INTEGER NOT NULL,
	cName VARCHAR(30) NOT NULL,
	gender VARCHAR(10) NOT NULL,
	PRIMARY KEY(cid, hid),
	FOREIGN KEY(hid) references HOUSES(hid));
    

INSERT INTO "PUBLIC"."CHARACTERS" ( "CID", "HID", "CNAME", "GENDER" ) VALUES ( 1, 1, 'Robb Stark', 'Male');
INSERT INTO "PUBLIC"."CHARACTERS" ( "CID", "HID", "CNAME", "GENDER" ) VALUES ( 2, 3, 'Lysa Arryn', 'Female');
INSERT INTO "PUBLIC"."CHARACTERS" ( "CID", "HID", "CNAME", "GENDER" ) VALUES ( 3, 5, 'Loras Tyrell', 'Male');
INSERT INTO "PUBLIC"."CHARACTERS" ( "CID", "HID", "CNAME", "GENDER" ) VALUES ( 4, 6, 'Edmure Tully', 'Male');
INSERT INTO "PUBLIC"."CHARACTERS" ( "CID", "HID", "CNAME", "GENDER" ) VALUES ( 5, 2, 'Jaime Lannister', 'Male');
INSERT INTO "PUBLIC"."CHARACTERS" ( "CID", "HID", "CNAME", "GENDER" ) VALUES ( 6, 4, 'Stannis Baratheon', 'Male');
    

