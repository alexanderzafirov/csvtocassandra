## Rest service exercise

### How to use
1. In the root of the dir run `sbt`
2. Inside sbt execute `runMain rest.RestApp`

REST interface
---

**Retrieve a list of observations for a given carrier id**

* URL

    ### /observations?carrier_id=observation_week={observation_week}&{carrier_id}

* Method

    GET

* Request Params

    - carrier_id - id of the carrier

    - оbservation_week - number of weeks since 01.01.2000

* Success Response - Json array containing the observations:
    - Code - 200

    - Payload:
 ```json
    [
    	{
          "observed_date_min_as_infaredate": 5246,
          "observed_date_max_as_infaredate": 5246,
          "full_weeks_before_departure": 0,
          "carrier_id": 10580711,
          "searched_cabin_class": "E",
          "booking_site_id": 763,
          "booking_site_type_id": 3,
          "is_trip_one_way": 0,
          "trip_origin_airport_id": 5497051,
          "trip_destination_airport_id": 2866723,
          "trip_min_stay": 7,
          "trip_price_min": 23361.880859375,
          "trip_price_max": 23361.880859375,
          "trip_price_avg": 23361.87890625,
          "aggregation_count": 1,
          "out_flight_departure_date_as_infaredate": 5246,
          "out_flight_departure_time_as_infaretime": 84000,
          "out_flight_time_in_minutes": 1345,
          "out_sector_count": 2,
          "out_flight_sector_1_flight_code_id": 2367827,
          "out_flight_sector_2_flight_code_id": 3066094,
          "home_flight_departure_date_as_infaredate": 5253,
          "home_flight_departure_time_as_infaretime": 42300,
          "home_flight_time_in_minutes": 745,
          "home_sector_count": 2,
          "home_flight_sector_1_flight_code_id": 8097869,
          "home_flight_sector_2_flight_code_id": 5871365,
          "observation_week": 749,
          "uuid": "0ba36dc4-5df0-43f2-b7cf-512acc547011"
        },
        ...
    ]
 ```
* Failed Response - Bad request with clarification message:
    - Code - 400

    - Payload:
```
The query parameter 'observation_week' was malformed:
    'asd' is not a valid 32-bit signed integer value
```