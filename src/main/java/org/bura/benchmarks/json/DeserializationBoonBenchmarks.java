package org.bura.benchmarks.json;

import com.wizzardo.tools.json.JsonTools;
import org.boon.json.JsonFactory;
import org.bura.benchmarks.json.domain.UserProfile;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//        Benchmark                                           (resourceName)   Mode  Cnt       Score        Error  Units
//        DeserializationBoonBenchmarks.boon_map                         N/A  thrpt   10   52525.301 ±   4838.818  ops/s
//        DeserializationBoonBenchmarks.boon_map_get                     N/A  thrpt   10   35181.680 ±   5627.096  ops/s
//        DeserializationBoonBenchmarks.boon_pojo                        N/A  thrpt   10   12494.686 ±    840.393  ops/s
//        DeserializationBoonBenchmarks.boon_pojo_get                    N/A  thrpt   10   12387.501 ±    847.777  ops/s
//        DeserializationBoonBenchmarks.tools_map                        N/A  thrpt   10   51425.221 ±   5017.561  ops/s
//        DeserializationBoonBenchmarks.tools_map_get                    N/A  thrpt   10   49752.246 ±   7382.129  ops/s
//        DeserializationBoonBenchmarks.tools_pojo                       N/A  thrpt   10   21037.825 ±   1572.674  ops/s
//        DeserializationBoonBenchmarks.tools_pojo_get                   N/A  thrpt   10   21114.613 ±   2102.971  ops/s

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1, jvmArgsAppend = {"-Xmx2048m", "-server", "-XX:+AggressiveOpts"})
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
public class DeserializationBoonBenchmarks {

    private String resource;


    @Setup(Level.Iteration)
    public void setup() {
        resource = Helper.getResource("user.json");
    }

    @Benchmark
    public Object boon_pojo() {
        return JsonFactory.create().readValue(resource, List.class, UserProfile.class);
    }

    @Benchmark
    public Object boon_map() {
        return JsonFactory.create().fromJson(resource);
    }

    @Benchmark
    public Object boon_pojo_get() {
        List<UserProfile> list = JsonFactory.create().readValue(resource, List.class, UserProfile.class);
        return list.get(0).getEmail();
    }

    @Benchmark
    public Object boon_map_get() {
        return ((Map) ((List) JsonFactory.create().fromJson(resource)).get(0)).get("email");
    }


    @Benchmark
    public Object tools_pojo() {
        return JsonTools.parse(resource, List.class, UserProfile.class);
    }

    @Benchmark
    public Object tools_pojo_get() {
        List<UserProfile> list = JsonTools.parse(resource, List.class, UserProfile.class);
        return list.get(0).getEmail();
    }

    @Benchmark
    public Object tools_map() {
        return JsonTools.parse(resource);
    }

    @Benchmark
    public Object tools_map_get() {
        return JsonTools.parse(resource).asJsonArray().get(0).asJsonObject().getAsString("email");
    }

}
