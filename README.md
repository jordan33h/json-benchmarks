# JSON serialization benchmarks

## Methodology

Participants:

- [Boon](https://github.com/boonproject/boon)
- [Fastjson](https://github.com/alibaba/fastjson)
- [Genson](https://owlike.github.io/genson/)
- [Gson](https://code.google.com/p/google-gson/)
- [Jackson](http://wiki.fasterxml.com/JacksonRelease20)
- [Jackson-afterburner](https://github.com/FasterXML/jackson-module-afterburner)
- [JSR 353 Glassfish](https://json-processing-spec.java.net/)
- [json.org](https://github.com/stleary/JSON-java)
- [Mjson](https://bolerio.github.io/mjson/)
- [minimal-json](https://github.com/ralfstx/minimal-json)
- [Moshi](https://github.com/square/moshi)
- [Tools](https://github.com/wizzardo/Tools)

For testing were selected data with different structure:

- **[citys](src/main/resources/citys.json)** - A large array (29470 items) of simple objects. The compact json representation takes about 2.5 MB.
- **[repos.json](src/main/resources/repos.json)** - An array of four objects with complex structure. The compact json representation takes about 342.8 kB.
- **[user.json](src/main/resources/user.json)** - one object with a complex structure. The compact json representation takes about 4.2 kB.
- **[response.json](src/main/resources/response.json)** - one object with a simple structure. The compact json representation takes about 425 B.

Serialization and deserialization were tested with [POJO](src/main/java/org/bura/benchmarks/json/domain)

## Build and Run

```shell
./gradlew clean && ./gradlew shadow && java -jar target/benchmarks.jar ".*Benchmarks.*"
```

## [Results]


![s.user]
![s.request]
![s.repos]
![s.cities]

![d.user]
![d.request]
![d.repos]
![d.cities]


## Checking performance of Boon

Very different results for boon deserialization into maps and pojos looked suspicious for me, so I've made another benchmark:
'deserialize into map' vs 'deserialize into map and get value of one field'
and compared it with Tools and pojos.

As I expected, Boon uses lazy maps and creates actual values only when requested

![db.user]

[Results]: https://raw.githubusercontent.com/wizzardo/json-benchmarks/master/results
[s.user]: https://cloud.githubusercontent.com/assets/5871626/16258943/6cabc9de-3860-11e6-94ec-70e6724b9de7.png
[s.request]: https://cloud.githubusercontent.com/assets/5871626/16258945/6cce3c62-3860-11e6-9f8f-bf7a9deb5500.png
[s.repos]: https://cloud.githubusercontent.com/assets/5871626/16258946/6ccf3432-3860-11e6-9485-2ef01d3ceb42.png
[s.cities]: https://cloud.githubusercontent.com/assets/5871626/16258944/6cbfb3a4-3860-11e6-8ed4-6bbe9e37a763.png
[d.user]: https://cloud.githubusercontent.com/assets/5871626/16258947/6ccf46ac-3860-11e6-8657-80d448928d20.png
[d.request]: https://cloud.githubusercontent.com/assets/5871626/16258950/6cd849b4-3860-11e6-9cc9-ae7e074fe32e.png
[d.repos]: https://cloud.githubusercontent.com/assets/5871626/16258949/6cd1cddc-3860-11e6-8664-1d9200e2db03.png
[d.cities]: https://cloud.githubusercontent.com/assets/5871626/16258948/6ccfbdf8-3860-11e6-8ec1-6fa95c3e9b23.png
[db.user]: https://cloud.githubusercontent.com/assets/5871626/16258951/6ce28f14-3860-11e6-835a-5bf946523aa2.png
