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
- **[response.json](src/main/resources/response.json)** - one object with a simple structure. The compact json representation takes about 425 B. Main challenge: Maps of Strings, small json

Serialization and deserialization were tested with [POJO](src/main/java/org/bura/benchmarks/json/domain) and Map-like structures

## Build and Run

```shell
./gradlew clean && ./gradlew shadow && java -jar target/benchmarks.jar ".*Benchmarks.*"
```

## [Results]

![serializationbenchmarks citys](https://user-images.githubusercontent.com/5871626/37311902-605d84f0-2649-11e8-969c-a1c4cab6da21.png)
![serializationbenchmarks repos](https://user-images.githubusercontent.com/5871626/37311903-607d2706-2649-11e8-86b9-7d026e070a10.png)
![serializationbenchmarks request](https://user-images.githubusercontent.com/5871626/37311904-6098c6c8-2649-11e8-94a7-ba433d60c397.png)
![serializationbenchmarks user](https://user-images.githubusercontent.com/5871626/37311905-60b807b8-2649-11e8-9494-7ebb03625c89.png)
![deserializationbenchmarks citys](https://user-images.githubusercontent.com/5871626/37311915-670c1a8c-2649-11e8-8906-2f44fc420246.png)
![deserializationbenchmarks repos](https://user-images.githubusercontent.com/5871626/37311917-672a421e-2649-11e8-8cd9-eb65ae559c14.png)
![deserializationbenchmarks request](https://user-images.githubusercontent.com/5871626/37311919-674a2ac0-2649-11e8-8c75-a7dcf0dde9a2.png)
![deserializationbenchmarks user](https://user-images.githubusercontent.com/5871626/37311920-676e68f4-2649-11e8-84a3-91e74b72d069.png)


## Checking performance of Boon

Very different results for boon deserialization into maps and pojos looked suspicious for me, so I've made another benchmark:
'deserialize into map' vs 'deserialize into map and get value of one field'
and compared it with Tools and pojos.

As I expected, Boon uses lazy maps and creates actual values only when requested

![deserializationboonbenchmarks](https://user-images.githubusercontent.com/5871626/37311922-6794f352-2649-11e8-830a-53028bb07bd4.png)

[Results]: https://raw.githubusercontent.com/wizzardo/json-benchmarks/master/results
