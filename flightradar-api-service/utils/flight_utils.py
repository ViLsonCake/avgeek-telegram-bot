import json


def get_json_config() -> list[str]:
    with open('config/white-list-plane-codes.json', 'r') as config_file:
        string_json = config_file.read()

    return json.loads(string_json)['white_list_codes']


def get_white_list_plane_codes(flights: list) -> list:
    white_list_codes: list[str] = get_json_config()
    white_list_planes: list = []

    for flight in flights:
        if flight['code'] in white_list_codes:
            white_list_planes.append(flight)

    return white_list_planes
