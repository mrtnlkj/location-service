--create postgis extension
CREATE
EXTENSION IF NOT EXISTS "postgis" SCHEMA "ls";

--update_id sequence
CREATE SEQUENCE "ls"."update_id_seq" start 1 increment 1;
--update record table
CREATE TABLE "ls"."update_record"
(
    "update_id"         int8 NOT NULL DEFAULT nextval('"ls".update_id_seq'::regclass),
    "started_time"      timestamptz,
    "finished_time"     timestamptz,
    "status"            text,
    "trigger"           text,
    "data_download_url" text,
    "description"       text,
    PRIMARY KEY ("update_id")
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
            REFERENCES "update_record" ("update_id")
);

--location_id sequence
CREATE SEQUENCE "ls"."location_id_seq" start 1 increment 1;
--location table
CREATE TABLE "ls"."location"
(
    "location_id" int8 NOT NULL DEFAULT nextval('"ls".location_id_seq'::regclass),
    "version_id"  int8,
    "name_sk"     text,
    PRIMARY KEY ("location_id"),
    CONSTRAINT fk_location_location_version_version_id
        FOREIGN KEY ("version_id")
            REFERENCES "location_version" ("version_id")
);
