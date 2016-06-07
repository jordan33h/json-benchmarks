# JSON serialization benchmarks

## Methodology

Participants:

- [Jackson](http://wiki.fasterxml.com/JacksonRelease20)
- [Gson](https://code.google.com/p/google-gson/)
- [Tools](https://github.com/wizzardo/Tools)

For testing were selected data with different structure:

- **[citys](src/main/resources/citys.json)** - A large array (29470 items) of simple objects. The compact json representation takes about 2.5 MB.
- **[repos.json](src/main/resources/repos.json)** - An array of four objects with complex structure. The compact json representation takes about 342.8 kB.
- **[user.json](src/main/resources/user.json)** - one object with a complex structure. The compact json representation takes about 4.2 kB.
- **[response.json](src/main/resources/response.json)** - one object with a simple structure. The compact json representation takes about 425 B.

Serialization and deserialization were tested with [POJO](src/main/java/org/bura/benchmarks/json/domain):

## Build and Run

```shell
./gradlew clean && ./gradlew shadow && java -jar target/benchmarks.jar ".*Benchmarks.*"
```

## Results
```
Benchmark                        (resourceName)   Mode  Cnt        Score      Error  Units
DeserializationBenchmarks.gson              citys  thrpt   10      28.201 ±     0.833  ops/s
DeserializationBenchmarks.gson              repos  thrpt   10     454.902 ±    34.062  ops/s
DeserializationBenchmarks.gson               user  thrpt   10    7506.928 ±   377.153  ops/s
DeserializationBenchmarks.gson            request  thrpt   10  318983.389 ± 12859.417  ops/s
DeserializationBenchmarks.jackson           citys  thrpt   10      34.983 ±     1.464  ops/s
DeserializationBenchmarks.jackson           repos  thrpt   10     493.302 ±    30.280  ops/s
DeserializationBenchmarks.jackson            user  thrpt   10    8291.480 ±   681.102  ops/s
DeserializationBenchmarks.jackson         request  thrpt   10  402340.314 ± 26640.525  ops/s
DeserializationBenchmarks.tools             citys  thrpt   10      61.449 ±     1.902  ops/s
DeserializationBenchmarks.tools             repos  thrpt   10     934.867 ±    59.520  ops/s
DeserializationBenchmarks.tools              user  thrpt   10   21561.766 ±  2373.121  ops/s
DeserializationBenchmarks.tools           request  thrpt   10  501078.404 ± 48615.667  ops/s


Benchmark                        (resourceName)   Mode  Cnt        Score      Error  Units
SerializationBenchmarks.gson              citys  thrpt   10      20.528 ±     0.702  ops/s
SerializationBenchmarks.gson              repos  thrpt   10     440.081 ±    17.025  ops/s
SerializationBenchmarks.gson               user  thrpt   10   12621.542 ±   468.992  ops/s
SerializationBenchmarks.gson            request  thrpt   10  243215.893 ± 23153.946  ops/s
SerializationBenchmarks.jackson           citys  thrpt   10      37.679 ±     1.611  ops/s
SerializationBenchmarks.jackson           repos  thrpt   10     619.034 ±    50.490  ops/s
SerializationBenchmarks.jackson            user  thrpt   10   19754.257 ±  1554.809  ops/s
SerializationBenchmarks.jackson         request  thrpt   10  720663.570 ± 32423.289  ops/s
SerializationBenchmarks.tools             citys  thrpt   10      43.276 ±     1.575  ops/s
SerializationBenchmarks.tools             repos  thrpt   10    1001.276 ±    77.295  ops/s
SerializationBenchmarks.tools              user  thrpt   10   29063.444 ±  1953.410  ops/s
SerializationBenchmarks.tools           request  thrpt   10  814572.305 ± 36677.162  ops/s
```

![s.user]
![s.request]
![s.repos]
![s.cities]

![d.user]
![d.request]
![d.repos]
![d.cities]

[s.user]: https://cloud.githubusercontent.com/assets/5871626/15876999/5e9c9166-2d11-11e6-83ae-040bd3039d2b.png
[s.request]: https://cloud.githubusercontent.com/assets/5871626/15876998/5e9b61ce-2d11-11e6-82da-46a52079d40c.png
[s.repos]: https://cloud.githubusercontent.com/assets/5871626/15877000/5e9d0b46-2d11-11e6-8e57-6738a170d23d.png
[s.cities]: https://cloud.githubusercontent.com/assets/5871626/15877002/5e9e40a6-2d11-11e6-8d90-b65ff95ccfaa.png
[d.user]: https://cloud.githubusercontent.com/assets/5871626/15877005/5eba05ca-2d11-11e6-85d6-79f912f3279c.png
[d.request]: https://cloud.githubusercontent.com/assets/5871626/15877004/5eb4a8f0-2d11-11e6-92eb-c8384039764f.png
[d.repos]: https://cloud.githubusercontent.com/assets/5871626/15877001/5e9e1ca2-2d11-11e6-87e6-1764f002f817.png
[d.cities]: https://cloud.githubusercontent.com/assets/5871626/15877003/5ea03032-2d11-11e6-8678-caced33a9ff7.png
