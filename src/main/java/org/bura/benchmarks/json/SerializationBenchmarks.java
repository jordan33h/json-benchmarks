package org.bura.benchmarks.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dslplatform.json.DslJson;
import com.eclipsesource.json.JsonValue;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jsoniter.extra.JdkDatetimeSupport;
import com.jsoniter.output.JsonStream;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;
import com.wizzardo.tools.interfaces.Mapper;
import com.wizzardo.tools.json.JsonTools;
import com.wizzardo.tools.misc.Unchecked;
import groovy.json.JsonOutput;
import org.boon.json.JsonFactory;
import org.json.JSONArray;
import org.openjdk.jmh.annotations.*;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1, jvmArgsAppend = {"-Xmx2048m", "-server", "-XX:+AggressiveOpts"})
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
public class SerializationBenchmarks {

    private static final String RESOURCE_CITYS = "citys";
    private static final String RESOURCE_REPOS = "repos";
    private static final String RESOURCE_USER = "user";
    private static final String RESOURCE_REQUEST = "request";

    private static final String DATA_STYLE_POJO = "pojo";
    private static final String DATA_STYLE_MAPLIST = "maplist";

    @Param({RESOURCE_CITYS, RESOURCE_REPOS, RESOURCE_USER, RESOURCE_REQUEST})
//    @Param({RESOURCE_REPOS, RESOURCE_USER, RESOURCE_REQUEST})
//    @Param({ RESOURCE_CITYS})
//    @Param({ RESOURCE_REPOS})
//    @Param({ RESOURCE_REQUEST})
//    @Param({ RESOURCE_USER})
    private String resourceName;

    Object data_pojo;
    List kotlin_pojo;
    Mapper<List<Object>, String> kotlinxSerializer;
    Mapper<List<Object>, String> klaxonSerializer;
    List data_map;
    JSONArray jsonArray;
    javax.json.JsonArray javaxJsonArray;
    JsonValue minimalJson;
    JsonAdapter moshiPojoAdapter;
    JsonAdapter moshiMapAdapter = new Moshi.Builder().build().adapter(List.class);
    DslJson<Object> dslJson = new DslJson<>(com.dslplatform.json.runtime.Settings
            .withRuntime()
            .includeServiceLoader());
    com.dslplatform.json.JsonWriter dslJsonWriter = dslJson.newWriter();

    @Setup(Level.Iteration)
    public void setup() throws JsonParseException, JsonMappingException, IOException {
        String resource = Helper.getResource(resourceName + ".json");

        Moshi moshi = new Moshi.Builder()
                .add(Date.class, new Rfc3339DateJsonAdapter())
                .build();

        switch (resourceName) {
            case RESOURCE_CITYS:
                data_pojo = jacksonMapper.readValue(resource, CityInfo[].class);
                moshiPojoAdapter = moshi.adapter(CityInfo[].class);

                break;
            case RESOURCE_REPOS:
                data_pojo = jacksonMapper.readValue(resource, Repo[].class);
                moshiPojoAdapter = moshi.adapter(Repo[].class);

                break;
            case RESOURCE_USER:
                data_pojo = jacksonMapper.readValue(resource, UserProfile[].class);
                moshiPojoAdapter = moshi.adapter(UserProfile[].class);

                break;
            case RESOURCE_REQUEST:
                data_pojo = jacksonMapper.readValue(resource, Request[].class);
                moshiPojoAdapter = moshi.adapter(Request[].class);

                break;
        }
        data_map = jacksonMapper.readValue(resource, List.class);
        jsonArray = new JSONArray(resource);

        JsonReader jsonReader = Json.createReader(new StringReader(resource));
        javaxJsonArray = jsonReader.readArray();
        jsonReader.close();

        minimalJson = com.eclipsesource.json.Json.parse(resource);

        kotlin_pojo = (List) KotlinHelper.getKotlinxParser(resourceName).map(resource);
        kotlinxSerializer = KotlinHelper.getKotlinxSerializer(resourceName);
        klaxonSerializer = KotlinHelper.getKlaxonSerializer(resourceName);

        Unchecked.ignore(() -> JdkDatetimeSupport.enable("yyyy-MM-dd'T'HH:mm:ssXXX"));
    }

