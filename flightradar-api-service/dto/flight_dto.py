import FlightRadar24

from utils.flight_utils import convert_knots_to_kmh, convert_feet_to_meters, AircraftUtils

aircraft_utils: AircraftUtils = AircraftUtils()

class Flight:
    id: str
    code: str
    callsign: str
    aircraft: str
    registration: str
    origin_airport_iata: str
    destination_airport_iata: str
    airline: str
    altitude: int
    ground_speed: int
    vertical_speed: int
    latitude: float
    longitude: float
    distance: int

    def __init__(self, flight: FlightRadar24.Flight, airport: FlightRadar24.Airport, airline='Unknown'):
        self.id = flight.id
        self.code = flight.aircraft_code
        self.callsign = flight.callsign
        self.airline = airline
        self.aircraft = aircraft_utils.get_aircraft_name_by_code(self.code)
        self.registration = flight.registration
        self.origin_airport_iata = flight.origin_airport_iata
        self.destination_airport_iata = flight.destination_airport_iata
        self.altitude = convert_feet_to_meters(flight.altitude)
        self.ground_speed = convert_knots_to_kmh(flight.ground_speed)
        self.vertical_speed = flight.vertical_speed
        self.latitude = flight.latitude
        self.longitude = flight.longitude
        self.distance = int(flight.get_distance_from(airport))


class An124Flight:
    id: str
    code: str
    callsign: str
    aircraft: str
    airline: str
    origin_airport_iata: str
    destination_airport_iata: str
    altitude: int
    ground_speed: int
    vertical_speed: int
    latitude: float
    longitude: float

    def __init__(self, flight: FlightRadar24.Flight):
        self.id = flight.id
        self.code = flight.aircraft_code
        self.callsign = flight.callsign
        self.aircraft = aircraft_utils.get_aircraft_name_by_code(self.code)
        self.airline = flight.airline_icao
        self.origin_airport_iata = flight.origin_airport_iata
        self.destination_airport_iata = flight.destination_airport_iata
        self.altitude = convert_feet_to_meters(flight.altitude)
        self.ground_speed = convert_knots_to_kmh(flight.ground_speed)
        self.vertical_speed = flight.vertical_speed
        self.latitude = flight.latitude
        self.longitude = flight.longitude


class ScheduledFlight:
    id: str
    code: str
    aircraft: str
    airline_name: str
    airport: str
    iata: str
    icao: str
    callsign: str
    registration: str
    live: bool
    text: str

    def __init__(self, row_flight: dict):
        self.id: str = row_flight['flight']['identification']['id']

        if self.id is None:
            self.id = str(row_flight['flight']['identification']['row'])

        self.airport: str = row_flight['flight']['airport']['origin']['name']
        self.iata: str = row_flight['flight']['airport']['origin']['code']['iata']
        self.icao: str = row_flight['flight']['airport']['origin']['code']['icao']
        self.callsign: str = row_flight['flight']['identification']['callsign']
        self.registration: str = row_flight['flight']['aircraft']['registration']
        self.live: bool = row_flight['flight']['status']['live']
        self.text: str = row_flight['flight']['status']['text']

        try:
            self.code: str = row_flight['flight']['aircraft']['model']['code']
            self.aircraft = aircraft_utils.get_aircraft_name_by_code(self.code)
        except TypeError:
            self.code = 'Unknown'
            self.aircraft = 'Unknown'

        try:
            self.airline_name: str = row_flight['flight']['airline']['name']
        except TypeError:
            self.airline_name = 'Unknown'
