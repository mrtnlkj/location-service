CREATE OR REPLACE FUNCTION "ls".unaccent_str(text)
    RETURNS "pg_catalog"."text"
AS
$BODY$
SELECT translate($1,
                 'aáäAÁÄbBcCčČdDďĎeéeëěEÉEËĚfFgGhHiIíÍjJkKlĺľLĹĽmMnňNŇoóöôOÓÖÔpPqQrŕřRŔŘsšSŠtťTŤuúuüUÚUÜvVwWxXyýYÝzžZŽ',
                 'aaaaaabbccccddddeeeeeeeeeeffgghhiiiijjkkllllllmmnnnnooooooooppqqrrrrrrssssttttuuuuuuuuvvwwxxyyyyzzzz'
           );
$BODY$
    LANGUAGE sql
    IMMUTABLE
    STRICT
    COST 100;