package com.uncub.blog.serializer;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.util.ByteUtils;

import java.nio.charset.Charset;

public class UncubSerializer<T> implements RedisSerializer {

    private String charSet = "utf8";
    private final String stringPrefix = "String~";
    private final String jsonPrefix = "json~";
    private final String jdkPrefix = "jdk~";
    /**
     * 是否优先json序列化
     */
    private boolean jsonFirst = true;

    private StringRedisSerializer stringRedisSerializer;
    private GenericFastJsonRedisSerializer genericFastJsonRedisSerializer;
    private JdkSerializationRedisSerializer jdkSerializationRedisSerializer;

    {
        stringRedisSerializer = new StringRedisSerializer(Charset.forName(charSet));
        genericFastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
    }


    /**
     * Serialize the given object to binary data.
     *
     * @param o object to serialize
     * @return the equivalent binary data
     */
    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        if (obj instanceof String) return stringRedisSerializer.serialize(stringPrefix + (String)obj);
        //基础类型使用字符串加载
        if (obj instanceof Integer || obj instanceof Long || obj instanceof Char || obj instanceof Double || obj instanceof Short || obj instanceof Byte || obj instanceof Boolean || obj instanceof Float)
            return stringRedisSerializer.serialize(stringPrefix + String.valueOf(obj));
        if (jsonFirst){
            return ByteUtils.concat(stringRedisSerializer.serialize(jsonPrefix) , genericFastJsonRedisSerializer.serialize(obj));
        }else{
            return ByteUtils.concat(stringRedisSerializer.serialize(jsonPrefix) , jdkSerializationRedisSerializer.serialize(obj));
        }
    }

    /**
     * Deserialize an object from the given binary data.
     *
     * @param bytes object binary representation
     * @return the equivalent object instance
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        String temp = stringRedisSerializer.deserialize(bytes);
        if (temp.startsWith(stringPrefix)) return (T)temp;
        else if (temp.startsWith(jsonPrefix)) {
            return (T)genericFastJsonRedisSerializer.deserialize(temp.substring(jsonPrefix.length(),temp.length()).getBytes(Charset.forName(charSet)));
        }
        else{
            return (T)jdkSerializationRedisSerializer.deserialize(temp.substring(jdkPrefix.length(),temp.length()).getBytes(Charset.forName(charSet)));
        }
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    public String getStringPrefix() {
        return stringPrefix;
    }

    public String getJsonPrefix() {
        return jsonPrefix;
    }

    public String getJdkPrefix() {
        return jdkPrefix;
    }

    public boolean isJsonFirst() {
        return jsonFirst;
    }

    public void setJsonFirst(boolean jsonFirst) {
        this.jsonFirst = jsonFirst;
    }

    public StringRedisSerializer getStringRedisSerializer() {
        return stringRedisSerializer;
    }

    public void setStringRedisSerializer(StringRedisSerializer stringRedisSerializer) {
        this.stringRedisSerializer = stringRedisSerializer;
    }

    public GenericFastJsonRedisSerializer getGenericFastJsonRedisSerializer() {
        return genericFastJsonRedisSerializer;
    }

    public void setGenericFastJsonRedisSerializer(GenericFastJsonRedisSerializer genericFastJsonRedisSerializer) {
        this.genericFastJsonRedisSerializer = genericFastJsonRedisSerializer;
    }

    public JdkSerializationRedisSerializer getJdkSerializationRedisSerializer() {
        return jdkSerializationRedisSerializer;
    }

    public void setJdkSerializationRedisSerializer(JdkSerializationRedisSerializer jdkSerializationRedisSerializer) {
        this.jdkSerializationRedisSerializer = jdkSerializationRedisSerializer;
    }
}
