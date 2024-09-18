from logtail import LogtailHandler
import logging
import os
from dotenv import load_dotenv

load_dotenv()

LOGGING_TOKEN = os.getenv('LOGGING_TOKEN')

logtail_handler = LogtailHandler(source_token=LOGGING_TOKEN)
logger = logging.getLogger()
logger.setLevel(logging.INFO)
logger.handlers = []
logger.addHandler(logtail_handler)