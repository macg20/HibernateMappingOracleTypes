CREATE SEQUENCE simpleSequence
  START WITH 1
  INCREMENT BY 1;


CREATE OR REPLACE TYPE CUST_ADDRESS_TYP AS OBJECT (
    street_address   VARCHAR2(100),
    postal_code      VARCHAR2(100),
    city             VARCHAR2(100),
    state_province   VARCHAR2(100),
    country_id       CHAR(500)
);


CREATE TABLE test_types (
    id            NUMBER(38,0),
    adres         cust_address_typ,
    insert_date   DATE
);