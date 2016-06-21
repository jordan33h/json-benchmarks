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
[s.user]: https://cloud.githubusercontent.com/assets/5871626/16225391/93ca1eb8-37a6-11e6-860a-5f199b641f6d.png
[s.request]: https://cloud.githubusercontent.com/assets/5871626/16225390/93c8199c-37a6-11e6-8f3c-5b373ed70888.png
[s.repos]: https://cloud.githubusercontent.com/assets/5871626/16225389/93c76394-37a6-11e6-878a-473f911ba3f3.png
[s.cities]: https://cloud.githubusercontent.com/assets/5871626/16225386/93b5823c-37a6-11e6-9cf2-cea266bf4296.png
[d.user]: https://cloud.githubusercontent.com/assets/5871626/16225383/93b3a214-37a6-11e6-9520-03cd90f3a5c8.png
[d.request]: https://cloud.githubusercontent.com/assets/5871626/16225387/93b57800-37a6-11e6-94b9-4897ed3c06ec.png
[d.repos]: https://cloud.githubusercontent.com/assets/5871626/16225385/93b4fe52-37a6-11e6-9ee7-7cfb9b84372e.png
[d.cities]: https://cloud.githubusercontent.com/assets/5871626/16225384/93b3f03e-37a6-11e6-9912-d0d3cffe38c5.png
[db.user]: https://cloud.githubusercontent.com/assets/5871626/16225388/93b7f738-37a6-11e6-9c16-1c40e9cb41a0.png
