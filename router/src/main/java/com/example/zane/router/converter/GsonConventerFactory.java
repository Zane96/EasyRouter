package com.example.zane.router.converter;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by Zane on 2017/4/11.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class GsonConventerFactory extends Converter.Factory {

    private final Gson gson;

    public static Converter.Factory creat() {
        return new GsonConventerFactory(new Gson());
    }

    private GsonConventerFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<?, String> encodeConverter(Type type, Object object) {
        return new GsonEncodeConverter<>(type);
    }

    @Override
    public Converter<String, ?> decodeConverter(Type type, String string) {
        return new GsonDecodeConventer<>(type);
    }

    private class GsonEncodeConverter<T> implements Converter<T, String> {
        private Type type;

        public GsonEncodeConverter(Type type) {
            this.type = type;
        }

        @Override
        public String convert(T value) throws IOException {
            return gson.toJson(value, type);
        }
    }

    private class GsonDecodeConventer<T> implements Converter<String, T> {
        private Type type;

        public GsonDecodeConventer(Type type) {
            this.type = type;
        }

        @Override
        public T convert(String value) throws IOException {
            return gson.fromJson(value, type);
        }
    }
}
