package org.alsjava.sessions.configuration.converter;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

@SuppressWarnings("NullableProblems")
@Slf4j
public class FastJson2HttpMessageConverter extends AbstractJsonHttpMessageConverter {

    @Override
    protected Object readInternal(Type resolvedType, Reader reader) {
        log.debug("Reading object using FastJSON2");
        return JSON.parseObject(reader, resolvedType);
    }

    @Override
    protected void writeInternal(Object object, @Nullable Type type, Writer writer) throws Exception {
        log.debug("Writing object using FastJSON2");
        writer.write(JSON.toJSONString(object));
        writer.flush();
    }
}
