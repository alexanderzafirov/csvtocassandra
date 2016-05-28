package rest

import java.util.UUID

case class CsvModel(
                     observed_date_min_as_infaredate: Int,
                     observed_date_max_as_infaredate: Int,
                     full_weeks_before_departure: Int,
                     carrier_id: Int,
                     searched_cabin_class: String,
                     booking_site_id: Int,
                     booking_site_type_id: Int,
                     is_trip_one_way: Int,
                     trip_origin_airport_id: Int,
                     trip_destination_airport_id: Int,
                     trip_min_stay: Option[Int],
                     trip_price_min: Double,
                     trip_price_max: Double,
                     trip_price_avg: Double,
                     aggregation_count: Int,
                     out_flight_departure_date_as_infaredate: Int,
                     out_flight_departure_time_as_infaretime: Int,
                     out_flight_time_in_minutes: Option[Int],
                     out_sector_count: Int,
                     out_flight_sector_1_flight_code_id: Int,
                     out_flight_sector_2_flight_code_id: Option[Int],
                     out_flight_sector_3_flight_code_id: Option[Int],
                     home_flight_departure_date_as_infaredate: Option[Int],
                     home_flight_departure_time_as_infaretime: Option[Int],
                     home_flight_time_in_minutes: Option[Int],
                     home_sector_count: Int,
                     home_flight_sector_1_flight_code_id: Option[Int],
                     home_flight_sector_2_flight_code_id: Option[Int],
                     home_flight_sector_3_flight_code_id: Option[Int],
                     observation_week: Int,
                     uuid: UUID
                   )
