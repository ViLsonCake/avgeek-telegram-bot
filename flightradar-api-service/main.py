from fastapi import FastAPI

app = FastAPI()


@app.get('/ping')
async def pong() -> str:
    return 'pong'

