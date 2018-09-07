package org.bura.benchmarks.json;

import com.alibaba.fastjson.JSON;
import com.dslplatform.json.DslJson;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jsoniter.JsonIterator;
import com.jsoniter.extra.JdkDatetimeSupport;
import com.jsoniter.spi.TypeLiteral;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import com.squareup.moshi.*;
import com.wizzardo.tools.interfaces.Mapper;
import com.wizzardo.tools.json.JsonTools;
import com.wizzardo.tools.misc.Unchecked;
import groovy.json.JsonSlurper;
import io.circe.ParsingFailure;
import org.boon.json.JsonFactory;
import org.boon.json.JsonParserFactory;
import org.boon.json.JsonSerializerFactory;
import org.bura.benchmarks.json.domain.*;
import org.bura.benchmarks.json.kotlin.KotlinHelper;
import org.json.JSONArray;
import org.json.simple.parser.ParseException;
import org.openjdk.jmh.annotations.*;
import scala.util.Either;
import io.circe.Decoder;
import io.circe.parser.package$;

import javax.json.Json;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Timeout(time = 20)
@Fork(value = 1, jvmArgsAppend = {"-Xmx2048m", "-server", "-XX:+AggressiveOpts"})
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 15, time = 1, timeUnit = TimeUnit.SECONDS)
public class DeserializationBenchmarks {

    public static final String RESOURCE_CITYS = "citys";
    public static final String RESOURCE_REPOS = "repos";
    public static final String RESOURCE_USER = "user";
    public static final String RESOURCE_REQUEST = "request";

    @Param({RESOURCE_CITYS, RESOURCE_REPOS, RESOURCE_USER, RESOURCE_REQUEST})
//    @Param({ RESOURCE_CITYS })
//    @Param({RESOURCE_REPOS})
//    @Param({RESOURCE_REQUEST})
//    @Param({RESOURCE_USER})
    private String resourceName;

    private String resource;

    private Class type;
    private TypeLiteral jsonIteratorType;

    @Setup(Level.Iteration)
    public void setup() {
        resource = Helper.getResource(resourceName + ".json");

        Moshi moshi = new Moshi.Builder()
                .add(Date.class, new Rfc3339DateJsonAdapter())
                .build();

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
                moshiPojoAdapter = moshi.adapter(Types.newParameterizedType(List.class, CityInfo.class));
                jsonIteratorType = new TypeLiteral<List<CityInfo>>() {
                };
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
                moshiPojoAdapter = moshi.adapter(Types.newParameterizedType(List.class, Repo.class));
                jsonIteratorType = new TypeLiteral<List<Repo>>() {
                };
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
                moshiPojoAdapter = moshi.adapter(Types.newParameterizedType(List.class, UserProfile.class));
                jsonIteratorType = new TypeLiteral<List<UserProfile>>() {
                };
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
                moshiPojoAdapter = moshi.adapter(Types.newParameterizedType(List.class, Request.class));
                jsonIteratorType = new TypeLiteral<List<Request>>() {
                };
                break;
            }
        }

        jacksonMapperAfterburner = new ObjectMapper();
        jacksonMapperAfterburner.registerModule(new AfterburnerModule());

        kolinxParser = KotlinHelper.getKotlinxParser(resourceName);
        klaxonParser = KotlinHelper.getKlaxonParser(resourceName);

        Unchecked.ignore(() -> JdkDatetimeSupport.enable("yyyy-MM-dd'T'HH:mm:ssXXX"));
    }

    Mapper<String, Object> kolinxParser;
    Mapper<String, Object> klaxonParser;
    JsonAdapter moshiPojoAdapter;
    JsonAdapter moshiMapAdapter = new Moshi.Builder().build().adapter(List.class);
    java.lang.reflect.Type gsonType;
    com.alibaba.fastjson.TypeReference fastjsonType;
    GenericType gensonType;

    ObjectMapper jacksonMapperAfterburner;
    final ObjectMapper jacksonMapper = new ObjectMapper();
    TypeReference<?> jacksonType;
    final TypeReference<List> jacksonMapType = new TypeReference<List>() {
    };
    DslJson<Object> dslJson = new DslJson<>(com.dslplatform.json.runtime.Settings
            .withRuntime()
            .includeServiceLoader());

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
    public Object map_json_simple() throws ParseException {
        return new org.json.simple.parser.JSONParser().parse(resource);
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


    @Benchmark
    public Object pojo_moshi() throws IOException {
        return moshiPojoAdapter.fromJson(resource);
    }

    @Benchmark
    public Object map_moshi() throws IOException {
        return moshiMapAdapter.fromJson(resource);
    }

    @Benchmark
    public Object pojo_dslplatform() throws IOException {
        com.dslplatform.json.JsonReader reader = dslJson.newReader(resource);
        reader.startArray();
        reader.read();
        return reader.deserializeCollection(dslJson.tryFindReader(type));
    }

    @Benchmark
    public Object pojo_jsonIterator() throws IOException {
        JsonIterator iterator = JsonIterator.parse(resource);
        return iterator.read(jsonIteratorType);
    }

    @Benchmark
    public Object map_circe() throws IOException {
        Either<ParsingFailure, io.circe.Json> either = package$.MODULE$.parse(resource);
        return either.right().get().as(Decoder.decodeList(Decoder.decodeJsonObject()));
    }

    @Benchmark
    public Object pojo_kotlinx() throws IOException {
        return kolinxParser.map(resource);
    }

    @Benchmark
    public Object pojo_klaxon() throws IOException {
        return klaxonParser.map(resource);
    }
}
