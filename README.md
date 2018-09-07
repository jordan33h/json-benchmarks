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

![serializationbenchmarks user](https://user-images.githubusercontent.com/5871626/45239308-11c24780-b2e5-11e8-8659-76c795fc5859.png)
![serializationbenchmarks citys](https://user-images.githubusercontent.com/5871626/45239309-125ade00-b2e5-11e8-8162-6dade7c257a2.png)
![serializationbenchmarks repos](https://user-images.githubusercontent.com/5871626/45239310-125ade00-b2e5-11e8-8ef0-9b7342bf27fb.png)
![serializationbenchmarks request](https://user-images.githubusercontent.com/5871626/45239311-125ade00-b2e5-11e8-9709-f109e95de3ff.png)
![deserializationbenchmarks user](https://user-images.githubusercontent.com/5871626/45239312-12f37480-b2e5-11e8-9a8d-98d08442e439.png)
![deserializationbenchmarks citys](https://user-images.githubusercontent.com/5871626/45239313-12f37480-b2e5-11e8-9474-656f8a4587d8.png)
![deserializationbenchmarks repos](https://user-images.githubusercontent.com/5871626/45239314-12f37480-b2e5-11e8-9817-838501776158.png)
![deserializationbenchmarks request](https://user-images.githubusercontent.com/5871626/45239315-138c0b00-b2e5-11e8-951f-9064773ef0dd.png)


## Checking performance of Boon

Very different results for boon deserialization into maps and pojos looked suspicious for me, so I've made another benchmark:
'deserialize into map' vs 'deserialize into map and get value of one field'
and compared it with Tools and pojos.

As I expected, Boon uses lazy maps and creates actual values only when requested

![deserializationboonbenchmarks](https://user-images.githubusercontent.com/5871626/45239316-138c0b00-b2e5-11e8-937b-474b65317436.png)

[Results]: https://raw.githubusercontent.com/wizzardo/json-benchmarks/master/results
