create or replace procedure delete_airline(airpt_id varchar2) as
child_exists  exception;
pragma exception_init (child_exists , -02292);
begin
  delete from airport where airpt_id = airpt_id;
exception
   when child_exists  then
      raise_application_error(-20002, ': Cannot delete airline. A route exists for this airline.'); 
end;

/

create or replace procedure delete_airport(airpt_id varchar2) as
child_exists  exception;
pragma exception_init (child_exists , -02292);
begin
  delete from airport where airpt_id = airpt_id;
exception
   when child_exists  then
      raise_application_error(-20001, ': Cannot delete airport. A route exists for this airport.'); 
end;