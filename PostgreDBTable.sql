CREATE TABLE IF NOT EXISTS public.calendar_availability
(
    center_id integer NOT NULL,
    vaccine character varying(100) COLLATE pg_catalog."default",
    available_capacity_dose1 integer,
    available_capacity_dose2 integer,
    slots character varying(2000) COLLATE pg_catalog."default",
    min_age_limit integer NOT NULL,
    available_date character varying(100) COLLATE pg_catalog."default" NOT NULL,
    name character varying(2000) COLLATE pg_catalog."default",
    pincode integer NOT NULL,
    fee_type character varying(20) COLLATE pg_catalog."default",
    detected_date_time timestamp without time zone NOT NULL,
    CONSTRAINT calendar_availability_pk PRIMARY KEY (center_id, pincode, detected_date_time)
)