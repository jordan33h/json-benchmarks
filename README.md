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
Benchmark                                           (resourceName)   Mode  Cnt        Score      Error  Units
DeserializationBenchmarks.map_boon                           citys  thrpt   10      92.416 ±     4.573  ops/s
DeserializationBenchmarks.map_boon                           repos  thrpt   10    1512.458 ±    93.347  ops/s
DeserializationBenchmarks.map_boon                            user  thrpt   10   67862.626 ±  3566.022  ops/s
DeserializationBenchmarks.map_boon                         request  thrpt   10  236559.222 ± 17375.593  ops/s
DeserializationBenchmarks.map_fastjson                       citys  thrpt   10      35.848 ±     1.990  ops/s
DeserializationBenchmarks.map_fastjson                       repos  thrpt   10     752.717 ±    51.519  ops/s
DeserializationBenchmarks.map_fastjson                        user  thrpt   10   37976.075 ±  3661.871  ops/s
DeserializationBenchmarks.map_fastjson                     request  thrpt   10  447679.891 ± 39671.374  ops/s
DeserializationBenchmarks.map_genson                         citys  thrpt   10      31.364 ±     1.014  ops/s
DeserializationBenchmarks.map_genson                         repos  thrpt   10     759.634 ±    73.828  ops/s
DeserializationBenchmarks.map_genson                          user  thrpt   10   32267.039 ±  3002.833  ops/s
DeserializationBenchmarks.map_genson                       request  thrpt   10  360364.735 ± 30425.518  ops/s
DeserializationBenchmarks.map_gson                           citys  thrpt   10      28.538 ±     0.787  ops/s
DeserializationBenchmarks.map_gson                           repos  thrpt   10     431.504 ±    27.071  ops/s
DeserializationBenchmarks.map_gson                            user  thrpt   10   22252.722 ±  1303.100  ops/s
DeserializationBenchmarks.map_gson                         request  thrpt   10  364501.232 ± 33065.402  ops/s
DeserializationBenchmarks.map_jackson                        citys  thrpt   10      33.528 ±     0.996  ops/s
DeserializationBenchmarks.map_jackson                        repos  thrpt   10     743.962 ±    73.413  ops/s
DeserializationBenchmarks.map_jackson                         user  thrpt   10   35388.983 ±  3874.862  ops/s
DeserializationBenchmarks.map_jackson                      request  thrpt   10  424394.706 ± 31629.341  ops/s
DeserializationBenchmarks.map_javax_glassfish                citys  thrpt   10      17.650 ±     0.477  ops/s
DeserializationBenchmarks.map_javax_glassfish                repos  thrpt   10     697.515 ±    31.293  ops/s
DeserializationBenchmarks.map_javax_glassfish                 user  thrpt   10   15372.742 ±   769.503  ops/s
DeserializationBenchmarks.map_javax_glassfish              request  thrpt   10   33198.555 ±  1065.382  ops/s
DeserializationBenchmarks.map_json_org                       citys  thrpt   10      17.605 ±     1.322  ops/s
DeserializationBenchmarks.map_json_org                       repos  thrpt   10     207.746 ±    11.023  ops/s
DeserializationBenchmarks.map_json_org                        user  thrpt   10   14305.196 ±  1242.410  ops/s
DeserializationBenchmarks.map_json_org                     request  thrpt   10  167663.899 ± 12231.629  ops/s
DeserializationBenchmarks.map_mjson                          citys  thrpt   10      24.139 ±     0.966  ops/s
DeserializationBenchmarks.map_mjson                          repos  thrpt   10     283.551 ±    20.260  ops/s
DeserializationBenchmarks.map_mjson                           user  thrpt   10   18843.066 ±  1647.589  ops/s
DeserializationBenchmarks.map_mjson                        request  thrpt   10  183833.957 ± 15543.600  ops/s
DeserializationBenchmarks.map_tools                          citys  thrpt   10      56.957 ±     1.850  ops/s
DeserializationBenchmarks.map_tools                          repos  thrpt   10    1025.764 ±    61.112  ops/s
DeserializationBenchmarks.map_tools                           user  thrpt   10   50355.759 ±  3131.490  ops/s
DeserializationBenchmarks.map_tools                        request  thrpt   10  686370.067 ± 90588.605  ops/s
DeserializationBenchmarks.pojo_boon                          citys  thrpt   10      34.525 ±     1.009  ops/s
DeserializationBenchmarks.pojo_boon                          repos  thrpt   10     633.808 ±    41.359  ops/s
DeserializationBenchmarks.pojo_boon                           user  thrpt   10   12749.121 ±  1469.797  ops/s
DeserializationBenchmarks.pojo_boon                        request  thrpt   10  157511.610 ± 15192.521  ops/s
DeserializationBenchmarks.pojo_fastjson                      citys  thrpt   10      47.658 ±     1.750  ops/s
DeserializationBenchmarks.pojo_fastjson                      repos  thrpt   10     297.896 ±    22.057  ops/s
DeserializationBenchmarks.pojo_fastjson                       user  thrpt   10   16836.054 ±  1705.091  ops/s
DeserializationBenchmarks.pojo_fastjson                    request  thrpt   10  359386.298 ± 38154.893  ops/s
DeserializationBenchmarks.pojo_genson                        citys  thrpt   10      40.348 ±     1.573  ops/s
DeserializationBenchmarks.pojo_genson                        repos  thrpt   10     602.051 ±    57.129  ops/s
DeserializationBenchmarks.pojo_genson                         user  thrpt   10   15920.810 ±  1674.750  ops/s
DeserializationBenchmarks.pojo_genson                      request  thrpt   10  419762.930 ± 31092.933  ops/s
DeserializationBenchmarks.pojo_gson                          citys  thrpt   10      28.141 ±     1.127  ops/s
DeserializationBenchmarks.pojo_gson                          repos  thrpt   10     475.520 ±    48.032  ops/s
DeserializationBenchmarks.pojo_gson                           user  thrpt   10    7124.350 ±   383.922  ops/s
DeserializationBenchmarks.pojo_gson                        request  thrpt   10  306235.684 ± 40585.699  ops/s
DeserializationBenchmarks.pojo_jackson                       citys  thrpt   10      34.979 ±     1.382  ops/s
DeserializationBenchmarks.pojo_jackson                       repos  thrpt   10     497.108 ±    47.013  ops/s
DeserializationBenchmarks.pojo_jackson                        user  thrpt   10    7520.526 ±   873.285  ops/s
DeserializationBenchmarks.pojo_jackson                     request  thrpt   10  378590.886 ± 32516.408  ops/s
DeserializationBenchmarks.pojo_jackson_afterburner           citys  thrpt   10      40.565 ±     1.683  ops/s
DeserializationBenchmarks.pojo_jackson_afterburner           repos  thrpt   10     533.028 ±    39.599  ops/s
DeserializationBenchmarks.pojo_jackson_afterburner            user  thrpt   10    8835.879 ±   788.449  ops/s
DeserializationBenchmarks.pojo_jackson_afterburner         request  thrpt   10  435327.682 ± 29664.601  ops/s
DeserializationBenchmarks.pojo_tools                         citys  thrpt   10      62.404 ±     2.381  ops/s
DeserializationBenchmarks.pojo_tools                         repos  thrpt   10     935.173 ±    67.067  ops/s
DeserializationBenchmarks.pojo_tools                          user  thrpt   10   21066.675 ±  1598.623  ops/s
DeserializationBenchmarks.pojo_tools                       request  thrpt   10  490855.217 ± 24643.280  ops/s


