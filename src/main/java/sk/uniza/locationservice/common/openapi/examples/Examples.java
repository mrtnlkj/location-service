package sk.uniza.locationservice.common.openapi.examples;

public class Examples {

	public static final String EXAMPLE_RESPONSE = "{\"data\":\"data\"}";

	public static final String UPDATE_RECORD_EXAMPLE = "{\n" +
			"  \"updateId\": 1,\n" +
			"  \"startedTime\": \"2022-01-01T12:00:00.000Z\",\n" +
			"  \"finishedTime\": null,\n" +
			"  \"dataDownloadUrl\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"  \"status\": \"RUNNING\",\n" +
			"  \"trigger\": \"MANUAL_UPDATE\",\n" +
			"  \"description\": \"Trying to run manual update of locations data. Default data updater setting will be applied.\" " +
			"}\n";

	public static final String LOCATIONS_OVERVIEW_EXAMPLE = "{\n" +
			"  \"records\": [\n" +
			"    {\n" +
			"      \"locationId\": 5613,\n" +
			"      \"versionId\": 2,\n" +
			"      \"nameSk\": \"Žilina\",\n" +
			"      \"nameEn\": \"Žilina\",\n" +
			"      \"area\": 73.99,\n" +
			"      \"population\": 81494,\n" +
			"      \"districtNameSk\": \"okres Žilina\",\n" +
			"      \"districtNameEn\": \"District of Žilina\",\n" +
			"      \"regionNameSk\": \"Žilinský kraj\",\n" +
			"      \"regionNameEn\": \"Region of Žilina\",\n" +
			"      \"stateNameSk\": \"Slovensko\",\n" +
			"      \"stateNameEn\": \"Slovakia\",\n" +
			"      \"isIn\": \"Stredné Slovensko\",\n" +
			"      \"postalCode\": null,\n" +
			"      \"type\": \"CITY\",\n" +
			"      \"lat\": 49.2234674,\n" +
			"      \"lon\": 18.7393139,\n" +
			"      \"boundary\": null\n" +
			"    }\n" +
			"  ],\n" +
			"  \"recordsCount\": 1\n" +
			"}\n";

