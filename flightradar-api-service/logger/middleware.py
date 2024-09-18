from fastapi import Request
from starlette.middleware.base import BaseHTTPMiddleware
from starlette.responses import Response

from logger.logger import logger

import time
import json


class LoggingMiddleware(BaseHTTPMiddleware):

    async def dispatch(self, request: Request, call_next):
        start_time = time.time()

        response = await call_next(request)

        process_time = time.time() - start_time

        body_json = ''

        if response.headers.get('Content-Type') == 'application/json':
            body = b''

            async for chunk in response.body_iterator:
                body += chunk

            body_str = body.decode('utf-8')
            body_json = json.loads(body_str)

            response = Response(content=json.dumps(body_json), media_type="application/json", status_code=response.status_code)

        log_data: dict = {
            'method': request.method,
            'url': request.url.path,
            'status': response.status_code,
        }

        extra_data: dict = {
            'method': request.method,
            'url': request.url.path,
            'status': response.status_code,
            'response_content': body_json,
            'process_time': round(process_time * 1000)
        }

        logger.info(log_data, extra=extra_data)

        return response