    private static ObjectMapper initMapper(boolean afterburner) {
        ObjectMapper m = new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        DateFormat formatter = new ISO8601DateFormat();
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        m.setDateFormat(formatter);
        if (afterburner)
            m.registerModule(new AfterburnerModule());

        return m;
    }

    private final ObjectMapper jacksonMapper = initMapper(false);
    private final ObjectMapper jacksonMapperAfterburner = initMapper(true);

    @Benchmark
    public String pojo_jackson() throws IOException {
        return jacksonMapper.writeValueAsString(data_pojo);
    }

    @Benchmark
    public String pojo_jackson_afterburner() throws IOException {
        return jacksonMapperAfterburner.writeValueAsString(data_pojo);
    }

    @Benchmark
    public String map_jackson() throws IOException {
        return jacksonMapper.writeValueAsString(data_map);
    }


    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

    @Benchmark
    public String pojo_gson() {
        return gson.toJson(data_pojo);
    }

    @Benchmark
    public String map_gson() {
        return gson.toJson(data_map);
    }


    @Benchmark
    public String pojo_boon() {
        return JsonFactory.createUseJSONDates().toJson(data_pojo);
    }

    @Benchmark
    public String map_boon() {
        return JsonFactory.createUseJSONDates().toJson(data_map);
    }


    @Benchmark
    public String pojo_tools() {
        return JsonTools.serialize(data_pojo);
    }

    @Benchmark
    public String map_tools() {
        return JsonTools.serialize(data_map);
    }


    @Benchmark
    public String pojo_fastjson() {
        return JSON.toJSONString(data_pojo, SerializerFeature.UseISO8601DateFormat);
    }

    @Benchmark
    public String map_fastjson() {
        return JSON.toJSONString(data_map, SerializerFeature.UseISO8601DateFormat);
    }


    @Benchmark
    public String map_json() {
        return jsonArray.toString();
    }


    @Benchmark
    public Object map_javax_glassfish() {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = Json.createWriter(stringWriter);
        writer.write(javaxJsonArray);
        return stringWriter.toString();
    }


    Genson genson = new GensonBuilder()
            .useDateAsTimestamp(false)
            .useDateFormat(new ISO8601DateFormat())
            .create();

    @Benchmark
    public Object pojo_genson() {
        return genson.serialize(data_pojo);
    }

    @Benchmark
    public Object map_genson() {
        return genson.serialize(data_map);
    }


    @Benchmark
    public Object map_mjson() {
        return mjson.Json.make(data_map).toString();
    }


    @Benchmark
    public Object map_minimal_json() {
        return minimalJson.toString();
    }


    @Benchmark
    public Object pojo_moshi() {
        return moshiPojoAdapter.toJson(data_pojo);
    }

    @Benchmark
    public Object map_moshi() {
        return moshiMapAdapter.toJson(data_map);
    }


    @Benchmark
    public Object pojo_dslplatform() {
        try {
            dslJsonWriter.serializeObject(data_pojo);
            return dslJsonWriter.toString();
        } finally {
            dslJsonWriter.reset();
        }
    }

    @Benchmark
    public Object pojo_jsonIterator() {
        return JsonStream.serialize(data_pojo);
    }

    @Benchmark
    public Object map_jsonIterator() {
        return JsonStream.serialize(data_map);
    }

    @Benchmark
    public Object pojo_kotlinx() {
        return kotlinxSerializer.map(kotlin_pojo);
    }

    @Benchmark
    public Object pojo_klaxon() {
        return klaxonSerializer.map(kotlin_pojo);
    }

    //    @Benchmark
    public String groovy() {
        return JsonOutput.toJson(data_pojo);
    }
}
