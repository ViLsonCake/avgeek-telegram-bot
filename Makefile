startbot:
	docker compose up --build -d
stopbot:
	docker stop avgeek-telegram-bot
botlogs:
	docker logs avgeek-telegram-bot