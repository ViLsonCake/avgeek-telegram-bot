from FlightRadar24.errors import AirportNotFoundError
from fastapi import FastAPI, Depends, HTTPException, status
from security.api_key_security import api_key_auth
from FlightRadar24 import FlightRadar24API
from utils.flight_utils import get_white_list_plane_codes, get_aircraft_name_by_code, convert_feet_to_meters, convert_knots_to_kmh

app = FastAPI()
flightradar_api: FlightRadar24API = FlightRadar24API()


@app.get('/ping')
async def pong(api_key=Depends(api_key_auth)) -> str:
    return 'pong'


@app.get('/aircraft/{aircraft_code}/{airport_code}')
async def get_aircraft_by_code(aircraft_code: str, airport_code: str, api_key=Depends(api_key_auth)) -> dict:
    flights: list = flightradar_api.get_flights(aircraft_type=aircraft_code)
    airport = flightradar_api.get_airport(airport_code)
    flights = [{
        'id': flight.id,
        'code': flight.aircraft_code,
        'callsign': flight.callsign,
        'altitude': convert_feet_to_meters(flight.altitude),
        'ground_speed': convert_knots_to_kmh(flight.ground_speed),
        'latitude': flight.latitude,
        'longitude': flight.longitude,
        'distance': int(flight.get_distance_from(airport))
    } for flight in flights]
    return {'flights': flights}


@app.get('/airport/name/{code}')
async def get_airport_by_code(code: str, api_key=Depends(api_key_auth)) -> dict:
    try:
        return {'name': flightradar_api.get_airport(code).name}
    except AirportNotFoundError:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f'Airport with code "{code}" not found'
        )
    except ValueError:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f'The code "{code}" is not valid. It must be the IATA or ICAO of the airport'
        )


@app.get('/airport/flights/{code}')
async def get_white_list_planes(code: str, api_key=Depends(api_key_auth)):
    try:
        details = flightradar_api.get_airport_details(code)
    except AirportNotFoundError:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f'Airport with code "{code}" not found'
        )
    except ValueError:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f'The code "{code}" is not valid. It must be the IATA or ICAO of the airport'
        )

    arrivals: list = details['airport']['pluginData']['schedule']['arrivals']['data']
    row_white_list_arrivals: list = get_white_list_plane_codes(arrivals)
    formatted_white_list_arrivals: list = []

    for arrival in row_white_list_arrivals:
        id: str = arrival['flight']['identification']['id']

        if id is None:
            id = str(arrival['flight']['identification']['row'])

        airport: str = arrival['flight']['airport']['origin']['name']
        callsign: str = arrival['flight']['identification']['callsign']
        live: bool = arrival['flight']['status']['live']
        text: str = arrival['flight']['status']['text']

        try:
            code: str = arrival['flight']['aircraft']['model']['code']
        except TypeError:
            code = 'Unknown'

        try:
            airline_name: str = arrival['flight']['airline']['name']
        except TypeError:
            airline_name = 'Unknown'

        formatted_white_list_arrivals.append({
            'id': id,
            'code': code,
            'aircraft': get_aircraft_name_by_code(code),
            'airline_name': airline_name,
            'airport': airport,
            'callsign': callsign,
            'live': live,
            'text': text
        })

    return {'flights': formatted_white_list_arrivals}
