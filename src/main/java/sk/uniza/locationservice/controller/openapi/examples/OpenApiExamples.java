package sk.uniza.locationservice.controller.openapi.examples;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@SuppressWarnings("java:S1192")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OpenApiExamples {

	public static final String HTTP_200 = "200";
	public static final String HTTP_201 = "201";
	public static final String HTTP_200_DESCRIPTION = "Success.";
	public static final String HTTP_201_DESCRIPTION = "Created.";
	public static final String EMPTY_RESPONSE_BODY = " ";

	public static final String LC_GET_LOCATIONS = "{\n" +
			"  \"records\": [\n" +
			"    {\n" +
			"      \"locationId\": 36689,\n" +
			"      \"versionId\": 149847,\n" +
			"      \"nameSk\": \"Ábelová\",\n" +
			"      \"nameEn\": null,\n" +
			"      \"area\": 52.18,\n" +
			"      \"population\": 231,\n" +
			"      \"districtNameSk\": null,\n" +
			"      \"districtNameEn\": null,\n" +
			"      \"regionNameSk\": null,\n" +
			"      \"regionNameEn\": null,\n" +
			"      \"stateNameSk\": \"Slovensko\",\n" +
			"      \"stateNameEn\": \"Slovakia\",\n" +
			"      \"isIn\": \"Stredné Slovensko\",\n" +
			"      \"postalCode\": \"985 13\",\n" +
			"      \"type\": \"VILLAGE\",\n" +
			"      \"lat\": 48.4107111,\n" +
			"      \"lon\": 19.4324517,\n" +
			"      \"boundary\": null\n" +
			"    },\n" +
			"    {\n" +
			"      \"locationId\": 36690,\n" +
			"      \"versionId\": 149847,\n" +
			"      \"nameSk\": \"Abovce\",\n" +
			"      \"nameEn\": null,\n" +
			"      \"area\": 8.16,\n" +
			"      \"population\": 617,\n" +
			"      \"districtNameSk\": null,\n" +
			"      \"districtNameEn\": null,\n" +
			"      \"regionNameSk\": null,\n" +
			"      \"regionNameEn\": null,\n" +
			"      \"stateNameSk\": \"Slovensko\",\n" +
			"      \"stateNameEn\": \"Slovakia\",\n" +
			"      \"isIn\": \"Stredné Slovensko\",\n" +
			"      \"postalCode\": \"980 44\",\n" +
			"      \"type\": \"VILLAGE\",\n" +
			"      \"lat\": 48.3192231,\n" +
			"      \"lon\": 20.3376889,\n" +
			"      \"boundary\": null\n" +
			"    },\n" +
			"    {\n" +
			"      \"locationId\": 36691,\n" +
			"      \"versionId\": 149847,\n" +
			"      \"nameSk\": \"Abrahám\",\n" +
			"      \"nameEn\": null,\n" +
			"      \"area\": 15.79,\n" +
			"      \"population\": 1078,\n" +
			"      \"districtNameSk\": null,\n" +
			"      \"districtNameEn\": null,\n" +
			"      \"regionNameSk\": null,\n" +
			"      \"regionNameEn\": null,\n" +
			"      \"stateNameSk\": \"Slovensko\",\n" +
			"      \"stateNameEn\": \"Slovakia\",\n" +
			"      \"isIn\": \"Západné Slovensko\",\n" +
			"      \"postalCode\": \"925 45\",\n" +
			"      \"type\": \"VILLAGE\",\n" +
			"      \"lat\": 48.2479831,\n" +
			"      \"lon\": 17.6194193,\n" +
			"      \"boundary\": null\n" +
			"    },\n" +
			"    {\n" +
			"      \"locationId\": 36692,\n" +
			"      \"versionId\": 149847,\n" +
			"      \"nameSk\": \"Abrahámovce\",\n" +
			"      \"nameEn\": null,\n" +
			"      \"area\": 5.9,\n" +
			"      \"population\": null,\n" +
			"      \"districtNameSk\": null,\n" +
			"      \"districtNameEn\": null,\n" +
			"      \"regionNameSk\": null,\n" +
			"      \"regionNameEn\": null,\n" +
			"      \"stateNameSk\": \"Slovensko\",\n" +
			"      \"stateNameEn\": \"Slovakia\",\n" +
			"      \"isIn\": \"Východné Slovensko\",\n" +
			"      \"postalCode\": \"086 41\",\n" +
			"      \"type\": \"VILLAGE\",\n" +
			"      \"lat\": 49.1616223,\n" +
			"      \"lon\": 21.3426096,\n" +
			"      \"boundary\": null\n" +
			"    },\n" +
			"    {\n" +
			"      \"locationId\": 36693,\n" +
			"      \"versionId\": 149847,\n" +
			"      \"nameSk\": \"Abrahámovce\",\n" +
			"      \"nameEn\": null,\n" +
			"      \"area\": 6.65,\n" +
			"      \"population\": null,\n" +
			"      \"districtNameSk\": null,\n" +
			"      \"districtNameEn\": null,\n" +
			"      \"regionNameSk\": null,\n" +
			"      \"regionNameEn\": null,\n" +
			"      \"stateNameSk\": \"Slovensko\",\n" +
			"      \"stateNameEn\": \"Slovakia\",\n" +
			"      \"isIn\": \"Východné Slovensko\",\n" +
			"      \"postalCode\": \"059 72\",\n" +
			"      \"type\": \"VILLAGE\",\n" +
			"      \"lat\": 49.0438759,\n" +
			"      \"lon\": 20.4416395,\n" +
			"      \"boundary\": null\n" +
			"    }\n" +
			"  ],\n" +
			"  \"recordsCount\": 2815\n" +
			"}";
	public static final String LC_GET_LOCATIONS_NOT_FOUND = "{\n" +
			"  \"records\": [],\n" +
			"  \"recordsCount\": 0\n" +
			"}";
	public static final String LC_GET_LOCATION_BY_ID = "{\n" +
			"  \"locationId\": 123,\n" +
			"  \"versionId\": 1,\n" +
			"  \"nameSk\": \"Bočiar\",\n" +
			"  \"nameEn\": null,\n" +
			"  \"area\": 0.47,\n" +
			"  \"population\": 222,\n" +
			"  \"districtNameSk\": \"okres Košice - okolie\",\n" +
			"  \"districtNameEn\": \"District of Košice - okolie\",\n" +
			"  \"regionNameSk\": \"Košický kraj\",\n" +
			"  \"regionNameEn\": \"Region of Košice\",\n" +
			"  \"stateNameSk\": \"Slovensko\",\n" +
			"  \"stateNameEn\": \"Slovakia\",\n" +
			"  \"isIn\": \"Východné Slovensko\",\n" +
			"  \"postalCode\": \"044 57\",\n" +
			"  \"type\": \"VILLAGE\",\n" +
			"  \"lat\": 48.5920569,\n" +
			"  \"lon\": 21.2353988,\n" +
			"  \"boundary\": null " +
			"}";
	public static final String LC_GET_LOCATION_BY_ID_EMBED_GEO_JSON = "{\n" +
			"  \"locationId\": 123,\n" +
			"  \"versionId\": 1,\n" +
			"  \"nameSk\": \"Bočiar\",\n" +
			"  \"nameEn\": 'null',\n" +
			"  \"area\": 0.47,\n" +
			"  \"population\": 222,\n" +
			"  \"districtNameSk\": \"okres Košice - okolie\",\n" +
			"  \"districtNameEn\": \"District of Košice - okolie\",\n" +
			"  \"regionNameSk\": \"Košický kraj\",\n" +
			"  \"regionNameEn\": \"Region of Košice\",\n" +
			"  \"stateNameSk\": \"Slovensko\",\n" +
			"  \"stateNameEn\": \"Slovakia\",\n" +
			"  \"isIn\": \"Východné Slovensko\",\n" +
			"  \"postalCode\": \"044 57\",\n" +
			"  \"type\": \"VILLAGE\",\n" +
			"  \"lat\": 48.5920569,\n" +
			"  \"lon\": 21.2353988,\n" +
			"  \"boundary\": {\n" +
			"    \"type\": \"Polygon\",\n" +
			"    \"coordinates\": [\n" +
			"      [\n" +
			"        [\n" +
			"          21.2312014,\n" +
			"          48.5978542\n" +
			"        ],\n" +
			"        [\n" +
			"          21.231215,\n" +
			"          48.5978345\n" +
			"        ],\n" +
			"        [\n" +
			"          21.231518,\n" +
			"          48.597396\n" +
			"        ],\n" +
			"        [\n" +
			"          21.232066,\n" +
			"          48.596558\n" +
			"        ],\n" +
			"        [\n" +
			"          21.232831,\n" +
			"          48.595421\n" +
			"        ],\n" +
			"        [\n" +
			"          21.232435,\n" +
			"          48.595373\n" +
			"        ],\n" +
			"        [\n" +
			"          21.232011,\n" +
			"          48.595317\n" +
			"        ],\n" +
			"        [\n" +
			"          21.231995,\n" +
			"          48.595314\n" +
			"        ],\n" +
			"        [\n" +
			"          21.231858,\n" +
			"          48.595288\n" +
			"        ],\n" +
			"        [\n" +
			"          21.231828,\n" +
			"          48.595282\n" +
			"        ],\n" +
			"        [\n" +
			"          21.231902,\n" +
			"          48.595052\n" +
			"        ],\n" +
			"        [\n" +
			"          21.232214,\n" +
			"          48.594085\n" +
			"        ],\n" +
			"        [\n" +
			"          21.232833,\n" +
			"          48.592165\n" +
			"        ],\n" +
			"        [\n" +
			"          21.233196,\n" +
			"          48.591073\n" +
			"        ],\n" +
			"        [\n" +
			"          21.23319,\n" +
			"          48.590907\n" +
			"        ],\n" +
			"        [\n" +
			"          21.233185,\n" +
			"          48.590765\n" +
			"        ],\n" +
			"        [\n" +
			"          21.23318,\n" +
			"          48.59063\n" +
			"        ],\n" +
			"        [\n" +
			"          21.23318,\n" +
			"          48.590589\n" +
			"        ],\n" +
			"        [\n" +
			"          21.234381,\n" +
			"          48.590767\n" +
			"        ],\n" +
			"        [\n" +
			"          21.234421,\n" +
			"          48.590685\n" +
			"        ],\n" +
			"        [\n" +
			"          21.234972,\n" +
			"          48.589558\n" +
			"        ],\n" +
			"        [\n" +
			"          21.235182,\n" +
			"          48.589127\n" +
			"        ],\n" +
			"        [\n" +
			"          21.235215,\n" +
			"          48.589063\n" +
			"        ],\n" +
			"        [\n" +
			"          21.235878,\n" +
			"          48.589163\n" +
			"        ],\n" +
			"        [\n" +
			"          21.236091,\n" +
			"          48.589196\n" +
			"        ],\n" +
			"        [\n" +
			"          21.236167,\n" +
			"          48.588769\n" +
			"        ],\n" +
			"        [\n" +
			"          21.236364,\n" +
			"          48.58767\n" +
			"        ],\n" +
			"        [\n" +
			"          21.236567,\n" +
			"          48.586606\n" +
			"        ],\n" +
			"        [\n" +
			"          21.23675,\n" +
			"          48.585511\n" +
			"        ],\n" +
			"        [\n" +
			"          21.236642,\n" +
			"          48.585207\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2406394,\n" +
			"          48.5860558\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2405614,\n" +
			"          48.586113\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2405122,\n" +
			"          48.5863964\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2407991,\n" +
			"          48.5865775\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2409028,\n" +
			"          48.5867248\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2407754,\n" +
			"          48.5867973\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2404288,\n" +
			"          48.5867325\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2403382,\n" +
			"          48.5867612\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2403301,\n" +
			"          48.5870367\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2399715,\n" +
			"          48.5873833\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2399854,\n" +
			"          48.5874424\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2402315,\n" +
			"          48.5874558\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2404281,\n" +
			"          48.5876564\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2397554,\n" +
			"          48.5877739\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2394504,\n" +
			"          48.5877746\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2393401,\n" +
			"          48.5878365\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2393286,\n" +
			"          48.5879036\n" +
			"        ],\n" +
			"        [\n" +
			"          21.239419,\n" +
			"          48.5880517\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2393896,\n" +
			"          48.5881519\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2390552,\n" +
			"          48.5882755\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2387792,\n" +
			"          48.5881622\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2385335,\n" +
			"          48.5883087\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2385338,\n" +
			"          48.5886915\n" +
			"        ],\n" +
			"        [\n" +
			"          21.238769,\n" +
			"          48.5887932\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2389723,\n" +
			"          48.5890056\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2388043,\n" +
			"          48.5891737\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2384571,\n" +
			"          48.5892327\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2383763,\n" +
			"          48.5896465\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2388115,\n" +
			"          48.5903083\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2388611,\n" +
			"          48.5905271\n" +
			"        ],\n" +
			"        [\n" +
			"          21.23906,\n" +
			"          48.5906594\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2390676,\n" +
			"          48.5907486\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2388654,\n" +
			"          48.5909133\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2390198,\n" +
			"          48.5910631\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2387901,\n" +
			"          48.5912045\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2382567,\n" +
			"          48.5911058\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2380345,\n" +
			"          48.5911956\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2379952,\n" +
			"          48.5912845\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2380501,\n" +
			"          48.5914079\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2383543,\n" +
			"          48.5916053\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2382105,\n" +
			"          48.5918494\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2384536,\n" +
			"          48.5919999\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2385405,\n" +
			"          48.5921986\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2386276,\n" +
			"          48.5922147\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2386548,\n" +
			"          48.5923021\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2385233,\n" +
			"          48.5922768\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2383353,\n" +
			"          48.5923554\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2382761,\n" +
			"          48.592529\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2381524,\n" +
			"          48.5925443\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2380479,\n" +
			"          48.5924736\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2378835,\n" +
			"          48.5924651\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2374867,\n" +
			"          48.5926372\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2373515,\n" +
			"          48.5926443\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2372745,\n" +
			"          48.5927117\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2374673,\n" +
			"          48.5931229\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2375794,\n" +
			"          48.5936778\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2380804,\n" +
			"          48.5940941\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2380305,\n" +
			"          48.5942826\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2381968,\n" +
			"          48.5943283\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2383187,\n" +
			"          48.5944548\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2385102,\n" +
			"          48.5945114\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2385501,\n" +
			"          48.5945813\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2384851,\n" +
			"          48.5949868\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2383998,\n" +
			"          48.5951924\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2379019,\n" +
			"          48.5954863\n" +
			"        ],\n" +
			"        [\n" +
			"          21.237748,\n" +
			"          48.59546\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2375175,\n" +
			"          48.5952255\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2373054,\n" +
			"          48.5952234\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2370008,\n" +
			"          48.5953434\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2369214,\n" +
			"          48.5954456\n" +
			"        ],\n" +
			"        [\n" +
			"          21.236641,\n" +
			"          48.5955888\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2363188,\n" +
			"          48.5957014\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2362504,\n" +
			"          48.595607\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2363883,\n" +
			"          48.5955427\n" +
			"        ],\n" +
			"        [\n" +
			"          21.236143,\n" +
			"          48.5954518\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2360011,\n" +
			"          48.5955936\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2358729,\n" +
			"          48.5956269\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2359283,\n" +
			"          48.5957175\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2357806,\n" +
			"          48.5958096\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2359672,\n" +
			"          48.5959562\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2358245,\n" +
			"          48.5960586\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2358369,\n" +
			"          48.5962153\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2356938,\n" +
			"          48.5962875\n" +
			"        ],\n" +
			"        [\n" +
			"          21.235638,\n" +
			"          48.5966492\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2355375,\n" +
			"          48.5967329\n" +
			"        ],\n" +
			"        [\n" +
			"          21.235752,\n" +
			"          48.5969568\n" +
			"        ],\n" +
			"        [\n" +
			"          21.235282,\n" +
			"          48.597318\n" +
			"        ],\n" +
			"        [\n" +
			"          21.235294,\n" +
			"          48.5973701\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2354097,\n" +
			"          48.5974131\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2354976,\n" +
			"          48.5976026\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2356284,\n" +
			"          48.5976383\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2356691,\n" +
			"          48.5977869\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2357904,\n" +
			"          48.5979112\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2359066,\n" +
			"          48.5983334\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2361313,\n" +
			"          48.5986859\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2361545,\n" +
			"          48.5988397\n" +
			"        ],\n" +
			"        [\n" +
			"          21.236032,\n" +
			"          48.598863\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2359798,\n" +
			"          48.5992439\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2348075,\n" +
			"          48.5988676\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2335944,\n" +
			"          48.5986552\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2332815,\n" +
			"          48.5986759\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2330128,\n" +
			"          48.5984086\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2329897,\n" +
			"          48.598444\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2327537,\n" +
			"          48.5983293\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2313126,\n" +
			"          48.5978882\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2313241,\n" +
			"          48.5978675\n" +
			"        ],\n" +
			"        [\n" +
			"          21.2312014,\n" +
			"          48.5978542\n" +
			"        ]\n" +
			"      ]\n" +
			"    ]\n" +
			"  }\n" +
			"}";
	public static final String LC_GET_NEAREST_LOCATION_EMBED_GEO_JSON = LC_GET_LOCATION_BY_ID_EMBED_GEO_JSON;
	public static final String LC_GET_NEAREST_LOCATION = LC_GET_LOCATION_BY_ID;

	public static final String LC_GET_LOCATION_BY_ID_NOT_FOUND = EMPTY_RESPONSE_BODY;

	public static final String LC_GET_NEAREST_LOCATION_NOT_FOUND = EMPTY_RESPONSE_BODY;

	public static final String LC_GET_LOCATIONS_WITHIN_DISTANCE = LC_GET_LOCATIONS;
	public static final String LC_GET_LOCATIONS_WITHIN_DISTANCE_NOT_FOUND = LC_GET_LOCATIONS_NOT_FOUND;

	public static final String LC_GPS_OCCURRENCE = "{\n" +
			"  \"value\": true\n" +
			"}";
	public static final String LC_GPS_OCCURRENCE_NOT_FOUND = "{\n" +
			"  \"value\": false\n" +
			"}";

	public static final String UC_GET_UPDATES = "{\n" +
			"  \"records\": [\n" +
			"    {\n" +
			"      \"updateId\": 11256,\n" +
			"      \"startedTime\": \"2022-04-05T19:10:17.155325Z\",\n" +
			"      \"finishedTime\": \"2022-04-05T19:51:40.327389Z\",\n" +
			"      \"dataDownloadUrl\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"      \"status\": \"FINISHED\",\n" +
			"      \"trigger\": \"SCHEDULED_STARTUP_UPDATE\",\n" +
			"      \"description\": null,\n" +
			"      \"failedReason\": null\n" +
			"    },\n" +
			"    {\n" +
			"      \"updateId\": 11255,\n" +
			"      \"startedTime\": \"2022-04-05T19:09:34.355455Z\",\n" +
			"      \"finishedTime\": \"2022-04-05T19:54:59.055674Z\",\n" +
			"      \"dataDownloadUrl\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"      \"status\": \"FAILED\",\n" +
			"      \"trigger\": \"SCHEDULED_STARTUP_UPDATE\",\n" +
			"      \"description\": null,\n" +
			"      \"failedReason\": \"Force kill stuck update - running more than 120 minutes.\"\n" +
			"    }\n" +
			"  ],\n" +
			"  \"recordsCount\": 2\n" +
			"}";
	public static final String UC_GET_UPDATE_BY_ID = "{\n" +
			"  \"updateId\": 11256,\n" +
			"  \"startedTime\": \"2022-04-05T19:10:17.155325Z\",\n" +
			"  \"finishedTime\": \"2022-04-05T19:51:40.327389Z\",\n" +
			"  \"dataDownloadUrl\": \"\"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\"\",\n" +
			"  \"status\": \"FINISHED\",\n" +
			"  \"trigger\": \"SCHEDULED_STARTUP_UPDATE\",\n" +
			"  \"description\": null,\n" +
			"  \"failedReason\": null\n" +
			"}";

	public static final String UC_GET_UPDATES_NOT_FOUND = LC_GET_LOCATIONS_NOT_FOUND;
	public static final String UC_GET_UPDATE_BY_ID_NOT_FOUND = EMPTY_RESPONSE_BODY;

	public static final String UPDATE_RECORD_EXAMPLE = "{\n" +
			"  \"updateId\": 1,\n" +
			"  \"startedTime\": \"2022-01-01T12:00:00.000Z\",\n" +
			"  \"finishedTime\": null,\n" +
			"  \"dataDownloadUrl\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"  \"status\": \"RUNNING\",\n" +
			"  \"trigger\": \"MANUAL_UPDATE\",\n" +
			"  \"description\": \"Trying to run manual update of locations data. Default data updater setting will be applied.\" " +
			"}\n";

	public static final String UC_ABORT_UPDATE_EXAMPLE = "{\n" +
			"  \"value\": true\n" +
			"}";

	public static final String UC_MANUAL_UPDATES_REQUEST_DEFAULT = "{\n" +
			"  \"url\": null,\n" +
			"  \"description\": \"Trying to run manual update of locations data. Default data updater setting will be applied.\"\n" +
			"}";
	public static final String UC_MANUAL_UPDATES_REQUEST_WITH_URL_SPECIFIED = "{\n" +
			"  \"url\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"  \"description\": \"Trying to run manual data update of locations data with specified custom URL for OSM data file download.\"\n" +
			"}";
	public static final String UC_MANUAL_UPDATES_REQUEST_WITH_SKIP_DOWNLOAD = "{\n" +
			"    \"url\": null,\n" +
			"    \"description\": \"Trying to run manual update of locations data. Skipping download and gives a file path.\",\n" +
			"    \"skipDownload\": true,\n" +
			"    \"filePath\": \"C:\\\\data\\\\location_service\\\\osm_data\\\\slovakia-latest.osm.pbf\"\n" +
			"}";

}
