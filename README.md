# JSON serialization benchmarks

## Methodology

Participants:

- [Boon](https://github.com/boonproject/boon)
- [Circe](https://circe.github.io/circe)
- [DSL-JSON](https://github.com/ngs-doo/dsl-json)
- [Fastjson](https://github.com/alibaba/fastjson)
- [Genson](https://owlike.github.io/genson/)
- [Gson](https://code.google.com/p/google-gson/)
- [Jackson](http://wiki.fasterxml.com/JacksonRelease20)
- [Jackson-afterburner](https://github.com/FasterXML/jackson-module-afterburner)
- [JSR 353 Glassfish (javax.json)](https://json-processing-spec.java.net/)
- [json.org](https://github.com/stleary/JSON-java)
- [Json-iterator](http://jsoniter.com)
- [Json-simple](https://github.com/fangyidong/json-simple)
- [Klaxon](https://github.com/cbeust/klaxon)
- [Kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)
- [Mjson](https://bolerio.github.io/mjson/)
- [Minimal-json](https://github.com/ralfstx/minimal-json)
- [Moshi](https://github.com/square/moshi)
- [Tools-json](https://github.com/wizzardo/tools)

For testing were selected data with different structure:

- **[citys](src/main/resources/citys.json)** - A large array (29470 items) of simple objects. The compact json representation takes about 2.5 MB. Main challenge: floating point numbers
- **[repos.json](src/main/resources/repos.json)** - An array of four objects with complex structure. The compact json representation takes about 342.8 kB. Main challenge: a lot of String fields
- **[user.json](src/main/resources/user.json)** - one object with a complex structure. The compact json representation takes about 4.2 kB. Main challenge: dates
- **[request.json](src/main/resources/request.json)** - one object with a simple structure. The compact json representation takes about 425 B. Main challenge: Maps of Strings, small json

Serialization and deserialization were tested with [POJO](src/main/java/org/bura/benchmarks/json/domain) and Map-like structures

## Build and Run

```shell
./gradlew clean && ./gradlew shadowJar && java -jar build/libs/json-benchmarks-all.jar ".*Benchmarks.*"
# or individual benchmarks
./gradlew clean && ./gradlew shadowJar && java -jar build/libs/json-benchmarks-all.jar ".(Des|S)erializationBenchmarks.(pojo|map)_(dslplatform|tools|kotlinx|jsonIterator|klaxon).*"  
```

## [Results]

![serializationbenchmarks user](https://user-images.githubusercontent.com/5871626/40268113-38cd669a-5b68-11e8-937b-25e427ebbc46.png)
![serializationbenchmarks citys](https://user-images.githubusercontent.com/5871626/40268114-38f4d31a-5b68-11e8-95a4-ebfcba2245b2.png)
![serializationbenchmarks repos](https://user-images.githubusercontent.com/5871626/40268115-391c1704-5b68-11e8-843f-c1721118ad7b.png)
![serializationbenchmarks request](https://user-images.githubusercontent.com/5871626/40268116-3941e25e-5b68-11e8-89c3-356fef2c573d.png)
![deserializationbenchmarks user](https://user-images.githubusercontent.com/5871626/40268117-396627a4-5b68-11e8-9c1e-05b68640fe99.png)
![deserializationbenchmarks citys](https://user-images.githubusercontent.com/5871626/40268118-39a16472-5b68-11e8-8aa0-0b2fd3174b91.png)
![deserializationbenchmarks repos](https://user-images.githubusercontent.com/5871626/40268119-39c8aa46-5b68-11e8-902a-02a2f52e46d1.png)
![deserializationbenchmarks request](https://user-images.githubusercontent.com/5871626/40268121-39f3cdac-5b68-11e8-95a4-375ab42a0565.png)


## Checking performance of Boon

Very different results for boon deserialization into maps and pojos looked suspicious for me, so I've made another benchmark:
'deserialize into map' vs 'deserialize into map and get value of one field'
and compared it with Tools and pojos.

As I expected, Boon uses lazy maps and creates actual values only when requested

![deserializationboonbenchmarks](https://user-images.githubusercontent.com/5871626/40268122-3a19ea82-5b68-11e8-9c90-715dfca55afe.png)

[Results]: https://raw.githubusercontent.com/wizzardo/json-benchmarks/master/results
