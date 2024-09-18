import os
import json


def get_json_config() -> dict[str, str]:
    with open(os.getcwd() + '/config/whitelist-aircraft-codes.json', 'r') as config_file:
        string_json = config_file.read()

    return json.loads(string_json)['white_list_codes']


def get_aircraft_name_by_code(code: str) -> str:
    return get_json_config()[code]


def get_white_list_plane_codes(row_flights: list) -> list:
    white_list_codes_with_names: dict[str, str] = get_json_config()
    white_list_planes: list = []

    for row_flight in row_flights:
        try:
            code: str = row_flight['flight']['aircraft']['model']['code']
        except TypeError:
            code = 'Unknown'

        if code in white_list_codes_with_names.keys():
            white_list_planes.append(row_flight)

    return white_list_planes


def convert_feet_to_meters(feets: int) -> int:
    return int(feets * 0.3048)


def convert_knots_to_kmh(knots: int) -> int:
    return int(knots * 1.852)
