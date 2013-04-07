create or replace procedure delete_airline(airlnid varchar2) as
child_exists  exception;
pragma exception_init (child_exists , -02292);
begin
  delete from airline where airln_id = airlnid;
exception
   when child_exists  then
      raise_application_error(-20002, ': Cannot delete airline. A route exists for this airline.'); 
end;

/

create or replace procedure delete_airport(airptid varchar2) as
child_exists  exception;
pragma exception_init (child_exists , -02292);
begin
  delete from airport where airpt_id = airptid;
exception
   when child_exists then
      raise_application_error(-20001, ': Cannot delete airport. A route exists for this airport.'); 
end;

/

create or replace trigger delete_route_seg
after delete on route
for each row
begin
  delete from route_seg where route_id = :old.route_id;
end;