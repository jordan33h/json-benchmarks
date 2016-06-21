package org.bura.benchmarks.json;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import com.wizzardo.tools.json.JsonTools;
import groovy.json.JsonSlurper;
import org.boon.json.JsonFactory;
import org.boon.json.JsonParserFactory;
import org.boon.json.JsonSerializerFactory;
import org.bura.benchmarks.json.domain.CityInfo;
import org.bura.benchmarks.json.domain.Repo;
import org.bura.benchmarks.json.domain.Request;
import org.bura.benchmarks.json.domain.UserProfile;
import org.json.JSONArray;
import org.openjdk.jmh.annotations.*;

import javax.json.Json;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1, jvmArgsAppend = {"-Xmx2048m", "-server", "-XX:+AggressiveOpts"})
@Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 20, time = 2, timeUnit = TimeUnit.SECONDS)
public class DeserializationBenchmarks {

    private static final String RESOURCE_CITYS = "citys";
    private static final String RESOURCE_REPOS = "repos";
    private static final String RESOURCE_USER = "user";
    private static final String RESOURCE_REQUEST = "request";

    @Param({RESOURCE_CITYS, RESOURCE_REPOS, RESOURCE_USER, RESOURCE_REQUEST})
//    @Param({ RESOURCE_CITYS })
//    @Param({RESOURCE_REPOS})
//    @Param({RESOURCE_REQUEST})
//    @Param({RESOURCE_USER})
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
                fastjsonType = new com.alibaba.fastjson.TypeReference<List<CityInfo>>() {
                };
                gensonType = new GenericType<List<CityInfo>>() {
                };
                type = CityInfo.class;
                break;
            }
            case RESOURCE_REPOS: {
                gsonType = new TypeToken<List<Repo>>() {
                }.getType();
                jacksonType = new TypeReference<List<Repo>>() {
                };
                fastjsonType = new com.alibaba.fastjson.TypeReference<List<Repo>>() {
                };
                gensonType = new GenericType<List<Repo>>() {
                };
                type = Repo.class;
                break;
            }
            case RESOURCE_USER: {
                gsonType = new TypeToken<List<UserProfile>>() {
                }.getType();
                jacksonType = new TypeReference<List<UserProfile>>() {
                };
                fastjsonType = new com.alibaba.fastjson.TypeReference<List<UserProfile>>() {
                };
                gensonType = new GenericType<List<UserProfile>>() {
                };
                type = UserProfile.class;
                break;
            }
            case RESOURCE_REQUEST: {
                gsonType = new TypeToken<List<Request>>() {
                }.getType();
                jacksonType = new TypeReference<List<Request>>() {
                };
                fastjsonType = new com.alibaba.fastjson.TypeReference<List<Request>>() {
                };
                gensonType = new GenericType<List<Request>>() {
                };
                type = Request.class;
                break;
            }
        }

        jacksonMapperAfterburner = new ObjectMapper();
        jacksonMapperAfterburner.registerModule(new AfterburnerModule());
    }

    java.lang.reflect.Type gsonType;
    com.alibaba.fastjson.TypeReference fastjsonType;
    GenericType gensonType;

    ObjectMapper jacksonMapperAfterburner;
    final ObjectMapper jacksonMapper = new ObjectMapper();
    TypeReference<?> jacksonType;
    final TypeReference<List> jacksonMapType = new TypeReference<List>() {
    };

    @Benchmark
    public Object pojo_jackson() throws IOException {
        return jacksonMapper.readValue(resource, jacksonType);
    }

    @Benchmark
    public Object pojo_jackson_afterburner() throws IOException {
        return jacksonMapperAfterburner.readValue(resource, jacksonType);
    }

    @Benchmark
    public Object map_jackson() throws IOException {
        return jacksonMapper.readValue(resource, jacksonMapType);
    }


    final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
    final java.lang.reflect.Type gsonMapType = new TypeToken<List>() {
    }.getType();

    @Benchmark
    public Object pojo_gson() {
        return gson.fromJson(resource, gsonType);
    }

    @Benchmark
    public Object map_gson() {
        return gson.fromJson(resource, gsonMapType);
    }


    @Benchmark
    public Object pojo_boon() {
        return JsonFactory.create().readValue(resource, List.class, type);
    }

    @Benchmark
    public Object map_boon() {
        return JsonFactory.create(new JsonParserFactory().setCheckDates(false), new JsonSerializerFactory()).fromJson(resource);
    }


    private final JsonSlurper groovy = new JsonSlurper();

    //    @Benchmark
    public Object groovy() {
        return groovy.parseText(resource);
    }


    @Benchmark
    public Object pojo_tools() {
        return JsonTools.parse(resource, List.class, type);
    }

    @Benchmark
    public Object map_tools() {
        return JsonTools.parse(resource);
    }


    @Benchmark
    public Object pojo_fastjson() {
        return JSON.parseObject(resource, fastjsonType);
    }

    @Benchmark
    public Object map_fastjson() {
        return JSON.parseArray(resource);
    }


    @Benchmark
    public Object map_json_org() {
        return new JSONArray(resource);
    }


    @Benchmark
    public Object map_javax_glassfish() {
        JsonReader jsonReader = Json.createReader(new StringReader(resource));
        javax.json.JsonArray object = jsonReader.readArray();
        jsonReader.close();
        return object;
    }


    Genson genson = new GensonBuilder()
            .useDateFormat(new ISO8601DateFormat())
            .create();

    @Benchmark
    public Object pojo_genson() {
        return genson.deserialize(resource, gensonType);
    }

    @Benchmark
    public Object map_genson() {
        return genson.deserialize(resource, Object.class);
    }


    @Benchmark
    public Object map_mjson() {
        return mjson.Json.read(resource);
    }


    @Benchmark
    public Object map_minimal_json() {
        return com.eclipsesource.json.Json.parse(resource);
    }

}
