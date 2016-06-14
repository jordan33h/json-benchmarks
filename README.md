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

Serialization and deserialization were tested with [POJO](src/main/java/org/bura/benchmarks/json/domain)

## Build and Run

```shell
./gradlew clean && ./gradlew shadow && java -jar target/benchmarks.jar ".*Benchmarks.*"
```

## Results
bigger number is better

![s.user]
![s.request]
![s.repos]
![s.cities]

![d.user]
![d.request]
![d.repos]
![d.cities]

```
Benchmark                                      (resourceName)   Mode  Cnt        Score      Error  Units
DeserializationBenchmarks.boon_map                      citys  thrpt   10      92.124 ±     2.782  ops/s
DeserializationBenchmarks.boon_map                      repos  thrpt   10    1510.174 ±   108.628  ops/s
DeserializationBenchmarks.boon_map                       user  thrpt   10   66459.439 ±  4748.963  ops/s
DeserializationBenchmarks.boon_map                    request  thrpt   10  241819.009 ± 19403.229  ops/s
DeserializationBenchmarks.boon_pojo                     citys  thrpt   10      32.815 ±     0.670  ops/s
DeserializationBenchmarks.boon_pojo                     repos  thrpt   10     649.059 ±    38.529  ops/s
DeserializationBenchmarks.boon_pojo                      user  thrpt   10   12534.207 ±   999.826  ops/s
DeserializationBenchmarks.boon_pojo                   request  thrpt   10  154424.298 ± 13356.475  ops/s
DeserializationBenchmarks.fastjson_map                  citys  thrpt   10      36.267 ±     1.665  ops/s
DeserializationBenchmarks.fastjson_map                  repos  thrpt   10     732.092 ±    41.807  ops/s
DeserializationBenchmarks.fastjson_map                   user  thrpt   10   37766.821 ±  1768.354  ops/s
DeserializationBenchmarks.fastjson_map                request  thrpt   10  443414.072 ± 55618.553  ops/s
DeserializationBenchmarks.fastjson_pojo                 citys  thrpt   10      35.373 ±     1.348  ops/s
DeserializationBenchmarks.fastjson_pojo                 repos  thrpt   10     307.859 ±    22.505  ops/s
DeserializationBenchmarks.fastjson_pojo                  user  thrpt   10   16065.814 ±   707.714  ops/s
DeserializationBenchmarks.fastjson_pojo               request  thrpt   10  373858.829 ± 28763.854  ops/s
DeserializationBenchmarks.gson_map                      citys  thrpt   10      28.619 ±     0.819  ops/s
DeserializationBenchmarks.gson_map                      repos  thrpt   10     426.238 ±    24.049  ops/s
DeserializationBenchmarks.gson_map                       user  thrpt   10   19249.799 ±  2020.379  ops/s
DeserializationBenchmarks.gson_map                    request  thrpt   10  356233.603 ± 34938.526  ops/s
DeserializationBenchmarks.gson_pojo                     citys  thrpt   10      27.754 ±     1.040  ops/s
DeserializationBenchmarks.gson_pojo                     repos  thrpt   10     446.110 ±    34.616  ops/s
DeserializationBenchmarks.gson_pojo                      user  thrpt   10    6850.039 ±   303.603  ops/s
DeserializationBenchmarks.gson_pojo                   request  thrpt   10  311209.274 ± 27088.052  ops/s
DeserializationBenchmarks.jackson_afterburner           citys  thrpt   10      40.077 ±     2.604  ops/s
DeserializationBenchmarks.jackson_afterburner           repos  thrpt   10     561.113 ±    38.615  ops/s
DeserializationBenchmarks.jackson_afterburner            user  thrpt   10    8461.624 ±  1018.068  ops/s
DeserializationBenchmarks.jackson_afterburner         request  thrpt   10  418928.329 ± 20651.874  ops/s
DeserializationBenchmarks.jackson_map                   citys  thrpt   10      35.831 ±     1.508  ops/s
DeserializationBenchmarks.jackson_map                   repos  thrpt   10     737.106 ±    48.443  ops/s
DeserializationBenchmarks.jackson_map                    user  thrpt   10   37015.024 ±  3382.449  ops/s
DeserializationBenchmarks.jackson_map                 request  thrpt   10  437505.666 ± 47528.644  ops/s
DeserializationBenchmarks.jackson_pojo                  citys  thrpt   10      36.939 ±     1.230  ops/s
DeserializationBenchmarks.jackson_pojo                  repos  thrpt   10     498.170 ±    40.602  ops/s
DeserializationBenchmarks.jackson_pojo                   user  thrpt   10    8283.572 ±   882.760  ops/s
DeserializationBenchmarks.jackson_pojo                request  thrpt   10  405656.272 ± 31775.924  ops/s
DeserializationBenchmarks.javax_glassfish_map           citys  thrpt   10      33.221 ±     1.533  ops/s
DeserializationBenchmarks.javax_glassfish_map           repos  thrpt   10     669.010 ±    31.989  ops/s
DeserializationBenchmarks.javax_glassfish_map            user  thrpt   10   23322.000 ±  2182.826  ops/s
DeserializationBenchmarks.javax_glassfish_map         request  thrpt   10  111137.212 ±  8751.833  ops/s
DeserializationBenchmarks.json_map                      citys  thrpt   10      17.481 ±     0.667  ops/s
DeserializationBenchmarks.json_map                      repos  thrpt   10     210.346 ±    13.448  ops/s
DeserializationBenchmarks.json_map                       user  thrpt   10   14346.811 ±  1740.396  ops/s
DeserializationBenchmarks.json_map                    request  thrpt   10  159923.569 ± 12216.073  ops/s
DeserializationBenchmarks.tools_map                     citys  thrpt   10      57.553 ±     2.749  ops/s
DeserializationBenchmarks.tools_map                     repos  thrpt   10    1125.499 ±    31.880  ops/s
DeserializationBenchmarks.tools_map                      user  thrpt   10   49184.286 ±  4184.566  ops/s
DeserializationBenchmarks.tools_map                   request  thrpt   10  691936.284 ± 72730.885  ops/s
DeserializationBenchmarks.tools_pojo                    citys  thrpt   10      62.132 ±     3.411  ops/s
DeserializationBenchmarks.tools_pojo                    repos  thrpt   10     966.364 ±    62.248  ops/s
DeserializationBenchmarks.tools_pojo                     user  thrpt   10   22088.584 ±  1861.338  ops/s
DeserializationBenchmarks.tools_pojo                  request  thrpt   10  505424.646 ± 37007.312  ops/s


Benchmark                                      (resourceName)   Mode  Cnt        Score      Error  Units
SerializationBenchmarks.boon_map                        citys  thrpt   10      23.025 ±     0.612  ops/s
SerializationBenchmarks.boon_map                        repos  thrpt   10     647.350 ±    28.966  ops/s
SerializationBenchmarks.boon_map                         user  thrpt   10   46674.814 ±  4710.170  ops/s
SerializationBenchmarks.boon_map                      request  thrpt   10  310101.661 ± 37717.120  ops/s
SerializationBenchmarks.boon_pojo                       citys  thrpt   10      24.132 ±     0.924  ops/s
SerializationBenchmarks.boon_pojo                       repos  thrpt   10     572.035 ±    33.325  ops/s
SerializationBenchmarks.boon_pojo                        user  thrpt   10    8475.366 ±   390.940  ops/s
SerializationBenchmarks.boon_pojo                     request  thrpt   10  258892.974 ± 25900.712  ops/s
SerializationBenchmarks.fastjson_map                    citys  thrpt   10      18.090 ±     0.828  ops/s
SerializationBenchmarks.fastjson_map                    repos  thrpt   10     618.079 ±    46.532  ops/s
SerializationBenchmarks.fastjson_map                     user  thrpt   10   40905.332 ±  3432.967  ops/s
SerializationBenchmarks.fastjson_map                  request  thrpt   10  703366.778 ± 43934.711  ops/s
SerializationBenchmarks.fastjson_pojo                   citys  thrpt   10      25.836 ±     1.226  ops/s
SerializationBenchmarks.fastjson_pojo                   repos  thrpt   10     586.694 ±    43.187  ops/s
SerializationBenchmarks.fastjson_pojo                    user  thrpt   10   10794.952 ±  1039.016  ops/s
SerializationBenchmarks.fastjson_pojo                 request  thrpt   10  738175.401 ± 81939.044  ops/s
SerializationBenchmarks.gson_map                        citys  thrpt   10      17.614 ±     0.741  ops/s
SerializationBenchmarks.gson_map                        repos  thrpt   10     554.489 ±    36.948  ops/s
SerializationBenchmarks.gson_map                         user  thrpt   10   23679.734 ±  2186.142  ops/s
SerializationBenchmarks.gson_map                      request  thrpt   10  280437.014 ± 22409.739  ops/s
SerializationBenchmarks.gson_pojo                       citys  thrpt   10      18.457 ±     0.923  ops/s
SerializationBenchmarks.gson_pojo                       repos  thrpt   10     471.067 ±    27.285  ops/s
SerializationBenchmarks.gson_pojo                        user  thrpt   10   12002.206 ±  1183.890  ops/s
SerializationBenchmarks.gson_pojo                     request  thrpt   10  242472.037 ± 20113.190  ops/s
SerializationBenchmarks.jackson_afterburner             citys  thrpt   10      38.346 ±     1.719  ops/s
SerializationBenchmarks.jackson_afterburner             repos  thrpt   10     798.438 ±    40.475  ops/s
SerializationBenchmarks.jackson_afterburner              user  thrpt   10   21071.424 ±  1644.512  ops/s
SerializationBenchmarks.jackson_afterburner           request  thrpt   10  747770.066 ± 63465.452  ops/s
SerializationBenchmarks.jackson_map                     citys  thrpt   10      30.194 ±     1.370  ops/s
SerializationBenchmarks.jackson_map                     repos  thrpt   10     723.721 ±    44.091  ops/s
SerializationBenchmarks.jackson_map                      user  thrpt   10   66160.116 ±  5334.092  ops/s
SerializationBenchmarks.jackson_map                   request  thrpt   10  727394.197 ± 91712.438  ops/s
SerializationBenchmarks.jackson_pojo                    citys  thrpt   10      34.718 ±     1.553  ops/s
SerializationBenchmarks.jackson_pojo                    repos  thrpt   10     656.510 ±    30.895  ops/s
SerializationBenchmarks.jackson_pojo                     user  thrpt   10   19139.824 ±  1838.763  ops/s
SerializationBenchmarks.jackson_pojo                  request  thrpt   10  703588.987 ± 60259.509  ops/s
SerializationBenchmarks.javax_glassfish_map             citys  thrpt   10      30.175 ±     2.294  ops/s
SerializationBenchmarks.javax_glassfish_map             repos  thrpt   10     608.041 ±    32.070  ops/s
SerializationBenchmarks.javax_glassfish_map              user  thrpt   10   33351.198 ±  2797.027  ops/s
SerializationBenchmarks.javax_glassfish_map           request  thrpt   10  132750.136 ± 11294.732  ops/s
SerializationBenchmarks.json_map                        citys  thrpt   10      14.873 ±     0.327  ops/s
SerializationBenchmarks.json_map                        repos  thrpt   10     222.693 ±    15.509  ops/s
SerializationBenchmarks.json_map                         user  thrpt   10   15868.007 ±  1355.026  ops/s
SerializationBenchmarks.json_map                      request  thrpt   10  173788.409 ±  8995.319  ops/s
SerializationBenchmarks.tools_map                       citys  thrpt   10      29.770 ±     1.329  ops/s
SerializationBenchmarks.tools_map                       repos  thrpt   10     944.926 ±    69.908  ops/s
SerializationBenchmarks.tools_map                        user  thrpt   10   58322.506 ±  4014.109  ops/s
SerializationBenchmarks.tools_map                     request  thrpt   10  761050.359 ± 66753.758  ops/s
SerializationBenchmarks.tools_pojo                      citys  thrpt   10      39.769 ±     1.231  ops/s
SerializationBenchmarks.tools_pojo                      repos  thrpt   10    1048.896 ±    93.552  ops/s
SerializationBenchmarks.tools_pojo                       user  thrpt   10   29486.289 ±  2576.968  ops/s
SerializationBenchmarks.tools_pojo                    request  thrpt   10  776922.163 ± 93098.591  ops/s
```

[s.user]: https://cloud.githubusercontent.com/assets/5871626/16057025/54b4aa64-3278-11e6-8153-2980d2531ba8.png
[s.request]: https://cloud.githubusercontent.com/assets/5871626/16057021/54b1e338-3278-11e6-976d-119e96b20c4d.png
[s.repos]: https://cloud.githubusercontent.com/assets/5871626/16057024/54b3ff06-3278-11e6-8440-ae6123d3af61.png
[s.cities]: https://cloud.githubusercontent.com/assets/5871626/16057022/54b34eee-3278-11e6-8741-3c6dcf92cf06.png
[d.user]: https://cloud.githubusercontent.com/assets/5871626/16057026/54b6568e-3278-11e6-83ef-5475fa7ced08.png
[d.request]: https://cloud.githubusercontent.com/assets/5871626/16057023/54b3e548-3278-11e6-8335-0b2199ea0311.png
[d.repos]: https://cloud.githubusercontent.com/assets/5871626/16057027/54ce5f40-3278-11e6-9552-2aaa9e4926ad.png
[d.cities]: https://cloud.githubusercontent.com/assets/5871626/16057028/54cf713c-3278-11e6-88d6-ef1c36558f05.png
