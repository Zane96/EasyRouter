package com.zane.router.message;

import com.zane.router.exception.ConverterExpection;
import com.zane.router.EasyRouterSet;
import com.zane.router.utils.ZLog;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zane on 2017/4/16.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class MessageBuilder {

    static final String TAG_SCHEME_SUFFIX = "://";
    static final String TAG_PARAMS_DIVIDER = "&";
    static final String TAG_PARAMS_OPERATOR = "=";
    static final String TAG_ADDRESS_SUFFIX = "?";

    String scheme;
    String authority;
    String address;
    Map<String, String> params;
    Map<String, String> headers;

    public MessageBuilder() {
        params = new HashMap<>();
        headers = new HashMap<>();
    }

    public MessageBuilder addParam(String key, Object value, Type type) {
        try {
            String encodeValue = EasyRouterSet.getConverterFactory().encodeConverter(type).convert(value);
            params.put(key, encodeValue);
            ZLog.i("testparam " + key + " " + encodeValue);
        } catch (ConverterExpection e) {
            ZLog.e("Message$Builder" + e.getMessage());
        }
        return this;
    }

    public MessageBuilder addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public MessageBuilder setScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public MessageBuilder setAuthority(String authority) {
        this.authority = authority;
        return this;
    }

    public MessageBuilder setAddress(String address) {
        //寻找scheme的结束地址 与 authority的起始地址
        int schemeEndPos = address.indexOf(TAG_SCHEME_SUFFIX);
        int authorityStartPos = schemeEndPos + TAG_SCHEME_SUFFIX.length();
        if (!address.contains(TAG_SCHEME_SUFFIX)) {
            schemeEndPos = 0;
            authorityStartPos = 0;
        }
        setScheme(address.substring(0,schemeEndPos));
        setAuthority(address.substring(authorityStartPos,address.length()));
        return this;
    }

    public Message build() {
        return new Message(this);
    }
}
