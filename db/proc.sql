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