import uvicorn

from FlightRadar24 import FlightRadar24API
from FlightRadar24.errors import AirportNotFoundError
from fastapi import FastAPI, Depends, HTTPException, status

from dto.flight_dto import Flight, An124Flight, ScheduledFlight
from logger.middleware import LoggingMiddleware
from security.api_key_security import api_key_auth
from utils.flight_utils import get_white_list_plane_codes

app = FastAPI()
app.add_middleware(LoggingMiddleware)
flightradar_api: FlightRadar24API = FlightRadar24API()


@app.get('/ping')
async def pong(api_key=Depends(api_key_auth)):
    return 'pong'


@app.get('/aircraft/{aircraft_code}')
async def get_aircraft_by_code(aircraft_code: str, api_key=Depends(api_key_auth)):
    flights: list = flightradar_api.get_flights(aircraft_type=aircraft_code)
    flights = [An124Flight(flight) for flight in flights]

    for flight in flights:
        airline_name = list(filter(lambda airline: airline['ICAO'] == flight.airline, flightradar_api.get_airlines()))

        if len(airline_name) == 0:
            airline_name = ''
        else:
            airline_name = airline_name[0]['Name']

        flight.airline = airline_name

    return {'flights': flights}


@app.get('/registration/{registration}/{airport_code}')
async def get_flight_by_registration(registration: str, airport_code: str, api_key=Depends(api_key_auth)):
    flights = flightradar_api.get_flights(registration=registration)

    if not flights:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f'Flight with registration {registration} not found'
        )

    flight = flights[0]
    airport = flightradar_api.get_airport(airport_code)
    airline_name = list(filter(lambda airline: airline['ICAO'] == flight.airline_icao, flightradar_api.get_airlines()))

    if len(airline_name) == 0:
        airline_name = 'Unknown'
    else:
        airline_name = airline_name[0]['Name']

    return Flight(flight, airport, airline_name)


@app.get('/airport/flights/{code}')
async def get_white_list_planes(code: str, api_key=Depends(api_key_auth)):
    try:
        details = flightradar_api.get_airport_details(code)
    except AirportNotFoundError:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f'Airport with code {code} not found'
        )
    except ValueError:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f'The code {code} is not valid. It must be the IATA or ICAO of the airport'
        )

    arrivals: list = details['airport']['pluginData']['schedule']['arrivals']['data']
    row_white_list_arrivals: list = get_white_list_plane_codes(arrivals)

    return {'flights': [ScheduledFlight(flight) for flight in row_white_list_arrivals]}




if __name__ == '__main__':
    uvicorn.run(app)