	public static final String LOCATION_BY_ID_EXAMPLE = "{\n" +
			"  \"locationId\": 969,\n" +
			"  \"versionId\": 1,\n" +
			"  \"nameSk\": \"Klokočov\",\n" +
			"  \"nameEn\": null,\n" +
			"  \"area\": 11.95,\n" +
			"  \"population\": 406,\n" +
			"  \"districtNameSk\": \"okres Michalovce\",\n" +
			"  \"districtNameEn\": \"District of Michalovce\",\n" +
			"  \"regionNameSk\": \"Košický kraj\",\n" +
			"  \"regionNameEn\": \"Region of Košice\",\n" +
			"  \"stateNameSk\": \"Slovensko\",\n" +
			"  \"stateNameEn\": \"Slovakia\",\n" +
			"  \"isIn\": \"Východné Slovensko\",\n" +
			"  \"postalCode\": \"072 36\",\n" +
			"  \"type\": \"VILLAGE\",\n" +
			"  \"lat\": 48.8143498,\n" +
			"  \"lon\": 22.0296342,\n" +
			"  \"boundary\": \"{\\\"type\\\":\\\"Polygon\\\",\\\"crs\\\":{\\\"type\\\":\\\"name\\\",\\\"properties\\\":{\\\"name\\\":\\\"EPSG:3857\\\"}},\\\"coordinates\\\":[[[2450645.267777828,6243209.801897366],[2450646.859646546,6242594.531792273],[2450954.669170538,6241688.273856876],[2451126.368353138,6241062.28109552],[2451255.35424712,6240333.825620674],[2451450.920328546,6239228.866073097],[2452092.231915006,6239393.119806187],[2453620.73757919,6239772.228421567],[2454003.676627519,6239778.649554986],[2454210.307866329,6240360.204739335],[2454238.11547513,6240371.138325072],[2454407.788642997,6240383.018264969],[2454551.824932134,6240362.773370268],[2454655.930919924,6240993.328214106],[2454673.686378705,6241139.025636045],[2454736.893585578,6242018.891189562],[2454761.695568127,6242045.850228921],[2454821.874884849,6242240.262090966],[2454880.317617516,6242326.82091961],[2454919.32396709,6242452.189960491],[2454875.786914241,6242481.601329368],[2454847.589687223,6242530.772633206],[2454860.07973409,6242672.118478476],[2454815.718917008,6242774.68950583],[2454725.316358536,6242938.758400312],[2454616.3234451,6243163.12851573],[2454613.317818848,6243292.127539682],[2454340.462614965,6244174.228521949],[2454227.072581643,6244590.222945002],[2454039.989045416,6245227.754372768],[2453962.443888129,6245553.420133688],[2453965.282535144,6245721.432037335],[2453794.162213897,6246170.173397544],[2453352.1904396,6247391.485104927],[2453332.141799309,6247474.832343463],[2453308.686782598,6247509.92705306],[2453213.519749919,6247773.471825785],[2453209.846206723,6247772.457005393],[2453193.482241576,6247767.721178495],[2453047.987667109,6247724.591448248],[2453020.157794412,6247716.303799738],[2452855.627587018,6247679.770588686],[2452643.118679095,6247626.493271626],[2452527.569047651,6247584.040792175],[2452466.565966696,6247561.884403321],[2452461.55658961,6247560.869608268],[2452450.31332104,6247559.347415926],[2452434.505953348,6247556.810429293],[2452421.926850889,6247552.751252258],[2452353.46536405,6247538.882412071],[2452224.112115749,6247532.793660138],[2452118.135960513,6247532.286264345],[2451997.131674021,6247534.146715748],[2451950.822765851,6247534.992375612],[2451947.149222655,6247533.977583783],[2451945.924708256,6247533.808451824],[2451938.577621864,6247533.131924021],[2451869.782176554,6247528.565362756],[2451769.371995858,6247538.375015912],[2451673.859872758,6247533.808451824],[2451635.009370471,6247532.117132418],[2451600.722967306,6247531.102340934],[2451589.591018227,6247531.948000494],[2451379.308500119,6247535.161507593],[2451325.763825047,6247534.992375612],[2451245.836430657,6247534.823243628],[2451213.442458836,6247536.852827608],[2451107.35498411,6247526.874044401],[2450967.315064692,6247531.60973666],[2450805.011247116,6247549.368606218],[2450686.455989421,6247546.324225937],[2450683.606210457,6247094.448845034],[2450700.259606279,6246921.014792219],[2450740.223303474,6246838.214179617],[2450720.864844026,6246548.942655927],[2450696.619458931,6245948.446166213],[2450685.053363837,6245320.143429049],[2450664.526049735,6244924.13220635],[2450659.639124089,6244680.322537692],[2450693.057235225,6244229.715807311],[2450691.999700063,6244089.307642764],[2450645.267777828,6243209.801897366]]]}\"\n" +
			"}";

	public static final String UPDATE_RECORDS_EXAMPLE = "{\n" +
			"  \"records\": [\n" +
			"    {\n" +
			"      \"updateId\": 6,\n" +
			"      \"startedTime\": \"2022-01-27T19:11:19.735359Z\",\n" +
			"      \"finishedTime\": \"2022-01-27T19:48:24.306794Z\",\n" +
			"      \"dataDownloadUrl\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"      \"status\": \"FINISHED\",\n" +
			"      \"trigger\": \"SCHEDULED_RETRY_UPDATE\",\n" +
			"      \"description\": null\n" +
			"    },\n" +
			"    {\n" +
			"      \"updateId\": 1,\n" +
			"      \"startedTime\": \"2022-01-27T16:05:42.357711Z\",\n" +
			"      \"finishedTime\": \"2022-01-27T16:33:06.266130Z\",\n" +
			"      \"dataDownloadUrl\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"      \"status\": \"FINISHED\",\n" +
			"      \"trigger\": \"MANUAL_UPDATE\",\n" +
			"      \"description\": \"Trying to run manual update of locations data. Default data updater setting will be applied.\"\n" +
			"    }\n" +
			"  ],\n" +
			"  \"recordsCount\": 2\n" +
			"}";

	public static final String RUN_UPDATE_REQUEST_EXAMPLE = "{\n" +
			"  \"url\": null,\n" +
			"  \"description\": \"Trying to run manual update of locations data. Default data updater setting will be applied.\"\n" +
			"}";

	public static final String RUN_UPDATE_REQUEST_WITH_URL_EXAMPLE = "{\n" +
			"  \"url\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"  \"description\": \"Trying to run manual data update of locations data with specified custom URL for OSM data file download.\"\n" +
			"}";
}
