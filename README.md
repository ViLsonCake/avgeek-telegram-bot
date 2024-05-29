# Avgeek Telegram Bot

___

Since I am an avgeek and plane spotter, I use Flightradar24 like everyone else, but I am missing some features.

- First of all, even though flightradar has an alert system, it does not track flights with flight number N/A. This is a problem for me since almost all An-124s have little to no flight information.
- Secondly it doesn't notify about scheduled flights I am interested in.

___

Of course, there may be simpler solutions to these problems, but I decided to create my own in the form of a Telegram bot.
The bot is able to notify about all flying An-124s, as well as about all wide-body airplanes (and Boeing 757) that will make a scheduled flight to the airport of your choice.

You can try it out for yourself [here](https://t.me/Avgeek_ViLsonCake_Bot).

___

### Stack

- Fastapi (Python)
- Spring boot (Java)
- Postgresql
- Docker

### Installation

1. Create a Telegram bot using [BotFather](https://t.me/BotFather).

2. Create an .env file:
    ```dotenv
    API_KEY=YOUR_API_KEY
    API_URL=http://localhost:8000
    API_DOCKER_URL=http://flightradar-api:8000

    BOT_NAME=YOUR_BOT_NAME
    BOT_TOKEN=YOUR_TOKEN

    POSTGRES_HOST=avgeek_bot_postgres
    POSTGRES_DOCKER_PORT=YOUR_DOCKER_PORT
    POSTGRES_USER=YOUR_POSTGRES_USER
    POSTGRES_PASSWORD=YOUR_POSTGRES_PASSWORD
    POSTGRES_DB=YOUR_DB_NAME
    ```

3. Run the application in Docker:
    ```
    docker-compose up --build -d
    ```
