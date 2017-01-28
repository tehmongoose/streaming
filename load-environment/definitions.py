# Example custom modules
# put the name, and file location, of the module you want to upload with th
# ["pet-alert-domain-mapper", "C:<path-to-file>/<filename>.jar"]
# Don't commit this with custom_modules
# custom_modules = []
custom_modules = [
    "C:/tmp/pet-chanel-streaming-realtime-artifact/lib/pet-channel-streaming-module-alert-domain-mapper-0.0.18-TEST.jar",
    "C:/tmp/pet-chanel-streaming-realtime-artifact/lib/pet-channel-streaming-module-alert-domain-splitter-0.0.18.jar"
]

module_names = [
    "pet-alert-domain-mapper",
    "pet-alert-mongo-converter",
    "pet-kafka-sink",
    "pet-kafka-source",
    "pet-alert-domain-splitter"
]

stream_definition = [
    "create stream --name equifax-realtime-inbound --definition 'pet-kafka-source --readHeaders=true | pet-alert-domain-mapper | pet-kafka-sink --preseverHeaders=true'",
    "create stream --name equifax-realtime-outbound --definition 'pet-kafka-source | pet-alert-mongo-converter | pet-kafka-sink'"
]
