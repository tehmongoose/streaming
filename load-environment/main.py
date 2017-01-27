import classes.Util as Util
import classes.Constants as Constants
import sys
import os

if Constants.XD_SHELL is None:
    sys.exit("ERROR: XD_SHELL environmental variable is not set")


def main():
    print_title_bar()
    if check_if_artifacts_are_present():
        if 'y' in existing_artifacts_found_question().lower():
            download_artifact()

    response = main_menu()

    if "1" in response:
        if not check_if_artifacts_are_present():
            download_artifact()
        modules = install_modules()
        install_stream(modules)
    elif "2" in response:
        modules = Util.get_streaming_modules()
        install_stream(modules)
    else:
        sys.exit()


def print_title_bar():
    print("********************************************************************************")
    print("********************   Development Environment Setup Tool   ********************")
    print("********************************************************************************")
    print("")


def main_menu():
    print("\t[1] Install modules and streams")
    print("\t[2] Install streams ")
    print("\t[q] Quit")
    print("")
    return input("Choose an option: ")


def existing_artifacts_found_question():
    print("")
    print("The working directory already contains a previously downloaded streaming artifact.")
    return input("Do you want to re-download the artifact from nexus?  ")


def download_artifact():
    Util.remove_and_create_directory(Constants.WORK_DIR)
    Util.download_artifact_from_nexus(Constants.ARTIFACT_URL, Constants.ARTIFACT_ZIP)
    Util.unzip(Constants.ARTIFACT_ZIP, Constants.WORK_DIR)


def install_modules():
    modules = Util.get_streaming_modules()
    Util.write_module_names_file(Constants.WORK_DIR, modules)
    Util.upload_modules(modules)
    return modules


def install_stream(modules):
    stream_definitions = Util.get_stream_definitions(modules)
    Util.upload_streams(stream_definitions)
    Util.deploy_streams(stream_definitions)


def check_if_artifacts_are_present():
    return os.path.exists(Util.get_extract_directory())


main()
