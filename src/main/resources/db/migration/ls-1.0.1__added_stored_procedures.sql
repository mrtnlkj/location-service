--
--
--
--
--insert_location_data_proc
CREATE
OR REPLACE PROCEDURE ls.insert_location_data_proc(IN in_version_id int8, INOUT out_count int8)
 AS $BODY$
DECLARE
BEGIN

SET
search_path TO "$user", public, "ls";

INSERT INTO ls.location (version_id,
                         name_sk,
                         name_en,
                         lat,
                         lon,
                         postal_code,
                         area,
                         population,
                         type,
                         boundary) (
    SELECT (in_version_id)                                                                      AS version_id,
           (point.name)                                                                         AS name_sk,
           (point.tags::hstore OPERATOR(->)'name:en')                                           AS name_en,
           (public.ST_Y(public.ST_TRANSFORM(point.way, 4326)))                                  AS lat,
           (public.ST_X(public.ST_TRANSFORM(point.way, 4326)))                                  AS lon,
           (point.tags::hstore OPERATOR(->)'postal_code')                                       AS postal_code,
           (public.ST_Area(public.ST_Transform(polygon.way, 4326)::public.geography) / 1000000) AS area,
           (point.population::int8)                                                             AS population,
           (point.place)                                                                        AS type,
           (polygon.way)                                                                        AS boundary
    FROM public.osm_point AS point,
         public.osm_polygon AS polygon
    WHERE 1 = 1
      AND (public.ST_intersects(public.ST_Transform(point.way, 4326), public.ST_Transform(polygon.way, 4326)))
      AND (point.place IN ('city', 'town', 'village'))
      AND (polygon.admin_level IN ('9')) --Obec (Town/Village), autonomous towns in Bratislava and Košice
    ORDER BY point.name ASC
);

SELECT COUNT(l.location_id)
INTO out_count
FROM ls.location l
WHERE 1 = 1
  AND (l.version_id = in_version_id);

END;$BODY$
LANGUAGE plpgsql;



--
--
--
--
--process_state_names_proc
CREATE
OR REPLACE PROCEDURE ls.process_state_names_proc(IN in_version_id int8, INOUT out_count int8)
 AS $BODY$
DECLARE
BEGIN

SET
search_path TO "$user", public, "ls";


UPDATE ls.location l
SET state_name_sk = polygon.name,
    state_name_en = (polygon.tags::hstore OPERATOR(->) 'name:en') FROM public.osm_polygon AS polygon
WHERE l.version_id = in_version_id
  AND public.ST_intersects(public.ST_SetSRID(public.ST_MakePoint(l.lon
    , l.lat)
    , 4326)
    , public.ST_Transform(polygon.way
    , 4326))
  AND polygon.admin_level IN ('2') --country borders (SK: Slovensko)
;

SELECT 1
INTO out_count;

END;$BODY$
LANGUAGE plpgsql;



--
--
--
--
--process_region_names_proc
CREATE
OR REPLACE PROCEDURE ls.process_region_names_proc(IN in_version_id int8, INOUT out_count int8)
 AS $BODY$
DECLARE
BEGIN

SET
search_path TO "$user", public, "ls";


UPDATE ls.location l
SET region_name_sk = polygon.name,
    region_name_en = (polygon.tags::hstore OPERATOR(->) 'name:en') FROM public.osm_polygon AS polygon
WHERE l.version_id = in_version_id
  AND public.ST_intersects(public.ST_SetSRID(public.ST_MakePoint(l.lon
    , l.lat)
    , 4326)
    , public.ST_Transform(polygon.way
    , 4326))
  AND polygon.admin_level IN ('4') --region borders (SK: hranica kraja, vyššieho územného celku)
;

SELECT 1
INTO out_count;

END;$BODY$
LANGUAGE plpgsql;



--
--
--
--
--process_district_names_proc
CREATE
OR REPLACE PROCEDURE ls.process_district_names_proc(IN in_version_id int8, INOUT out_count int8)
 AS $BODY$
DECLARE
BEGIN

SET
search_path TO "$user", public, "ls";

UPDATE ls.location l
SET district_name_sk = polygon_district.name,
    district_name_en = (polygon_district.tags::hstore OPERATOR(->) 'name:en'),
    is_in            = polygon_is_in.name FROM
    public.osm_polygon AS polygon_district,
    public.osm_polygon AS polygon_is_in
WHERE l.version_id = in_version_id
  AND public.ST_intersects(public.ST_SetSRID(public.ST_MakePoint(l.lon
    , l.lat)
    , 4326)
    , public.ST_Transform(polygon_district.way
    , 4326))
  AND public.ST_intersects(public.ST_SetSRID(public.ST_MakePoint(l.lon
    , l.lat)
    , 4326)
    , public.ST_Transform(polygon_is_in.way
    , 4326))
  AND polygon_district.admin_level IN ('8') --district borders (SK: hranica okresu)
  AND polygon_is_in.admin_level IN ('3') -- region (Groups of Regions): (SK: oblasti:západné Slovensko, stredné Slovensko, východné Slovensko)
;

SELECT 1
INTO out_count;

END;$BODY$
LANGUAGE plpgsql;