import os

import classes.Constants as Constants
import definitions
import custom_definitions


class StreamingModule:
    name = ''
    type = ''
    file_path = ''
    simple_name = ''

    def __init__(self, full_path, jar_name):
        self.name = get_module_name_from_jar_name(jar_name)
        self.simple_name = get_simple_name_from_module_name(self.name)
        self.type = get_type_from_jar_name(jar_name)
        self.file_path = os.path.join(full_path, jar_name)

        if has_custom_module_definition(self.simple_name):
            custom_definition = get_custom_module_definition(self.simple_name)
            self.name = "LOCAL_" + get_module_name_from_jar_name(os.path.basename(custom_definition))
            self.simple_name = get_simple_name_from_module_name(self.name)
            self.file_path = custom_definition


def get_simple_name_from_module_name(module_name):
    for simple_name in definitions.module_names:
        if simple_name in module_name:
            return simple_name


def get_type_from_jar_name(jar_name):
    if Constants.SINK in jar_name:
        return Constants.SINK
    elif Constants.SOURCE in jar_name:
        return Constants.SOURCE
    else:
        return Constants.PROCESSOR


def get_module_name_from_jar_name(jar_name):
    temp = jar_name
    temp = temp.replace(Constants.CHANNEL_PREFIX, 'pet')
    temp = temp.replace('.jar', '')
    temp = temp.replace('_', '')
    temp = temp.replace('-channel-streaming-module', '')
    return temp


def get_custom_module_definition(module_name):
    for custom_definition in custom_definitions.custom_modules:
        if module_name in get_simple_name_from_module_name(get_module_name_from_jar_name(os.path.basename(custom_definition))):
            return custom_definition
    return None


def has_custom_module_definition(module_name):
    return get_custom_module_definition(module_name) is not None
