package org.alsjava.sessions;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.alsjava.sessions.model.DataDto;
import org.alsjava.sessions.model.UserDto;
import org.alsjava.sessions.model.fory.DemoEnum;
import org.alsjava.sessions.model.fory.Wrapper;
import org.alsjava.sessions.test.IntegrationTest;
import org.apache.fory.BaseFory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ForyTest extends IntegrationTest {

    public static final int REPETITION = 100;

    @Autowired
    private BaseFory fory;

    private static Wrapper wrapper;

    @BeforeAll
    static void init() {
        log.info("Init data creation");
        wrapper = Wrapper.builder()
                .users(generateUserDto(200, 20))
                .build();
        log.info("End data creation");
    }

    @Order(0)
    @Test
    void initTest() throws IOException {
        fory.register(Wrapper.class);
        fory.register(UserDto.class);
        fory.register(DataDto.class);
        fory.register(DemoEnum.class);
        ByteArrayOutputStream fastjson2ByteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream jacksonByteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream foryByteArrayOutputStream = new ByteArrayOutputStream();
        fastjson2ByteArrayOutputStream.write(JSON.toJSONBytes(wrapper));
        jacksonByteArrayOutputStream.write(jsonMapper.writeValueAsBytes(wrapper));
        foryByteArrayOutputStream.write(fory.serialize(wrapper));
        log.info("Repetitions: {}", REPETITION);
        log.info("Serialized data in bytes");
        log.info("FastJson2: {}", fastjson2ByteArrayOutputStream.size());
        log.info("Jackson: {}", jacksonByteArrayOutputStream.size());
        log.info("Fory: {}", foryByteArrayOutputStream.size());
    }

    @Order(1)
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class SingleThreadClass {

        @Order(1)
        @Nested
        @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
        public class SerializeTest {

            @RepeatedTest(REPETITION)
            void fastjson2SerializeTest() throws IOException {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(JSON.toJSONBytes(wrapper));
            }

            @RepeatedTest(REPETITION)
            void jacksonSerializeTest() throws IOException {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(jsonMapper.writeValueAsBytes(wrapper));
            }

            @RepeatedTest(REPETITION)
            void forySerializeTest() throws IOException {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(fory.serialize(wrapper));
            }
        }

        @Order(1)
        @Nested
        @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
        public class DeserializeTest {

            private static final ByteArrayOutputStream fastjson2ByteArrayOutputStream = new ByteArrayOutputStream();
            private static final ByteArrayOutputStream jacksonByteArrayOutputStream = new ByteArrayOutputStream();
            private static final ByteArrayOutputStream foryByteArrayOutputStream = new ByteArrayOutputStream();

            @Order(1)
            @Test
            void preparationTest() throws IOException {
                fastjson2ByteArrayOutputStream.write(JSON.toJSONBytes(wrapper));
                jacksonByteArrayOutputStream.write(jsonMapper.writeValueAsBytes(wrapper));
                foryByteArrayOutputStream.write(fory.serialize(wrapper));
            }

            @Order(Integer.MAX_VALUE)
            @Test
            void comparationTest() {
                String javaSerialization = wrapper.toString();
                log.info("Java Serialization: {}", javaSerialization);
                Assertions.assertEquals(JSON.parseObject(fastjson2ByteArrayOutputStream.toByteArray(), Wrapper.class).toString(), javaSerialization);
                Assertions.assertEquals(jsonMapper.readValue(jacksonByteArrayOutputStream.toByteArray(), Wrapper.class).toString(), javaSerialization);
                Assertions.assertEquals(fory.deserialize(foryByteArrayOutputStream.toByteArray(), Wrapper.class).toString(), javaSerialization);
            }

            @RepeatedTest(REPETITION)
            void fastjson2SerializeTest() {
                JSON.parseObject(fastjson2ByteArrayOutputStream.toByteArray(), Wrapper.class);
            }

            @RepeatedTest(REPETITION)
            void jacksonSerializeTest() {
                jsonMapper.readValue(jacksonByteArrayOutputStream.toByteArray(), Wrapper.class);
            }

            @RepeatedTest(REPETITION)
            void forySerializeTest() {
                fory.deserialize(foryByteArrayOutputStream.toByteArray(), Wrapper.class);
            }
        }
    }

    @Order(2)
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class MultiThreadClass {

        @Nested
        @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
        public class SerializeTest {

            @Execution(ExecutionMode.CONCURRENT)
            @RepeatedTest(REPETITION)
            void fastjson2SerializeTest() {
                ByteArrayOutputStream fastjson2ByteArrayOutputStream = new ByteArrayOutputStream();
                JSON.parseObject(fastjson2ByteArrayOutputStream.toByteArray(), Wrapper.class);
            }

            @Execution(ExecutionMode.CONCURRENT)
            @RepeatedTest(REPETITION)
            void jacksonSerializeTest() throws IOException {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(jsonMapper.writeValueAsBytes(wrapper));
            }

            @Execution(ExecutionMode.CONCURRENT)
            @RepeatedTest(REPETITION)
            void forySerializeTest() throws IOException {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(fory.serialize(wrapper));
            }
        }

        @Nested
        @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
        public class DeserializeTest {

            private static final ByteArrayOutputStream fastjson2ByteArrayOutputStream = new ByteArrayOutputStream();
            private static final ByteArrayOutputStream jacksonByteArrayOutputStream = new ByteArrayOutputStream();
            private static final ByteArrayOutputStream foryByteArrayOutputStream = new ByteArrayOutputStream();

            @Order(1)
            @Test
            void preparationTest() throws IOException {
                fastjson2ByteArrayOutputStream.write(JSON.toJSONBytes(wrapper));
                jacksonByteArrayOutputStream.write(jsonMapper.writeValueAsBytes(wrapper));
                foryByteArrayOutputStream.write(fory.serialize(wrapper));
            }

            @Order(Integer.MAX_VALUE)
            @Test
            void comparationTest() {
                String javaSerialization = wrapper.toString();
                log.info("Java Serialization: {}", javaSerialization);
                Assertions.assertEquals(JSON.parseObject(fastjson2ByteArrayOutputStream.toByteArray(), Wrapper.class).toString(), javaSerialization);
                Assertions.assertEquals(jsonMapper.readValue(jacksonByteArrayOutputStream.toByteArray(), Wrapper.class).toString(), javaSerialization);
                Assertions.assertEquals(fory.deserialize(foryByteArrayOutputStream.toByteArray(), Wrapper.class).toString(), javaSerialization);
            }

            @Execution(ExecutionMode.CONCURRENT)
            @RepeatedTest(REPETITION)
            void fastjson2SerializeTest() {
                JSON.parseObject(fastjson2ByteArrayOutputStream.toByteArray(), Wrapper.class);
            }

            @Execution(ExecutionMode.CONCURRENT)
            @RepeatedTest(REPETITION)
            void jacksonSerializeTest() {
                jsonMapper.readValue(jacksonByteArrayOutputStream.toByteArray(), Wrapper.class);
            }

            @Execution(ExecutionMode.CONCURRENT)
            @RepeatedTest(REPETITION)
            void forySerializeTest() {
                fory.deserialize(foryByteArrayOutputStream.toByteArray(), Wrapper.class);
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static List<UserDto> generateUserDto(int quantity, int dataQuantity) {
        List<UserDto> users = new ArrayList<>();
        List<DataDto> dataDtos = generateData(dataQuantity);
        for (int i = 0; i < quantity; i++) {
            UserDto userDto = UserDto.builder()
                    .name("name-" + i)
                    .description("Description-" + i)
                    .data(dataDtos)
                    .date(LocalDate.now())
                    .build();
            users.add(userDto);
        }
        return users;
    }

    private static List<DataDto> generateData(int quantity) {
        List<DataDto> data = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            DataDto dataDto = DataDto.builder()
                    .name("name-" + i)
                    .description("Description-" + i)
                    .demoEnum((i & 1) == 0 ? DemoEnum.EXTRA : DemoEnum.NORMAL)
                    .num(i)
                    .bigNumber((i * 100) + 0.14526)
                    .extraBigNumber(new BigDecimal("484859554984522"))
                    .build();
            data.add(dataDto);
        }
        return data;
    }
}
