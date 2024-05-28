import os
import json


def get_json_config() -> list[str]:
    with open(os.getcwd() + '/app/config/white-list-plane-codes.json', 'r') as config_file:
        string_json = config_file.read()

    return json.loads(string_json)['white_list_codes']


def get_white_list_plane_codes(flights: list) -> list:
    white_list_codes: list[str] = get_json_config()
    white_list_planes: list = []

    for flight in flights:
        if flight['code'] in white_list_codes:
            white_list_planes.append(flight)

    return white_list_planes


def convert_feet_to_meters(feets: int) -> int:
    return int(feets * 0.3048)


def convert_knots_to_kmh(knots: int) -> int:
    return int(knots * 1.852)
