import os
import json

class Singleton(type):
    _instances = {}
    def __call__(cls, *args, **kwargs):
        if cls not in cls._instances:
            cls._instances[cls] = super(Singleton, cls).__call__(*args, **kwargs)
        return cls._instances[cls]

class AircraftUtils(metaclass=Singleton):

    __data: dict[str, str]

    def __init__(self):
        self.__data = self.__load_json_data()

    def __load_json_data(self) -> dict[str, str]:
        with open(os.getcwd() + '/config/whitelist-aircraft-codes.json', 'r') as config_file:
            string_json = config_file.read()

        return json.loads(string_json)

    def get_data(self):
        return self.__data

    def get_aircraft_name_by_code(self, code: str) -> str:
        return self.get_data()[code]

    def get_white_list_plane_codes(self, row_flights: list) -> list:
        white_list_codes_with_names: dict[str, str] = self.get_data()
        white_list_planes: list = []

        for row_flight in row_flights:
            try:
                code: str = row_flight['flight']['aircraft']['model']['code']
            except TypeError:
                code = 'Unknown'

            if code in white_list_codes_with_names.keys():
                white_list_planes.append(row_flight)

        return white_list_planes


class AirlinesUtils(metaclass=Singleton):

    __data: dict[str, str]

    def __init__(self):
        self.__data = self.__load_json_data()

    def __load_json_data(self) -> dict[str, str]:
        with open(os.getcwd() + '/config/airlines.json', 'r') as config_file:
            string_json = config_file.read()

        return json.loads(string_json)

    def get_data(self):
        return self.__data

    def get_airline_by_icao(self, icao: str) -> str:
        if icao.upper() not in self.__data:
            return 'Unknown'

        return self.__data[icao]


def convert_feet_to_meters(feets: int) -> int:
    return int(feets * 0.3048)


def convert_knots_to_kmh(knots: int) -> int:
    return int(knots * 1.852)
