INSERT INTO home(text, reference) VALUES ('This is a welcome text 2', 10);

INSERT INTO employee(firstname, lastname, profession, role) VALUES
  ('Sabine', 'Borislava', 'FAG', 'HEALTH_VISITOR'),
  ('Lara', 'Gerber', 'Pflegerin', 'HEALTH_VISITOR'),
  ('Hans', 'Brunner', 'FAG', 'HEALTH_VISITOR'),
  ('Timo', 'Von Burg', 'Pfleger', 'HEALTH_VISITOR');

INSERT INTO user(username, password, employee_id) VALUES
  ('sabine.borislava', 'klartext', select id from employee where lastname = 'Borislava'),
  ('lara.gerber', 'klartext', select id from employee where lastname = 'Gerber'),
  ('hans.brunner', 'klartext', select id from employee where lastname = 'Brunner'),
  ('timo.vonburg', 'klartext', select id from employee where lastname = 'Von Burg');

INSERT INTO address(postal_code, city, street_name, house_nr) VALUES
  (3000, 'Bern', 'Teststrasse', 34),
  (4512, 'Bellach', 'Hasenmattstrasse', 1),
  (3422, 'Kirchberg', 'Solothurnstrasse', 34),
  (3423, 'Ersigen', 'Birkenweg', 3);

INSERT INTO doctor(title, firstname, lastname, telephone) VALUES
  ('Dr. med.', 'Klaus', 'Baumann', '047 432 14 10'),
  ('Dr. med.', 'Bernd', 'Brot', '034 454 23 12');

INSERT INTO medication(name, usage) VALUES
  ('CardioFix', 'Bluthochdruck'),
  ('HeadAce', 'Kopfschmerzen'),
  ('PainKiller', 'Schmerzen');

INSERT INTO diagnose(name) VALUES
  ('Bluthochdruck'),
  ('Rückenschmerzen'),
  ('Alkoholabhängigkeit'),
  ('Heroinabhängigkeit');

