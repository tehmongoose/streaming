import shutil
import zipfile
import os
import urllib.error
import urllib.parse
import urllib.request
from classes.StreamingArtifact import StreamingModule
import classes.Constants as Constants
import definitions


def unzip(zip_file, extract_directory):
    zip_ref = zipfile.ZipFile(zip_file, 'r')
    zip_ref.extractall(extract_directory)
    zip_ref.close()


def download_artifact_from_nexus(url, destination_file):
    print("")
    print("*********************************************************************************")
    print("*************************DOWNLOADING STREAMING ARTIFACT**************************")
    print("*********************************************************************************")
    f = urllib.request.urlopen(url)
    data = f.read()
    with open(destination_file, "wb") as code:
        code.write(data)


def remove_and_create_directory(dir_to_modify):
    shutil.rmtree(dir_to_modify)
    os.mkdir(dir_to_modify, 777)


def get_streaming_modules():
    lib_path = get_extract_directory()
    result_list = []
    for jar_name in os.listdir(lib_path):
        if Constants.CHANNEL_PREFIX in jar_name:
            module = StreamingModule(lib_path, jar_name)
            result_list.append(module)
    return result_list


def get_extract_directory():
    return os.path.join(os.path.join(Constants.WORK_DIR, Constants.ARTIFACT_NAME), 'lib')


def upload_modules(stream_artifact_list):
    print("")
    print("********************************************************************************")
    print("*******************************UPLOADING MODULES********************************")
    print("********************************************************************************")

    for module in stream_artifact_list:
        upload_module(module)


def upload_module(module):
    command = Constants.XD_SHELL + " module upload --name " + module.name + " --type " + module.type + " --file " + module.file_path
    print("uploading module: " + module.name)
    print("command: " + command)
    print("")
    os.system(command)


def write_module_names_file(write_directory, list_of_streaming_artifacts):
    with open(os.path.join(write_directory, Constants.BACKUP_FILE_FOR_MODULE_NAMES), "w") as bk_file:
        for module in list_of_streaming_artifacts:
            bk_file.write(module.name + '\n')


def get_stream_definitions(modules):
    real_stream_definitions = []
    for stream_def in definitions.stream_definition:
        for module in modules:
            stream_def = stream_def.replace(module.simple_name, module.name)
        real_stream_definitions.append(stream_def)
    return real_stream_definitions


def upload_streams(stream_definitions):
    print("")
    print("********************************************************************************")
    print("*******************************UPLOADING STREAMS********************************")
    print("********************************************************************************")
    for stream in stream_definitions:
        upload_stream(stream)


def upload_stream(stream_definition):
    command = Constants.XD_SHELL + " " + stream_definition
    stream_name = extract_stream_name(stream_definition)
    print("Trying to destroy stream: " + stream_name)
    os.system(Constants.XD_SHELL + " stream destroy " + stream_name)

    print("uploading stream: " + stream_name)
    print("command: " + command)
    os.system(command)

    print("")


def extract_stream_name(stream_definition):
    end_of_name = stream_definition.index("--name ") + 7
    return stream_definition[end_of_name: stream_definition.index(" ", end_of_name)]


def deploy_streams(stream_definitions):
    for stream_definition in stream_definitions:
        deploy_stream(stream_definition)


def deploy_stream(stream_definition):
    stream_name = extract_stream_name(stream_definition)
    command = Constants.XD_SHELL + " stream deploy " + stream_name
    print("")
    print("Deploying Stream: " + stream_name)
    os.system(command)
