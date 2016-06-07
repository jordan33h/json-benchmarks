package org.bura.benchmarks.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wizzardo.tools.json.JsonTools;
import groovy.json.JsonOutput;
import org.boon.json.JsonFactory;
import org.bura.benchmarks.json.domain.CityInfo;
import org.bura.benchmarks.json.domain.Repo;
import org.bura.benchmarks.json.domain.Request;
import org.bura.benchmarks.json.domain.UserProfile;
import org.json.JSONArray;
import org.openjdk.jmh.annotations.*;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1, jvmArgsAppend = {"-Xmx2048m", "-server", "-XX:+AggressiveOpts"})
@Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 20, time = 2, timeUnit = TimeUnit.SECONDS)
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
    List data_map;
    JSONArray jsonArray;
    javax.json.JsonArray javaxJsonArray;

    @Setup(Level.Iteration)
    public void setup() throws JsonParseException, JsonMappingException, IOException {
        String resource = Helper.getResource(resourceName + ".json");
        switch (resourceName) {
            case RESOURCE_CITYS:
                data_pojo = jacksonMapper.readValue(resource, CityInfo[].class);
                break;
            case RESOURCE_REPOS:
                data_pojo = jacksonMapper.readValue(resource, Repo[].class);

                break;
            case RESOURCE_USER:
                data_pojo = jacksonMapper.readValue(resource, UserProfile[].class);

                break;
            case RESOURCE_REQUEST:
                data_pojo = jacksonMapper.readValue(resource, Request[].class);

                break;
        }
        data_map = jacksonMapper.readValue(resource, List.class);
        jsonArray = new JSONArray(resource);

        JsonReader jsonReader = Json.createReader(new StringReader(resource));
        javaxJsonArray = jsonReader.readArray();
        jsonReader.close();
    }

    private static ObjectMapper initMapper(boolean afterburner) {
        ObjectMapper m = new ObjectMapper().enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        m.setDateFormat(formatter);
        if (afterburner)
            m.registerModule(new AfterburnerModule());

        return m;
    }

    private final ObjectMapper jacksonMapper = initMapper(false);
    private final ObjectMapper jacksonMapperAfterburner = initMapper(true);

    @Benchmark
    public String jackson_pojo() throws IOException {
        return jacksonMapper.writeValueAsString(data_pojo);
    }

    @Benchmark
    public String jackson_afterburner() throws IOException {
        return jacksonMapperAfterburner.writeValueAsString(data_pojo);
    }

    @Benchmark
    public String jackson_map() throws IOException {
        return jacksonMapper.writeValueAsString(data_map);
    }


    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

    @Benchmark
    public String gson_pojo() {
        return gson.toJson(data_pojo);
    }

    @Benchmark
    public String gson_map() {
        return gson.toJson(data_map);
    }


    @Benchmark
    public String boon_pojo() {
        return JsonFactory.createUseJSONDates().toJson(data_pojo);
    }

    @Benchmark
    public String boon_map() {
        return JsonFactory.createUseJSONDates().toJson(data_map);
    }


    @Benchmark
    public String tools_pojo() {
        return JsonTools.serialize(data_pojo);
    }

    @Benchmark
    public String tools_map() {
        return JsonTools.serialize(data_map);
    }


    @Benchmark
    public String fastjson_pojo() {
        return JSON.toJSONString(data_pojo, SerializerFeature.UseISO8601DateFormat);
    }

    @Benchmark
    public String fastjson_map() {
        return JSON.toJSONString(data_map, SerializerFeature.UseISO8601DateFormat);
    }


    @Benchmark
    public String json_map() {
        return jsonArray.toString();
    }


    @Benchmark
    public Object javax_glassfish_map() {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = Json.createWriter(stringWriter);
        writer.write(javaxJsonArray);
        return stringWriter.toString();
    }


    //    @Benchmark
    public String groovy() {
        return JsonOutput.toJson(data_pojo);
    }
}
