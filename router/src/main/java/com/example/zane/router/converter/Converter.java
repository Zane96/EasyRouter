package com.example.zane.router.converter;

import com.example.zane.router.exception.ConverterExpection;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 数据传输过程中的数据序列化工具抽象
 *
 * Created by Zane on 2017/4/11.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public interface Converter<F, T> {

    /**
     * T -> To, F -> From
     * @param value
     * @return
     * @throws IOException
     */
    T convert(F value) throws ConverterExpection;

    abstract class Factory {

        /**
         * Creat encodeConventer
         * @param type
         * @return
         */
        public abstract Converter<? super Object, String> encodeConverter(Type type);

        /**
         * Creat decodeConventer
         * @param type
         * @return
         */
        public abstract Converter<String, ? super Object> decodeConverter(Type type);
    }
}
