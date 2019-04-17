package org.bura.benchmarks.json;

import com.wizzardo.tools.json.JsonTools;

import org.boon.json.JsonParserFactory;
import org.boon.json.JsonSerializerFactory;
import org.bura.benchmarks.json.domain.UserProfile;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1, jvmArgsAppend = {"-Xmx2048m", "-server", "-XX:+AggressiveOpts"})
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 5, time = 1)
public class DeserializationBoonBenchmarks {

    private String resource;


    @Setup(Level.Iteration)
    public void setup() {
        resource = Helper.getResource("user.json");
    }

    @Benchmark
    public Object boon_pojo() {
        return new JsonParserFactory().create().parseList(UserProfile.class, resource);
    }

    @Benchmark
    public Object boon_map() {
        return new JsonSerializerFactory().create().serialize(resource);
    }

    @Benchmark
    public Object boon_pojo_get() {
        List<UserProfile> list = new JsonParserFactory().create().parseList(UserProfile.class, resource);
        return list.get(0).getEmail();
    }

    @Benchmark
    public Object boon_map_get() {
        return ((Map) ((List) new JsonSerializerFactory().create().serialize(resource)).get(0)).get("email");
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
