--------------------------------------------------------
--  File created - วันเสาร์-กันยายน-17-2554   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Sequence EMPLOYEES_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "REDCARPET"."EMPLOYEES_SEQ"  MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 107 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence ORDER_HEADS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "REDCARPET"."ORDER_HEADS_SEQ"  MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 9 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence PICTURES_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "REDCARPET"."PICTURES_SEQ"  MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 31 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence PRODUCTS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "REDCARPET"."PRODUCTS_SEQ"  MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 11 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence PROMOTIONS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "REDCARPET"."PROMOTIONS_SEQ"  MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 14 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SIZES_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "REDCARPET"."SIZES_SEQ"  MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 9 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence STOCKS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "REDCARPET"."STOCKS_SEQ"  MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 51 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence STOCK_TYPES_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "REDCARPET"."STOCK_TYPES_SEQ"  MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table CONSISTS
--------------------------------------------------------

  CREATE TABLE "REDCARPET"."CONSISTS" 
   (	"PRODUCT_ID" NUMBER(*,5), 
	"STOCK_ID" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table EMPLOYEES
--------------------------------------------------------

  CREATE TABLE "REDCARPET"."EMPLOYEES" 
   (	"EMP_ID" NUMBER, 
	"EMP_NAME" VARCHAR2(50 CHAR), 
	"EMP_DOB" DATE, 
	"EMP_LOG" DATE, 
	"EMP_SURNAME" VARCHAR2(200 CHAR), 
	"EMP_USER" VARCHAR2(20 CHAR), 
	"EMP_PWD" VARCHAR2(20 CHAR), 
	"EMP_PIC" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table ORDER_DETAILS
--------------------------------------------------------

  CREATE TABLE "REDCARPET"."ORDER_DETAILS" 
   (	"ORDER_ID" NUMBER, 
	"PRODUCT_ID" NUMBER, 
	"QUANTY" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table ORDER_HEADS
--------------------------------------------------------

  CREATE TABLE "REDCARPET"."ORDER_HEADS" 
   (	"ORDER_ID" NUMBER, 
	"EMP_ID" NUMBER, 
	"ORDER_DATE" DATE, 
	"TOTAL" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table PICTURES
--------------------------------------------------------

  CREATE TABLE "REDCARPET"."PICTURES" 
   (	"BLOB_ID" NUMBER, 
	"BLOB_FILE" VARCHAR2(500 CHAR), 
	"BLOB_TYPE" VARCHAR2(50 CHAR), 
	"BLOB_CONTENT" BLOB
   ) ;
--------------------------------------------------------
--  DDL for Table PRODUCTS
--------------------------------------------------------

  CREATE TABLE "REDCARPET"."PRODUCTS" 
   (	"PRODUCT_ID" NUMBER, 
	"SIZE_ID" NUMBER, 
	"COUNT" NUMBER, 
	"PRODUCT_NAME" VARCHAR2(50 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table PROMOTIONS
--------------------------------------------------------

  CREATE TABLE "REDCARPET"."PROMOTIONS" 
   (	"PROMOTION_ID" NUMBER, 
	"PROMOTION_DESC" VARCHAR2(100 CHAR), 
	"SIZE_ID" NUMBER, 
	"DISCOUNT" NUMBER, 
	"PROMOTION_START" DATE, 
	"PROMOTION_END" DATE, 
	"PROMOTION_NAME" VARCHAR2(50 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table SIZES
--------------------------------------------------------

  CREATE TABLE "REDCARPET"."SIZES" 
   (	"SIZE_ID" NUMBER, 
	"SIZE_NAME" VARCHAR2(50 CHAR), 
	"MAX_ICE_CREAM" NUMBER, 
	"MAX_TOPPING" NUMBER, 
	"PRICE" NUMBER, 
	"SIZE_PIC" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table STOCKS
--------------------------------------------------------

  CREATE TABLE "REDCARPET"."STOCKS" 
   (	"STOCK_ID" NUMBER, 
	"STOCK_NAME" VARCHAR2(100 CHAR), 
	"STOCK_TYPE" NUMBER, 
	"STOCK_PIC" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table STOCK_TYPES
--------------------------------------------------------

  CREATE TABLE "REDCARPET"."STOCK_TYPES" 
   (	"TYPE_ID" NUMBER, 
	"TYPE_NAME" VARCHAR2(30 CHAR)
   ) ;

---------------------------------------------------
--   DATA FOR TABLE CONSISTS
--   FILTER = none used
---------------------------------------------------
REM INSERTING into REDCARPET.CONSISTS
Insert into REDCARPET.CONSISTS (PRODUCT_ID,STOCK_ID) values (8,43);
Insert into REDCARPET.CONSISTS (PRODUCT_ID,STOCK_ID) values (8,44);
Insert into REDCARPET.CONSISTS (PRODUCT_ID,STOCK_ID) values (8,47);
Insert into REDCARPET.CONSISTS (PRODUCT_ID,STOCK_ID) values (8,49);
Insert into REDCARPET.CONSISTS (PRODUCT_ID,STOCK_ID) values (8,50);
Insert into REDCARPET.CONSISTS (PRODUCT_ID,STOCK_ID) values (9,43);
Insert into REDCARPET.CONSISTS (PRODUCT_ID,STOCK_ID) values (9,44);
Insert into REDCARPET.CONSISTS (PRODUCT_ID,STOCK_ID) values (9,46);
Insert into REDCARPET.CONSISTS (PRODUCT_ID,STOCK_ID) values (9,47);
Insert into REDCARPET.CONSISTS (PRODUCT_ID,STOCK_ID) values (9,50);

---------------------------------------------------
--   END DATA FOR TABLE CONSISTS
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE EMPLOYEES
--   FILTER = none used
---------------------------------------------------
REM INSERTING into REDCARPET.EMPLOYEES
Insert into REDCARPET.EMPLOYEES (EMP_ID,EMP_NAME,EMP_DOB,EMP_LOG,EMP_SURNAME,EMP_USER,EMP_PWD,EMP_PIC) values (106,'df',to_timestamp('01-JAN-09 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('17-SEP-11 03.59.04.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'dfdf','df','df',22);

---------------------------------------------------
--   END DATA FOR TABLE EMPLOYEES
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE ORDER_DETAILS
--   FILTER = none used
---------------------------------------------------
REM INSERTING into REDCARPET.ORDER_DETAILS
Insert into REDCARPET.ORDER_DETAILS (ORDER_ID,PRODUCT_ID,QUANTY) values (7,8,0);
Insert into REDCARPET.ORDER_DETAILS (ORDER_ID,PRODUCT_ID,QUANTY) values (8,9,0);

---------------------------------------------------
--   END DATA FOR TABLE ORDER_DETAILS
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE ORDER_HEADS
--   FILTER = none used
---------------------------------------------------
REM INSERTING into REDCARPET.ORDER_HEADS
Insert into REDCARPET.ORDER_HEADS (ORDER_ID,EMP_ID,ORDER_DATE,TOTAL) values (7,106,to_timestamp('16-APR-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),45);
Insert into REDCARPET.ORDER_HEADS (ORDER_ID,EMP_ID,ORDER_DATE,TOTAL) values (8,106,to_timestamp('17-SEP-11 04.10.33.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),90);

---------------------------------------------------
--   END DATA FOR TABLE ORDER_HEADS
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE PICTURES
--   FILTER = none used
---------------------------------------------------
REM INSERTING into REDCARPET.PICTURES
Insert into REDCARPET.PICTURES (BLOB_ID,BLOB_FILE,BLOB_TYPE,BLOB_CONTENT) values (20,'C:\Users\hiterujung\Desktop\IceTop\iceChoco.jpg','jpeg'
---------------------------------------------------
--   END DATA FOR TABLE PICTURES
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE PRODUCTS
--   FILTER = none used
---------------------------------------------------
REM INSERTING into REDCARPET.PRODUCTS
Insert into REDCARPET.PRODUCTS (PRODUCT_ID,SIZE_ID,COUNT,PRODUCT_NAME) values (8,7,0,'noname');
Insert into REDCARPET.PRODUCTS (PRODUCT_ID,SIZE_ID,COUNT,PRODUCT_NAME) values (9,7,0,'noname');

---------------------------------------------------
--   END DATA FOR TABLE PRODUCTS
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE PROMOTIONS
--   FILTER = none used
---------------------------------------------------
REM INSERTING into REDCARPET.PROMOTIONS
Insert into REDCARPET.PROMOTIONS (PROMOTION_ID,PROMOTION_DESC,SIZE_ID,DISCOUNT,PROMOTION_START,PROMOTION_END,PROMOTION_NAME) values (13,'fgfgf',7,5,to_timestamp('01-JAN-09 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('01-JAN-12 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'fgfg');

---------------------------------------------------
--   END DATA FOR TABLE PROMOTIONS
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE SIZES
--   FILTER = none used
---------------------------------------------------
REM INSERTING into REDCARPET.SIZES
Insert into REDCARPET.SIZES (SIZE_ID,SIZE_NAME,MAX_ICE_CREAM,MAX_TOPPING,PRICE,SIZE_PIC) values (7,'S',3,2,50,21);
Insert into REDCARPET.SIZES (SIZE_ID,SIZE_NAME,MAX_ICE_CREAM,MAX_TOPPING,PRICE,SIZE_PIC) values (8,'M',4,3,30,23);

---------------------------------------------------
--   END DATA FOR TABLE SIZES
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE STOCKS
--   FILTER = none used
---------------------------------------------------
REM INSERTING into REDCARPET.STOCKS
Insert into REDCARPET.STOCKS (STOCK_ID,STOCK_NAME,STOCK_TYPE,STOCK_PIC) values (43,'Chocok',1,20);
Insert into REDCARPET.STOCKS (STOCK_ID,STOCK_NAME,STOCK_TYPE,STOCK_PIC) values (44,'Chccochip',1,24);
Insert into REDCARPET.STOCKS (STOCK_ID,STOCK_NAME,STOCK_TYPE,STOCK_PIC) values (45,'Strawberry',1,25);
Insert into REDCARPET.STOCKS (STOCK_ID,STOCK_NAME,STOCK_TYPE,STOCK_PIC) values (46,'Vanila',1,26);
Insert into REDCARPET.STOCKS (STOCK_ID,STOCK_NAME,STOCK_TYPE,STOCK_PIC) values (47,'Jelly',2,27);
Insert into REDCARPET.STOCKS (STOCK_ID,STOCK_NAME,STOCK_TYPE,STOCK_PIC) values (48,'M&M',2,28);
Insert into REDCARPET.STOCKS (STOCK_ID,STOCK_NAME,STOCK_TYPE,STOCK_PIC) values (49,'Ohjpo',2,29);
Insert into REDCARPET.STOCKS (STOCK_ID,STOCK_NAME,STOCK_TYPE,STOCK_PIC) values (50,'lookkad',1,30);

---------------------------------------------------
--   END DATA FOR TABLE STOCKS
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE STOCK_TYPES
--   FILTER = none used
---------------------------------------------------
REM INSERTING into REDCARPET.STOCK_TYPES
Insert into REDCARPET.STOCK_TYPES (TYPE_ID,TYPE_NAME) values (1,'ICE-CREAM');
Insert into REDCARPET.STOCK_TYPES (TYPE_ID,TYPE_NAME) values (2,'TOPPING');

---------------------------------------------------
--   END DATA FOR TABLE STOCK_TYPES
---------------------------------------------------

--------------------------------------------------------
--  Constraints for Table CONSISTS
--------------------------------------------------------

  ALTER TABLE "REDCARPET"."CONSISTS" ADD CONSTRAINT "CONSISTS_PK" PRIMARY KEY ("PRODUCT_ID", "STOCK_ID") ENABLE;
 
  ALTER TABLE "REDCARPET"."CONSISTS" MODIFY ("PRODUCT_ID" NOT NULL ENABLE);
 
  ALTER TABLE "REDCARPET"."CONSISTS" MODIFY ("STOCK_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PROMOTIONS
--------------------------------------------------------

  ALTER TABLE "REDCARPET"."PROMOTIONS" ADD CONSTRAINT "PROMOTION_PK" PRIMARY KEY ("PROMOTION_ID") ENABLE;
 
  ALTER TABLE "REDCARPET"."PROMOTIONS" MODIFY ("PROMOTION_ID" NOT NULL ENABLE);

--------------------------------------------------------
--  Constraints for Table EMPLOYEES
--------------------------------------------------------

  ALTER TABLE "REDCARPET"."EMPLOYEES" ADD CONSTRAINT "EMPLOYEES_PK" PRIMARY KEY ("EMP_ID") ENABLE;
 
  ALTER TABLE "REDCARPET"."EMPLOYEES" MODIFY ("EMP_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ORDER_HEADS
--------------------------------------------------------

  ALTER TABLE "REDCARPET"."ORDER_HEADS" ADD CONSTRAINT "ORDER_HEADS_PK" PRIMARY KEY ("ORDER_ID") ENABLE;
 
  ALTER TABLE "REDCARPET"."ORDER_HEADS" MODIFY ("ORDER_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SIZES
--------------------------------------------------------

  ALTER TABLE "REDCARPET"."SIZES" ADD CONSTRAINT "SIZES_PK" PRIMARY KEY ("SIZE_ID") ENABLE;
 
  ALTER TABLE "REDCARPET"."SIZES" MODIFY ("SIZE_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PRODUCTS
--------------------------------------------------------

  ALTER TABLE "REDCARPET"."PRODUCTS" ADD CONSTRAINT "PRODUCTS_PK" PRIMARY KEY ("PRODUCT_ID") ENABLE;
 
  ALTER TABLE "REDCARPET"."PRODUCTS" MODIFY ("PRODUCT_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table STOCK_TYPES
--------------------------------------------------------

  ALTER TABLE "REDCARPET"."STOCK_TYPES" ADD CONSTRAINT "STOCK_TYPES_PK" PRIMARY KEY ("TYPE_ID") ENABLE;
 
  ALTER TABLE "REDCARPET"."STOCK_TYPES" MODIFY ("TYPE_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ORDER_DETAILS
--------------------------------------------------------

  ALTER TABLE "REDCARPET"."ORDER_DETAILS" ADD CONSTRAINT "ORDER_DETAILS_PK" PRIMARY KEY ("ORDER_ID", "PRODUCT_ID") ENABLE;
 
  ALTER TABLE "REDCARPET"."ORDER_DETAILS" MODIFY ("ORDER_ID" NOT NULL ENABLE);
 
  ALTER TABLE "REDCARPET"."ORDER_DETAILS" MODIFY ("PRODUCT_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table STOCKS
--------------------------------------------------------

  ALTER TABLE "REDCARPET"."STOCKS" ADD CONSTRAINT "STOCKS_PK" PRIMARY KEY ("STOCK_ID") ENABLE;
 
  ALTER TABLE "REDCARPET"."STOCKS" MODIFY ("STOCK_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  DDL for Index PRODUCTS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "REDCARPET"."PRODUCTS_PK" ON "REDCARPET"."PRODUCTS" ("PRODUCT_ID") 
  ;
--------------------------------------------------------
--  DDL for Index EMPLOYEES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "REDCARPET"."EMPLOYEES_PK" ON "REDCARPET"."EMPLOYEES" ("EMP_ID") 
  ;
--------------------------------------------------------
--  DDL for Index SIZES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "REDCARPET"."SIZES_PK" ON "REDCARPET"."SIZES" ("SIZE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index CONSISTS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "REDCARPET"."CONSISTS_PK" ON "REDCARPET"."CONSISTS" ("PRODUCT_ID", "STOCK_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PROMOTION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "REDCARPET"."PROMOTION_PK" ON "REDCARPET"."PROMOTIONS" ("PROMOTION_ID") 
  ;
--------------------------------------------------------
--  DDL for Index ORDER_DETAILS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "REDCARPET"."ORDER_DETAILS_PK" ON "REDCARPET"."ORDER_DETAILS" ("ORDER_ID", "PRODUCT_ID") 
  ;
--------------------------------------------------------
--  DDL for Index STOCK_TYPES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "REDCARPET"."STOCK_TYPES_PK" ON "REDCARPET"."STOCK_TYPES" ("TYPE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index ORDER_HEADS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "REDCARPET"."ORDER_HEADS_PK" ON "REDCARPET"."ORDER_HEADS" ("ORDER_ID") 
  ;
--------------------------------------------------------
--  DDL for Index STOCKS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "REDCARPET"."STOCKS_PK" ON "REDCARPET"."STOCKS" ("STOCK_ID") 
  ;






--------------------------------------------------------
--  Ref Constraints for Table PROMOTIONS
--------------------------------------------------------

  ALTER TABLE "REDCARPET"."PROMOTIONS" ADD CONSTRAINT "PROMOTIONS_SIZES_FK1" FOREIGN KEY ("SIZE_ID")
	  REFERENCES "REDCARPET"."SIZES" ("SIZE_ID") ENABLE;



