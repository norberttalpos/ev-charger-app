DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS Electriccar;
DROP TABLE IF EXISTS ElectricCarType;
DROP TABLE IF EXISTS CarTypeJoin;
DROP TABLE IF EXISTS ChargerType;
DROP TABLE IF EXISTS Charger;
DROP TABLE IF EXISTS ChargingStation;
DROP TABLE IF EXISTS Location;

CREATE TABLE IF NOT EXISTS Person (
      ID   SERIAL    NOT NULL,
      name   varchar(200)   NOT NULL,
      car_ID   integer    NOT NULL,
    CONSTRAINT   pk_Person   PRIMARY KEY (
          ID
     )
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
    CONSTRAINT   pk_ElectricCarType   PRIMARY KEY (
          ID
     )
);
CREATE TABLE IF NOT EXISTS CarTypeJoin   (
      ID   SERIAL    NOT NULL,
      type_ID   integer    NOT NULL,
      charger_type_ID   integer    NOT NULL,
    CONSTRAINT   pk_CarTypeJoin   PRIMARY KEY (
          ID
     )
);
CREATE TABLE IF NOT EXISTS ChargerType   (
      ID   SERIAL    NOT NULL,
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
CREATE TABLE IF NOT EXISTS Location   (
      ID   SERIAL    NOT NULL,
      x   float(8)   NOT NULL,
      y   float(8)   NOT NULL,
    CONSTRAINT   pk_Location   PRIMARY KEY (
          ID
     )
);
ALTER TABLE   Person   ADD CONSTRAINT fk_Person_car_ID   FOREIGN KEY(  car_ID  )
REFERENCES   ElectricCar   (  ID  );
ALTER TABLE   ElectricCar   ADD CONSTRAINT fk_ElectricCar_driver_ID   FOREIGN KEY(  driver_ID  )
REFERENCES   Person   (  ID  );
ALTER TABLE   ElectricCar   ADD CONSTRAINT fk_ElectricCar_car_type_ID   FOREIGN KEY(  car_type_ID  )
REFERENCES   ElectricCarType   (  ID  );
ALTER TABLE   CarTypeJoin   ADD CONSTRAINT fk_CarTypeJoin_type_ID   FOREIGN KEY(  type_ID  )
REFERENCES   ElectricCarType   (  ID  );
ALTER TABLE   CarTypeJoin   ADD CONSTRAINT fk_CarTypeJoin_charger_type_ID   FOREIGN KEY(  charger_type_ID  )
REFERENCES   ChargerType   (  ID  );
ALTER TABLE   Charger   ADD CONSTRAINT fk_Charger_currently_charging_car_ID   FOREIGN KEY(  currently_charging_car_ID  )
REFERENCES   ElectricCar   (  ID  );
ALTER TABLE   Charger   ADD CONSTRAINT fk_Charger_charger_type_ID   FOREIGN KEY(  charger_type_ID  )
REFERENCES   ChargerType   (  ID  );
ALTER TABLE   Charger   ADD CONSTRAINT fk_Charger_station_ID   FOREIGN KEY(  station_ID  )
REFERENCES   ChargingStation   (  ID  );
ALTER TABLE   ChargingStation   ADD CONSTRAINT fk_ChargingStation_location_ID   FOREIGN KEY(  location_ID  )
REFERENCES   Location   (  ID  );