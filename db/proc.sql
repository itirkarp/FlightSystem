create or replace procedure delete_route(route_id varchar2) as
begin
  delete from route where route_id = route_id;
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