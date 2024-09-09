// for kotlin-dsl develop
include("generate-liquibase-kwrapper")
// integration-test
include("integration-test")

// production code
// dsl
include("dsl")
// parser
include("script-parser")
include("typesafe-parser")
// serializer
include("serializer-core")
include("script-serializer")
include("typesafe-serializer")
// client
include("client")
