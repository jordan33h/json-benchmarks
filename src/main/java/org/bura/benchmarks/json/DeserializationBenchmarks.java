package org.bura.benchmarks.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wizzardo.tools.json.JsonTools;
import groovy.json.JsonSlurper;
import org.boon.json.JsonFactory;
import org.bura.benchmarks.json.domain.CityInfo;
import org.bura.benchmarks.json.domain.Repo;
import org.bura.benchmarks.json.domain.Request;
import org.bura.benchmarks.json.domain.UserProfile;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1, jvmArgsAppend = {"-Xmx2048m", "-server", "-XX:+AggressiveOpts"})
@Measurement(iterations = 10, time = 3, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 20, time = 3, timeUnit = TimeUnit.SECONDS)
public class DeserializationBenchmarks {

    private static final String RESOURCE_CITYS = "citys";
    private static final String RESOURCE_REPOS = "repos";
    private static final String RESOURCE_USER = "user";
    private static final String RESOURCE_REQUEST = "request";

    //    @Param({RESOURCE_CITYS, RESOURCE_REPOS, RESOURCE_USER, RESOURCE_REQUEST})
//    @Param({ RESOURCE_CITYS })
//    @Param({RESOURCE_REPOS})
//    @Param({RESOURCE_REQUEST})
    @Param({RESOURCE_USER})
    private String resourceName;

    private String resource;

    private Class type;

    @Setup(Level.Iteration)
    public void setup() {
        resource = Helper.getResource(resourceName + ".json");

        switch (resourceName) {
            case RESOURCE_CITYS: {
                gsonType = new TypeToken<List<CityInfo>>() {
                }.getType();
                jacksonType = new TypeReference<List<CityInfo>>() {
                };
                type = CityInfo.class;
                break;
            }
            case RESOURCE_REPOS: {
                gsonType = new TypeToken<List<Repo>>() {
                }.getType();
                jacksonType = new TypeReference<List<Repo>>() {
                };
                type = Repo.class;
                break;
            }
            case RESOURCE_USER: {
                gsonType = new TypeToken<List<UserProfile>>() {
                }.getType();
                jacksonType = new TypeReference<List<UserProfile>>() {
                };
                type = UserProfile.class;
                break;
            }
            case RESOURCE_REQUEST: {
                gsonType = new TypeToken<List<Request>>() {
                }.getType();
                jacksonType = new TypeReference<List<Request>>() {
                };
                type = Request.class;
                break;
            }
        }
    }

    private final ObjectMapper jacksonMapper = new ObjectMapper();
    private TypeReference<?> jacksonType;

    @Benchmark
    public Object jackson() throws IOException {
        return jacksonMapper.readValue(resource, jacksonType);
    }

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
    private java.lang.reflect.Type gsonType;

    @Benchmark
    public Object gson() {
        return gson.fromJson(resource, gsonType);
    }

    @Benchmark
    public Object boon() {
        return JsonFactory.create().readValue(resource, List.class, type);
    }

    private final JsonSlurper groovy = new JsonSlurper();

    @Benchmark
    public Object groovy() {
        return groovy.parseText(resource);
    }

    @Benchmark
    public Object tools() {
        return JsonTools.parse(resource, List.class, type);
//        return JsonObject.parse(resource);
    }
}
