import os

import classes.Constants as Constants


class StreamingArtifact:
    name = ''
    type = ''
    file_path = ''

    def __init__(self, full_path, jar_name):
        self.file_path = os.path.join(full_path, jar_name)
        self.set_type(jar_name)
        self.set_module_name(jar_name)

    def set_type(self, jar_name):
        if Constants.SINK in jar_name:
            self.type = Constants.SINK
        elif Constants.SOURCE in jar_name:
            self.type = Constants.SOURCE
        else:
            self.type = Constants.PROCESSOR

    def set_module_name(self, jar_name):
        temp = jar_name
        temp = temp.replace(Constants.CHANNEL_PREFIX, 'pet')
        temp = temp.replace('.jar', '')
        temp = temp.replace('_', '')
        temp = temp.replace('-channel-streaming-module', '')
        self.name = temp
