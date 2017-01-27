import os

SINK = 'sink'
SOURCE = 'source'
PROCESSOR = 'processor'
CHANNEL_PREFIX = 'pet-channel-streaming-module'

# CONSTANTS
WORK_DIR = 'work'
ARTIFACT_URL = 'http://www.blog.pythonlibrary.org/wp-content/uploads/2012/06/wxDbViewer.zip'
ARTIFACT_NAME = 'pet-chanel-streaming-realtime-artifact'
ARTIFACT_ZIP = ARTIFACT_NAME + '.zip'
XD_HOME = os.environ.get('XD_HOME')
XD_SHELL = os.environ.get('XD_SHELL')
BACKUP_FILE_FOR_MODULE_NAMES = 'moduleNamesBK.txt'
