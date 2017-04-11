package com.example.zane.router.converter;

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
    T convert(F value) throws IOException;

    abstract class Factory {

        /**
         * Creat encodeConventer
         * @param type
         * @param object
         * @return
         */
        public abstract Converter<?, String> encodeConverter(Type type, Object object);

        /**
         * Creat decodeConventer
         * @param type
         * @param string
         * @return
         */
        public abstract Converter<String, ?> decodeConverter(Type type, String string);
    }
}
