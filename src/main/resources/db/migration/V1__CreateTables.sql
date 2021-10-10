DROP TABLE IF EXISTS flywayInitializer;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS Electriccar;
DROP TABLE IF EXISTS ElectricCarType;
DROP TABLE IF EXISTS CarTypeJoin;
DROP TABLE IF EXISTS ChargerType;
DROP TABLE IF EXISTS Charger;
DROP TABLE IF EXISTS ChargingStation;
DROP TABLE IF EXISTS Location;
DROP TABLE IF EXISTS flywayInitializer;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS personrolejoin;



CREATE TABLE IF NOT EXISTS Person (
      ID   SERIAL    NOT NULL,
      name   varchar(200)  NOT NULL,
      username varchar(200) UNIQUE NOT NULL,
      password varchar(200) NOT NULL,
      email   varchar(200)   NOT NULL, --todo API-ba beírni
      phone_number   varchar(200),
      car_ID   integer,
    CONSTRAINT   pk_Person   PRIMARY KEY (
          ID
     )
);
CREATE TABLE IF NOT EXISTS Role (
     ID   SERIAL    NOT NULL,
     name   varchar(200)  UNIQUE NOT NULL,
     CONSTRAINT   pk_Role   PRIMARY KEY (ID)
);
CREATE TABLE IF NOT EXISTS PersonRoleJoin   (
     ID   SERIAL    NOT NULL,
     person_id   integer    NOT NULL,
     role_id   integer    NOT NULL,
     CONSTRAINT   pk_PersonRoleJoin   PRIMARY KEY (ID)
);
CREATE TABLE IF NOT EXISTS ElectricCar   (
      ID   SERIAL    NOT NULL,
      license_plate   varchar(200)   NOT NULL,
      battery_percentage   integer   NOT NULL,
      driver_ID   integer    NOT NULL,
      car_type_ID   integer   NOT NULL,
    CONSTRAINT   pk_ElectricCar   PRIMARY KEY (
          ID
     )
);
CREATE TABLE IF NOT EXISTS ElectricCarType   (
      ID   SERIAL    NOT NULL,
      name   varchar(200)   NOT NULL,
      battery_size   integer   NOT NULL,
      max_charging_speed integer NOT NULL,
    CONSTRAINT   pk_ElectricCarType   PRIMARY KEY (
          ID
     )
);
CREATE TABLE IF NOT EXISTS CarTypeJoin   (
      ID   SERIAL    NOT NULL,
      car_type_ID   integer    NOT NULL,
      charger_type_ID   integer    NOT NULL,
    CONSTRAINT   pk_CarTypeJoin   PRIMARY KEY (
          ID
     )
);
CREATE TABLE IF NOT EXISTS ChargerType   (
      ID   SERIAL    NOT NULL,
      name varchar(200) NOT NULL, --todo
      max_charging_speed   integer   NOT NULL,
    CONSTRAINT   pk_ChargerType   PRIMARY KEY (
          ID
     )
);
CREATE TABLE IF NOT EXISTS Charger   (
      ID    SERIAL    NOT NULL,
      currently_charging_car_ID   integer,
      charger_type_ID   integer    NOT NULL,
      station_ID   integer    NOT NULL,
    CONSTRAINT   pk_Charger   PRIMARY KEY (
          ID
     )
);
CREATE TABLE IF NOT EXISTS ChargingStation   (
      ID   SERIAL    NOT NULL,
      max_number_of_chargers   integer   NOT NULL,
      owner_company_name   varchar(200)   NOT NULL,
      location_ID   integer    NOT NULL,
    CONSTRAINT   pk_ChargingStation   PRIMARY KEY (
          ID
     )
);
CREATE TABLE IF NOT EXISTS location   (
      ID   SERIAL    NOT NULL,
      coordinates point NOT NULL, --todo
    CONSTRAINT   pk_Location   PRIMARY KEY (
          ID
     )
);


delete from person where 1 = 1;
delete from location where 1 = 1;
delete from role where 1 = 1;
delete from charger where 1 = 1;
delete from chargertype where 1 = 1;
delete from electriccartype where 1 = 1;
delete from electriccar where 1 = 1;
delete from chargingstation where 1 = 1;

insert into location (coordinates) values( point(10.0, 5.0));
insert into location (coordinates) values( point(-3.0, 5.0));
insert into location (coordinates) values( point(2.0, 4.0));
insert into location (coordinates) values( point(5.0, 3.0));
insert into location (coordinates) values( point(-9.0, -6.0));

insert into electriccartype (name, battery_size, max_charging_speed) VALUES ('BMW i3',60,8);
insert into electriccartype (name, battery_size, max_charging_speed) VALUES ('Renault Zoe',36,22);
insert into electriccartype (name, battery_size, max_charging_speed) VALUES ('Tesla Model 3',80,211);

insert into chargertype (name, max_charging_speed) values ('ChaDeMo',2);
insert into chargertype (name, max_charging_speed) values ('Type 2',3);
insert into chargertype (name, max_charging_speed) values ('Type 3',4);

insert into chargingstation (max_number_of_chargers, owner_company_name, location_id) values (30,'Mobility',1);
insert into chargingstation (max_number_of_chargers, owner_company_name, location_id) values (10,'Mobility',2);
insert into chargingstation (max_number_of_chargers, owner_company_name, location_id) values (12,'Mobility',3);

insert into person (name, username, password, email, phone_number, car_id) VALUES ('Talpos Norbert','norbi','proba','asd','123',1);
insert into person (name, username, password, email, phone_number, car_id) VALUES ('Virág Ádám','edemsz','proba','asd','123',2);
insert into person (name, username, password, email, phone_number, car_id) VALUES ('Virág Ádám2','edemsz2',null,'asd','123',3);

insert into electriccar ( license_plate, battery_percentage, driver_id, car_type_id) VALUES ('ABC-123',90,1,1);
insert into electriccar ( license_plate, battery_percentage, driver_id, car_type_id) VALUES ('ADJA-12',100,2,3);
insert into electriccar ( license_plate, battery_percentage, driver_id, car_type_id) VALUES ('ADJA-13',100,3,3);

insert into charger (currently_charging_car_id,charger_type_id,station_id) values (1,3,3);
insert into charger (currently_charging_car_id,charger_type_id,station_id) values (2,1,2);
insert into charger (currently_charging_car_id,charger_type_id,station_id) values (3,2,1);

insert into cartypejoin (type_id,charger_type_id) values(1,3);
insert into cartypejoin (type_id,charger_type_id) values(2,1);
insert into cartypejoin (type_id,charger_type_id) values(3,2);
insert into cartypejoin (type_id,charger_type_id) values(3,3);
insert into cartypejoin (type_id,charger_type_id) values(3,1);
insert into cartypejoin (type_id,charger_type_id) values(2,3);
insert into cartypejoin (type_id,charger_type_id) values(1,1);





insert into role (name) values ('role_user');
insert into role (name) values ('role_admin');

