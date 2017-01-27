module_names = [
    "pet-alert-domain-mapper",
    "pet-alert-mongo-converter",
    "pet-kafka-sink",
    "pet-kafka-source"
]

stream_definition = [
    "create stream --name equifax-realtime-inbound --definition 'pet-kafka-source --readHeaders=true | pet-alert-domain-mapper | pet-kafka-sink --preseverHeaders=true'",
    "create stream --name equifax-realtime-outbound --definition 'pet-kafka-source | pet-alert-mongo-converter | pet-kafka-sink'"
]
