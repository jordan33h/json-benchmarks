package org.bura.benchmarks.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wizzardo.tools.json.Binder;
import groovy.json.JsonOutput;
import org.boon.json.serializers.impl.JsonSimpleSerializerImpl;
import org.bura.benchmarks.json.domain.CityInfo;
import org.bura.benchmarks.json.domain.Repo;
import org.bura.benchmarks.json.domain.Request;
import org.bura.benchmarks.json.domain.UserProfile;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1, jvmArgsAppend = {"-Xmx2048m", "-server", "-XX:+AggressiveOpts"})
@Measurement(iterations = 10, time = 3, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 20, time = 3, timeUnit = TimeUnit.SECONDS)
public class SerializationBenchmarks {

    private static final String RESOURCE_CITYS = "citys";
    private static final String RESOURCE_REPOS = "repos";
    private static final String RESOURCE_USER = "user";
    private static final String RESOURCE_REQUEST = "request";

    private static final String DATA_STYLE_POJO = "pojo";
    private static final String DATA_STYLE_MAPLIST = "maplist";

    @Param({RESOURCE_CITYS, RESOURCE_REPOS, RESOURCE_USER, RESOURCE_REQUEST})
//    @Param({ RESOURCE_CITYS})
    private String resourceName;

    private Object data;

    @Setup(Level.Iteration)
    public void setup() throws JsonParseException, JsonMappingException, IOException {
        String resource = Helper.getResource(resourceName + ".json");
        switch (resourceName) {
            case RESOURCE_CITYS:
                data = jacksonMapper.readValue(resource, CityInfo[].class);

                break;
            case RESOURCE_REPOS:
                data = jacksonMapper.readValue(resource, Repo[].class);

                break;
            case RESOURCE_USER:
                data = jacksonMapper.readValue(resource, UserProfile[].class);

                break;
            case RESOURCE_REQUEST:
                data = jacksonMapper.readValue(resource, Request[].class);

                break;
        }
    }

    private ObjectMapper initMapper() {
        ObjectMapper m = new ObjectMapper().enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        m.setDateFormat(formatter);

        return m;
    }

    private final ObjectMapper jacksonMapper = initMapper();

    @Benchmark
    public String jackson() throws IOException {
        return jacksonMapper.writeValueAsString(data);
    }

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

    @Benchmark
    public String gson() {
        return gson.toJson(data);
    }

    private final JsonSimpleSerializerImpl boon = new JsonSimpleSerializerImpl();

    @Benchmark
    public String boon() {
        return boon.serialize(data).toString();
    }

    @Benchmark
    public String tools() {
        return Binder.toJSON(data);
    }

    @Benchmark
    public String groovy() {
        return JsonOutput.toJson(data);
    }
}
