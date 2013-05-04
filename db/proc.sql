create or replace procedure sp_delete_airline(pairlnid varchar2) as
child_exists  exception;
pragma exception_init (child_exists , -02292);
begin
  delete from airline where airln_id = pairlnid;
exception
   when child_exists  then
      raise_application_error(-20002, ': Cannot delete airline. A route exists for this airline.'); 
end;

/

create or replace procedure sp_delete_airport(pairptid varchar2) as
child_exists  exception;
pragma exception_init (child_exists , -02292);
begin
  delete from airport where airpt_id = pairptid;
exception
   when child_exists then
      raise_application_error(-20001, ': Cannot delete airport. A route exists for this airport.'); 
end;

/

CREATE OR REPLACE TRIGGER trg_Update_Segment_Details
AFTER UPDATE ON ROUTE
FOR EACH ROW
BEGIN
UPDATE ROUTE_SEG SET
ARR_TIME = :new.ARR_TIME,
DEP_TIME = :new.DEP_TIME,
AIRPT_ID_TO = :new.AIRPT_ID_TO,
AIRPT_ID_FROM = :new.AIRPT_ID_FROM
WHERE
ROUTE_ID = :old.ROUTE_ID; 
END;

/

CREATE OR REPLACE PROCEDURE SP_DELETE_AIRCRAFT_TYPE(pAircraft_type_id VARCHAR2) AS
child_exists exception;
pragma exception_init (child_exists , -02292);
begin
  delete from aircraft_type where aircr_type_id = pAircraft_type_id;
exception
   when child_exists  then
      raise_application_error(-20004, ': Cannot delete aircraft type. A aircraft exists for this aircraft type.'); 
end;

/

CREATE OR REPLACE PROCEDURE SP_DELETE_AIRCRAFT(pAircraft_id VARCHAR2) AS
child_exists exception;
pragma exception_init (child_exists , -02292);
begin
  delete from aircraft where aircraft_id = pAircraft_id;
exception
   when child_exists  then
      raise_application_error(-20005, ': Cannot delete aircraft. A flight exists for this aircraft .'); 
end;

/

CREATE OR REPLACE PROCEDURE SP_DELETE_FLIGHT(pFlight_id VARCHAR2) AS
child_exists exception;
pragma exception_init (child_exists , -02292);
begin
  delete from Flight where flight_id = pFlight_id;
exception
   when child_exists  then
      raise_application_error(-20006, ': Cannot delete flight. A flight segment exists for this flight .'); 
end;

/

CREATE OR REPLACE PACKAGE pkg_EasyFly_Maintenance AS
procedure sp_delete_airline(pairlnid varchar2);
procedure sp_delete_airport(pairptid varchar2);
PROCEDURE SP_DELETE_AIRCRAFT_TYPE(pAircraft_type_id VARCHAR2);
END pkg_EasyFly_Maintenance;

/

create or replace 
PROCEDURE sp_ADD_FLIGHT(pRoute_ID VARCHAR2,pDep_Date VARCHAR2,pArr_Time NUMBER,
pDep_Time NUMBER,pAircraft_ID VARCHAR2,pFlight_ID NUMBER) IS
vCons_Name VARCHAR(100);
vAircraft_type_id varchar2(5);
vFirstClass NUMBER;
vBusinessClass NUMBER;
vEconomyClass NUMBER;
vFlightSegNo NUMBER;
BEGIN
  vFlightSegNo := FlightSegmentSeq.nextval;
  SELECT aircr_type_id into vAircraft_type_id from aircraft where aircraft_id = pAircraft_ID;
  SELECT seats_qty_f, seats_qty_B, seats_qty_E into vFirstClass, vBusinessClass, vEconomyClass from aircraft where aircraft_id = pAircraft_ID;
	INSERT INTO flight values(pRoute_ID,pDep_Date,pArr_Time,pDep_Time,pAircraft_ID,vAircraft_type_id,pFlight_ID);
	INSERT INTO flight_seg SELECT route_id, pDep_Date, vFlightSegNo, arr_time, dep_time, pFlight_ID FROM route_seg WHERE route_id=pRoute_ID;
	INSERT INTO seats_avail values(pRoute_ID, pDep_Date, vFlightSegNo, 'E', vEconomyClass, 0);
  INSERT INTO seats_avail values(pRoute_ID, pDep_Date, vFlightSegNo, 'F', vFirstClass, 0);
  INSERT INTO seats_avail values(pRoute_ID, pDep_Date, vFlightSegNo, 'B', vBusinessClass, 0);
  COMMIT;
EXCEPTION 
	WHEN OTHERS THEN
        ROLLBACK;
	raise_application_error(-20007, SQLERRM);
END;

/

CREATE OR REPLACE FUNCTION strip_constraint_name(errmsg VARCHAR2)
RETURN VARCHAR2 AS
rp_pos NUMBER;
dot_pos NUMBER;
-- The constraint name lies between the first '.' and the first ')'
BEGIN
dot_pos := INSTR(errmsg, '.');
rp_pos := INSTR(errmsg, ')');
IF (dot_pos = 0 OR rp_pos = 0 ) THEN
RETURN(NULL);
ELSE
RETURN(UPPER(SUBSTR(errmsg, dot_pos+1,
rp_pos-dot_pos-1)));
END IF;
END;

create sequence RouteSegmentSeq start with 1;
create sequence FlightSegmentSeq start with 1;

/

create or replace 
PROCEDURE sp_Update_FLIGHT_Details(pRoute_ID VARCHAR2,pDep_Date VARCHAR2,pArr_Time NUMBER,
pDep_Time NUMBER,pAircraft_ID VARCHAR2,pFlight_ID NUMBER) IS
vRoute_ID route.route_id%type;
vAircraft_type_id varchar2(5);
BEGIN

	SELECT route_id INTO vRoute_ID FROM flight WHERE Flight_ID = pFlight_ID;
	select aircr_type_id into vAircraft_type_id from aircraft where aircraft_id = pAircraft_ID;
	UPDATE flight SET
		route_ID = pRoute_ID,
		dep_date = pDep_Date,
		arr_time = pArr_Time,
		dep_time = pDep_Time,
		aircraft_id = pAircraft_ID,
		aircr_type_id = vAircraft_type_id
		WHERE Flight_ID = pFlight_ID;
	
	DELETE FROM flight_seg WHERE route_ID = vRoute_ID;
	
	INSERT INTO flight_seg SELECT route_id,pDep_Date,seg_no,arr_time,dep_time,pFlight_ID FROM route_seg WHERE route_id=pRoute_ID;
	COMMIT;
EXCEPTION 
	WHEN OTHERS THEN
        ROLLBACK;
	raise_application_error(-20008, SQLERRM);
END;
