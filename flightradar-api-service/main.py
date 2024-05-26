from FlightRadar24.errors import AirportNotFoundError
from fastapi import FastAPI, Depends, HTTPException, status
from security.api_key_security import api_key_auth
from FlightRadar24 import FlightRadar24API
from utils.flight_utils import get_white_list_plane_codes

app = FastAPI()
flightradar_api: FlightRadar24API = FlightRadar24API()


@app.get('/ping')
async def pong(api_key=Depends(api_key_auth)) -> str:
    return 'pong'


@app.get('/aircraft/{icao}')
async def get_aircraft_by_icao(icao: str, api_key=Depends(api_key_auth)) -> dict:
    flights: list = flightradar_api.get_flights(aircraft_type=icao)
    flights = [str(flight) for flight in flights]
    return {'flights': flights}


@app.get('/airport/name/{icao}')
async def get_airport_by_code(icao: str, api_key=Depends(api_key_auth)) -> dict:
    try:
        return {'name': flightradar_api.get_airport(icao).name}
    except AirportNotFoundError:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f'Airport with code "{icao}" not found'
        )
    except ValueError:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f'The code "{icao}" is not valid. It must be the IATA or ICAO of the airport'
        )


@app.get('/airport/flights/{icao}')
async def get_white_list_planes(icao: str, api_key=Depends(api_key_auth)) -> dict:
    try:
        details = flightradar_api.get_airport_details(icao)
    except AirportNotFoundError:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f'Airport with code "{icao}" not found'
        )
    except ValueError:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f'The code "{icao}" is not valid. It must be the IATA or ICAO of the airport'
        )

    arrivals: list = details['airport']['pluginData']['schedule']['arrivals']['data']
    formatted_arrivals: list = []

    for arrival in arrivals:
        code: str = arrival['flight']['aircraft']['model']['code']
        airport: str = arrival['flight']['airport']['origin']['name']

        try:
            airline_name: str = arrival['flight']['airline']['name']
        except TypeError:
            airline_name = 'Unknown'

        formatted_arrivals.append({
            'code': code,
            'airline_name': airline_name,
            'airport': airport
        })

    return {'flights': get_white_list_plane_codes(formatted_arrivals)}