Benchmark                                           (resourceName)   Mode  Cnt        Score      Error  Units
SerializationBenchmarks.map_boon                             citys  thrpt   10      24.262 ±     0.718  ops/s
SerializationBenchmarks.map_boon                             repos  thrpt   10     665.313 ±    43.969  ops/s
SerializationBenchmarks.map_boon                              user  thrpt   10   46364.305 ±  3366.612  ops/s
SerializationBenchmarks.map_boon                           request  thrpt   10  321868.064 ± 23392.075  ops/s
SerializationBenchmarks.map_fastjson                         citys  thrpt   10      18.250 ±     1.003  ops/s
SerializationBenchmarks.map_fastjson                         repos  thrpt   10     603.838 ±    36.054  ops/s
SerializationBenchmarks.map_fastjson                          user  thrpt   10   42312.671 ±  3340.320  ops/s
SerializationBenchmarks.map_fastjson                       request  thrpt   10  688570.290 ± 54489.753  ops/s
SerializationBenchmarks.map_genson                           citys  thrpt   10      17.981 ±     0.410  ops/s
SerializationBenchmarks.map_genson                           repos  thrpt   10     512.617 ±    28.174  ops/s
SerializationBenchmarks.map_genson                            user  thrpt   10   31530.171 ±  2568.991  ops/s
SerializationBenchmarks.map_genson                         request  thrpt   10  306078.749 ± 21030.792  ops/s
SerializationBenchmarks.map_gson                             citys  thrpt   10      16.722 ±     0.583  ops/s
SerializationBenchmarks.map_gson                             repos  thrpt   10     542.325 ±    32.021  ops/s
SerializationBenchmarks.map_gson                              user  thrpt   10   24918.216 ±  1870.860  ops/s
SerializationBenchmarks.map_gson                           request  thrpt   10  286680.053 ± 24416.250  ops/s
SerializationBenchmarks.map_jackson                          citys  thrpt   10      29.999 ±     1.471  ops/s
SerializationBenchmarks.map_jackson                          repos  thrpt   10     734.495 ±    50.582  ops/s
SerializationBenchmarks.map_jackson                           user  thrpt   10   65294.933 ±  3265.026  ops/s
SerializationBenchmarks.map_jackson                        request  thrpt   10  742826.771 ± 73691.752  ops/s
SerializationBenchmarks.map_javax_glassfish                  citys  thrpt   10      18.394 ±     0.757  ops/s
SerializationBenchmarks.map_javax_glassfish                  repos  thrpt   10     483.463 ±    27.730  ops/s
SerializationBenchmarks.map_javax_glassfish                   user  thrpt   10   18038.238 ±   420.245  ops/s
SerializationBenchmarks.map_javax_glassfish                request  thrpt   10   37199.714 ±  1329.356  ops/s
SerializationBenchmarks.map_json                             citys  thrpt   10      14.647 ±     0.289  ops/s
SerializationBenchmarks.map_json                             repos  thrpt   10     234.992 ±    14.199  ops/s
SerializationBenchmarks.map_json                              user  thrpt   10   15131.910 ±  1284.400  ops/s
SerializationBenchmarks.map_json                           request  thrpt   10  181819.380 ± 13270.213  ops/s
SerializationBenchmarks.map_mjson                            citys  thrpt   10      10.374 ±     1.953  ops/s
SerializationBenchmarks.map_mjson                            repos  thrpt   10     151.943 ±    10.957  ops/s
SerializationBenchmarks.map_mjson                             user  thrpt   10   10434.771 ±   718.012  ops/s
SerializationBenchmarks.map_mjson                          request  thrpt   10  143317.315 ± 13270.521  ops/s
SerializationBenchmarks.map_tools                            citys  thrpt   10      31.256 ±     1.224  ops/s
SerializationBenchmarks.map_tools                            repos  thrpt   10     943.561 ±    71.503  ops/s
SerializationBenchmarks.map_tools                             user  thrpt   10   59402.944 ±  3913.833  ops/s
SerializationBenchmarks.map_tools                          request  thrpt   10  762660.973 ± 35243.893  ops/s
SerializationBenchmarks.pojo_boon                            citys  thrpt   10      24.956 ±     0.675  ops/s
SerializationBenchmarks.pojo_boon                            repos  thrpt   10     597.754 ±    51.289  ops/s
SerializationBenchmarks.pojo_boon                             user  thrpt   10    8344.520 ±   963.270  ops/s
SerializationBenchmarks.pojo_boon                          request  thrpt   10  264836.300 ± 15848.621  ops/s
SerializationBenchmarks.pojo_fastjson                        citys  thrpt   10      24.352 ±     1.203  ops/s
SerializationBenchmarks.pojo_fastjson                        repos  thrpt   10     611.797 ±    39.916  ops/s
SerializationBenchmarks.pojo_fastjson                         user  thrpt   10   10942.680 ±  1058.570  ops/s
SerializationBenchmarks.pojo_fastjson                      request  thrpt   10  758844.098 ± 51664.522  ops/s
SerializationBenchmarks.pojo_genson                          citys  thrpt   10      25.377 ±     0.828  ops/s
SerializationBenchmarks.pojo_genson                          repos  thrpt   10     554.972 ±    36.641  ops/s
SerializationBenchmarks.pojo_genson                           user  thrpt   10   17428.791 ±  1293.893  ops/s
SerializationBenchmarks.pojo_genson                        request  thrpt   10  432326.276 ± 24069.198  ops/s
SerializationBenchmarks.pojo_gson                            citys  thrpt   10      17.168 ±     0.543  ops/s
SerializationBenchmarks.pojo_gson                            repos  thrpt   10     457.335 ±    35.423  ops/s
SerializationBenchmarks.pojo_gson                             user  thrpt   10   12332.433 ±  1113.454  ops/s
SerializationBenchmarks.pojo_gson                          request  thrpt   10  246699.277 ± 15851.193  ops/s
SerializationBenchmarks.pojo_jackson                         citys  thrpt   10      31.914 ±     0.585  ops/s
SerializationBenchmarks.pojo_jackson                         repos  thrpt   10     668.309 ±    45.300  ops/s
SerializationBenchmarks.pojo_jackson                          user  thrpt   10   18771.948 ±  1499.572  ops/s
SerializationBenchmarks.pojo_jackson                       request  thrpt   10  753683.701 ± 59154.701  ops/s
SerializationBenchmarks.pojo_jackson_afterburner             citys  thrpt   10      32.821 ±     2.156  ops/s
SerializationBenchmarks.pojo_jackson_afterburner             repos  thrpt   10     779.822 ±    55.538  ops/s
SerializationBenchmarks.pojo_jackson_afterburner              user  thrpt   10   19042.921 ±  1423.705  ops/s
SerializationBenchmarks.pojo_jackson_afterburner           request  thrpt   10  741530.004 ± 69521.201  ops/s
SerializationBenchmarks.pojo_tools                           citys  thrpt   10      35.994 ±     2.481  ops/s
SerializationBenchmarks.pojo_tools                           repos  thrpt   10    1025.527 ±    80.847  ops/s
SerializationBenchmarks.pojo_tools                            user  thrpt   10   28684.624 ±  2254.766  ops/s
SerializationBenchmarks.pojo_tools                         request  thrpt   10  781003.659 ± 50283.011  ops/s
```

[s.user]: https://cloud.githubusercontent.com/assets/5871626/16057025/54b4aa64-3278-11e6-8153-2980d2531ba8.png
[s.request]: https://cloud.githubusercontent.com/assets/5871626/16057021/54b1e338-3278-11e6-976d-119e96b20c4d.png
[s.repos]: https://cloud.githubusercontent.com/assets/5871626/16057024/54b3ff06-3278-11e6-8440-ae6123d3af61.png
[s.cities]: https://cloud.githubusercontent.com/assets/5871626/16057022/54b34eee-3278-11e6-8741-3c6dcf92cf06.png
[d.user]: https://cloud.githubusercontent.com/assets/5871626/16057026/54b6568e-3278-11e6-83ef-5475fa7ced08.png
[d.request]: https://cloud.githubusercontent.com/assets/5871626/16057023/54b3e548-3278-11e6-8335-0b2199ea0311.png
[d.repos]: https://cloud.githubusercontent.com/assets/5871626/16057027/54ce5f40-3278-11e6-9552-2aaa9e4926ad.png
[d.cities]: https://cloud.githubusercontent.com/assets/5871626/16057028/54cf713c-3278-11e6-88d6-ef1c36558f05.png
