

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