INSERT INTO patient(firstname, lastname, birthdate, number, address_id, doctor_id) VALUES
  (
    'Terard', 'Rickner', parsedatetime('1930-09-17 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1,
    select id from address where street_name = 'Teststrasse',
    select id from doctor where lastname = 'Baumann'
  ),
  (
    'Martha', 'Müller', parsedatetime('1936-05-12 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 2,
    select id from address where street_name = 'Hasenmattstrasse',
    select id from doctor where lastname = 'Brot'
  ),
  (
    'Klaus', 'Stucki', parsedatetime('1929-02-05 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 3,
    select id from address where street_name = 'Birkenweg',
    select id from doctor where lastname = 'Brot'
  );

INSERT INTO patient_diagnoses(diagnoses_id, patient_id) VALUES
  (select id from diagnose where name = 'Bluthochdruck', select id from patient where lastname = 'Rickner'),
  (select id from diagnose where name = 'Rückenschmerzen', select id from patient where lastname = 'Rickner'),
  (select id from diagnose where name = 'Alkoholabhängigkeit', select id from patient where lastname = 'Rickner'),
  (select id from diagnose where name = 'Heroinabhängigkeit', select id from patient where lastname = 'Rickner');

INSERT INTO patient_diagnoses(diagnoses_id, patient_id) VALUES
  (select id from diagnose where name = 'Rückenschmerzen', select id from patient where lastname = 'Müller'),
  (select id from diagnose where name = 'Heroinabhängigkeit', select id from patient where lastname = 'Müller');

INSERT INTO patient_diagnoses(diagnoses_id, patient_id) VALUES
  (select id from diagnose where name = 'Bluthochdruck', select id from patient where lastname = 'Stucki'),
  (select id from diagnose where name = 'Alkoholabhängigkeit', select id from patient where lastname = 'Stucki');

INSERT INTO tasktemplate(description, patient_id) VALUES
  ('Blutdruck messen', select id from patient where lastname = 'Rickner'),
  ('Kleidung anziehen', select id from patient where lastname = 'Rickner'),
  ('Stützstrümpfe anziehen', select id from patient where lastname = 'Rickner'),
  ('Waschen', select id from patient where lastname = 'Rickner');

INSERT INTO tasktemplate(description, patient_id) VALUES
  ('Geschirr spülen', select id from patient where lastname = 'Müller'),
  ('Kleidung anziehen', select id from patient where lastname = 'Müller'),
  ('Waschen', select id from patient where lastname = 'Müller');

INSERT INTO tasktemplate(description, patient_id) VALUES
  ('Blutdruck messen', select id from patient where lastname = 'Stucki'),
  ('Geschirr spülen', select id from patient where lastname = 'Stucki'),
  ('Kleidung anziehen', select id from patient where lastname = 'Stucki'),
  ('Spazieren', select id from patient where lastname = 'Stucki');

INSERT INTO absence(description, from_date, end_date, employee_id) VALUES
  ('Ferien', parsedatetime('2019-05-06 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-12 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), select id from employee where lastname = 'Borislava'),
  ('Freier Tag', parsedatetime('2019-05-15 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-16 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), select id from employee where lastname = 'Borislava');

INSERT INTO absence(description, from_date, end_date, employee_id) VALUES
  ('Ferien', parsedatetime('2019-05-13 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-19 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), select id from employee where lastname = 'Gerber'),
  ('Freier Tag', parsedatetime('2019-05-20 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-21 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), select id from employee where lastname = 'Gerber');

INSERT INTO absence(description, from_date, end_date, employee_id) VALUES
  ('Ferien', parsedatetime('2019-05-20 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-26 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), select id from employee where lastname = 'Brunner'),
  ('Freier Tag', parsedatetime('2019-05-09 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-10 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), select id from employee where lastname = 'Brunner');

INSERT INTO absence(description, from_date, end_date, employee_id) VALUES
  ('Ferien', parsedatetime('2019-05-27 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-06-02 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), select id from employee where lastname = 'Von Burg'),
  ('Freier Tag', parsedatetime('2019-05-13 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-13 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), select id from employee where lastname = 'Von Burg');

-- Einsatzserien Patient Rickner
INSERT INTO mission_series(start_date, end_date, repetition_type, patient_id) VALUES
  (parsedatetime('2019-05-06 08:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 09:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-06 16:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 07:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-07 08:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 09:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-08 15:30:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 16:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-09 15:30:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-10 08:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 09:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-11 08:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 09:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-12 15:30:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 16:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner');

INSERT INTO mission_series(start_date, end_date, repetition_type, patient_id) VALUES
  (parsedatetime('2019-05-06 09:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-06 14:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-07 09:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-08 09:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-09 09:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-10 09:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-11 09:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-12 09:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Rickner');

INSERT INTO mission_series(start_date, end_date, repetition_type, patient_id) VALUES
  (parsedatetime('2019-05-07 20:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-06 21:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'ONCE', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-17 19:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-06 20:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'ONCE', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-23 20:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-06 21:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'ONCE', select id from patient where lastname = 'Rickner'),
  (parsedatetime('2019-05-28 19:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-06 20:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'ONCE', select id from patient where lastname = 'Rickner');

-- Einsatzserien Patient Müller
INSERT INTO mission_series(start_date, end_date, repetition_type, patient_id) VALUES
  (parsedatetime('2019-05-06 09:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 10:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Müller'),
  (parsedatetime('2019-05-07 09:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 10:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Müller'),
  (parsedatetime('2019-05-08 09:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 10:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Müller'),
  (parsedatetime('2019-05-09 09:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 10:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Müller'),
  (parsedatetime('2019-05-10 09:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 10:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Müller'),
  (parsedatetime('2019-05-11 09:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 10:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Müller'),
  (parsedatetime('2019-05-12 09:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 10:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Müller');

INSERT INTO mission_series(start_date, end_date, repetition_type, patient_id) VALUES
  (parsedatetime('2019-05-06 11:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 12:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Müller'),
  (parsedatetime('2019-05-07 11:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 12:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Müller'),
  (parsedatetime('2019-05-08 11:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 12:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Müller'),
  (parsedatetime('2019-05-09 11:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 12:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Müller'),
  (parsedatetime('2019-05-10 11:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 12:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Müller'),
  (parsedatetime('2019-05-11 11:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 12:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Müller'),
  (parsedatetime('2019-05-12 11:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 12:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Müller');

INSERT INTO mission_series(start_date, end_date, repetition_type, patient_id) VALUES
  (parsedatetime('2019-05-07 15:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-06 16:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'ONCE', select id from patient where lastname = 'Müller'),
  (parsedatetime('2019-05-17 14:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-06 15:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'ONCE', select id from patient where lastname = 'Müller'),
  (parsedatetime('2019-05-23 14:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-06 15:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'ONCE', select id from patient where lastname = 'Müller'),
  (parsedatetime('2019-05-28 15:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-06 16:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'ONCE', select id from patient where lastname = 'Müller');

-- Einsatzserien Patient Stucki
INSERT INTO mission_series(start_date, end_date, repetition_type, patient_id) VALUES
  (parsedatetime('2019-05-06 18:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 17:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Stucki'),
  (parsedatetime('2019-05-07 18:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 17:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Stucki'),
  (parsedatetime('2019-05-08 18:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 17:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Stucki'),
  (parsedatetime('2019-05-09 18:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 17:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Stucki'),
  (parsedatetime('2019-05-10 18:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 17:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Stucki'),
  (parsedatetime('2019-05-11 18:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 17:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Stucki'),
  (parsedatetime('2019-05-12 18:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 17:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Stucki');

INSERT INTO mission_series(start_date, end_date, repetition_type, patient_id) VALUES
  (parsedatetime('2019-05-06 10:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 11:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Stucki'),
  (parsedatetime('2019-05-07 10:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 11:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Stucki'),
  (parsedatetime('2019-05-08 10:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 11:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Stucki'),
  (parsedatetime('2019-05-09 10:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 11:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Stucki'),
  (parsedatetime('2019-05-10 10:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 11:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Stucki'),
  (parsedatetime('2019-05-11 10:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 11:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Stucki'),
  (parsedatetime('2019-05-12 10:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-07-07 11:15:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'WEEKLY', select id from patient where lastname = 'Stucki');

INSERT INTO mission_series(start_date, end_date, repetition_type, patient_id) VALUES
  (parsedatetime('2019-05-07 13:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-06 14:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'ONCE', select id from patient where lastname = 'Stucki'),
  (parsedatetime('2019-05-17 14:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-06 15:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'ONCE', select id from patient where lastname = 'Stucki'),
  (parsedatetime('2019-05-23 13:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-06 14:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'ONCE', select id from patient where lastname = 'Stucki'),
  (parsedatetime('2019-05-28 14:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('2019-05-06 15:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'ONCE', select id from patient where lastname = 'Stucki');
