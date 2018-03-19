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

![serializationbenchmarks citys](https://user-images.githubusercontent.com/5871626/37598220-f7b52528-2b81-11e8-9931-942c498b3565.png)
![serializationbenchmarks repos](https://user-images.githubusercontent.com/5871626/37598222-f7dd93be-2b81-11e8-8c91-c031b2785f32.png)
![serializationbenchmarks request](https://user-images.githubusercontent.com/5871626/37598223-f7f850c8-2b81-11e8-9ca8-da76ebe24b2e.png)
![serializationbenchmarks user](https://user-images.githubusercontent.com/5871626/37598225-f81882e4-2b81-11e8-9215-a96587bc1cb6.png)
![deserializationbenchmarks citys](https://user-images.githubusercontent.com/5871626/37598235-fd82ebc0-2b81-11e8-8f5f-0e4519a2e662.png)
![deserializationbenchmarks repos](https://user-images.githubusercontent.com/5871626/37598237-fda3d150-2b81-11e8-9b40-939ec2346eab.png)
![deserializationbenchmarks request](https://user-images.githubusercontent.com/5871626/37598238-fdbf8e36-2b81-11e8-898f-a3c868ce052a.png)
![deserializationbenchmarks user](https://user-images.githubusercontent.com/5871626/37598239-fddfebcc-2b81-11e8-8c82-db3b5420b9c8.png)


## Checking performance of Boon

Very different results for boon deserialization into maps and pojos looked suspicious for me, so I've made another benchmark:
'deserialize into map' vs 'deserialize into map and get value of one field'
and compared it with Tools and pojos.

As I expected, Boon uses lazy maps and creates actual values only when requested

![deserializationboonbenchmarks](https://user-images.githubusercontent.com/5871626/37598246-00a70fac-2b82-11e8-877d-479410719036.png)

[Results]: https://raw.githubusercontent.com/wizzardo/json-benchmarks/master/results
