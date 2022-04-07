--create postgis extension
CREATE EXTENSION IF NOT EXISTS "postgis" WITH SCHEMA public;
--create hstore extension
CREATE EXTENSION IF NOT EXISTS "hstore" WITH SCHEMA public;
SET search_path TO "$user", public, "ls";

--update_id sequence
CREATE SEQUENCE "ls"."update_id_seq" start 1 increment 1;
--update record table
CREATE TABLE "ls".update
(
    "update_id"         int8 NOT NULL DEFAULT nextval('"ls".update_id_seq'::regclass),
    "started_time"      timestamptz,
    "finished_time"     timestamptz,
    "status"            text,
    "type"              text,
    "data_download_url" text,
    "description"       text,
    "failed_reason"     text,
    PRIMARY KEY ("update_id"),
    CONSTRAINT constraint_update_check_type
        CHECK ( "type" IN ('SCHEDULED',
                           'SCHEDULED_STARTUP',
                           'MANUAL',
                           NULL)),
    CONSTRAINT constraint_update_check_status
        CHECK ( "status" IN ('RUNNING',
                             'FINISHED',
                             'FAILED',
                             NULL))
);

CREATE SEQUENCE "ls"."processing_task_id_seq" start 1 increment 1;
--location table
CREATE TABLE "ls"."update_processing_task"
(
    "processing_task_id" int8 NOT NULL DEFAULT nextval('"ls".processing_task_id_seq'::regclass),
    "update_id"          int8,
    "started_time"       timestamptz,
    "finished_time"      timestamptz,
    "status"             text,
    "task_code"          text,
    "attempt"            int8,
    PRIMARY KEY ("processing_task_id"),
    CONSTRAINT fk_update_processing_task_update_update_id
        FOREIGN KEY ("update_id")
            REFERENCES "update" ("update_id"),
    CONSTRAINT constraint_update_processing_task_check_task_code
        CHECK ( "task_code" IN ('OSM_FILE_DOWNLOAD',
                                'OSM2PGSQL_IMPORT',
                                'INCREMENT_LOCATION_VERSION',
                                'LOCATIONS_REMAP',
                                'PROCESS_STATE_NAMES',
                                'PROCESS_REGION_NAMES',
                                'PROCESS_DISTRICT_NAMES',
                                'FINAL_CLEANUP',
                                NULL)),
    CONSTRAINT constraint_update_processing_task_check_status
        CHECK ( "status" IN ('RUNNING',
                             'FINISHED',
                             'FAILED',
                             NULL))

);

--version_id sequence
CREATE SEQUENCE "ls"."version_id_seq" start 1 increment 1;
--location_version table
CREATE TABLE "ls"."location_version"
(
    "version_id"    int8 NOT NULL DEFAULT nextval('"ls".version_id_seq'::regclass),
    "validity_from" timestamptz,
    "validity_to"   timestamptz,
    "update_id"     int8,
    "description"   text,
    PRIMARY KEY ("version_id"),
    CONSTRAINT fk_location_version_update_record_update_id
        FOREIGN KEY ("update_id")
            REFERENCES update ("update_id")
);

--location_id sequence
CREATE SEQUENCE "ls"."location_id_seq" start 1 increment 1;
--location table
CREATE TABLE "ls"."location"
(
    "location_id"      int8 NOT NULL DEFAULT nextval('"ls".location_id_seq'::regclass),
    "version_id"       int8,
    "name_sk"          text,
    "name_en"          text,
    "lat"              decimal(10, 7),
    "lon"              decimal(10, 7),
    "postal_code"      text,
    "area"             decimal(10, 2),
    "population"       int4,
    "type"             text,
    "district_name_sk" text,
    "district_name_en" text,
    "region_name_sk"   text,
    "region_name_en"   text,
    "state_name_sk"    text,
    "state_name_en"    text,
    "is_in"            text,
    "boundary"         geometry,
    PRIMARY KEY ("location_id"),
    CONSTRAINT fk_location_location_version_version_id
        FOREIGN KEY ("version_id")
            REFERENCES "location_version" ("version_id")
);
