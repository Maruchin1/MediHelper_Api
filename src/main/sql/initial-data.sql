
truncate times_of_taking,  medicines_plans, medicines, persons, registered_user restart identity;

insert into registered_user (registered_user_id, auth_token, email, password)
values (1, 'aa', 'aa', 'aa');

insert into medicines (medicine_id, additional_info, curr_state, expire_date, image, medicine_name, medicine_unit, package_size, registered_user_id)
values (1, 'Sram psa jak sra', 60, '2019-09-30', '', 'Hitaxa', 'tabletki', 70, 1);

insert into persons (person_id, main_person, person_color_res_id, person_name, registered_user_id)
values (1, true, 1, 'Ja', 1);

insert into medicines_plans (medicine_plan_id, friday, monday, saturday, sunday, thursday, tuesday, wednesday, days_type, duration_type, end_date, interval_of_days, start_date, medicine_id, person_id)
values (1, null, null, null, null, null, null, null, 'codziennie', 'okres', '2019-09-23', null, '2019-09-24', 1, 1);

insert into times_of_taking (time_of_taking_id, dose_size, time, medicine_plan_id)
values (1, 3, '08:00', 1)



