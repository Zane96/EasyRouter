package com.zane.router;

import com.zane.router.converter.Converter;

/**
 * EasyRouter配置类
 *
 * Created by Zane on 2017/4/11.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class EasyRouterSet {
    private static Converter.Factory converterFactory;

    public static Converter.Factory getConverterFactory() {
        return converterFactory;
    }

    public static void setConverterFactory(Converter.Factory converterFactory) {
        EasyRouterSet.converterFactory = converterFactory;
    }
}
