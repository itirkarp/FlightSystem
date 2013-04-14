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

create or replace trigger tr_delete_route_seg
after delete on route
for each row
begin
  delete from route_seg where route_id = :old.route_id;
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

ALTER TABLE ROUTE_SEG DROP CONSTRAINT RTSG_IN;

/

ALTER TABLE ROUTE_SEG ADD CONSTRAINT RTSG_IN FOREIGN KEY (ROUTE_ID) REFERENCES ROUTE (ROUTE_ID) ON DELETE CASCADE;