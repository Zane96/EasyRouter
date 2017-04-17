package com.example.zane.router.message;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.example.zane.router.message.MessageBuilder.TAG_ADDRESS_SUFFIX;
import static com.example.zane.router.message.MessageBuilder.TAG_PARAMS_DIVIDER;
import static com.example.zane.router.message.MessageBuilder.TAG_PARAMS_OPERATOR;
import static com.example.zane.router.message.MessageBuilder.TAG_SCHEME_SUFFIX;

/**
 * 报文的实体封装类
 * Url: activity://  xxxxxxxxx
 *     scheme         authority
 *      -----------------------
 *          url
 *
 *      Message
 * ----------------
 * -     URL      -
 * ----------------
 * -     Header   -
 * ----------------
 * -     Body     -
 * ----------------
 *
 * Created by Zane on 2017/4/10.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class Message implements Parcelable {

    private Url url;
    private Header header;
    private Body body;

    public Message(MessageBuilder builder) {
        header = new Header(builder.headers);
        body = new Body(builder.params);
        url = new Url(builder.scheme, builder.authority);
    }

    public MessageBuilder newBuilder(){
        MessageBuilder builder = new MessageBuilder();
        builder.scheme = url.getScheme();
        builder.authority = url.getAuthority();
        builder.params = body.getDatas();
        builder.headers = header.getHeaders();
        return builder;
    }

    public Url getUrl() {
        return url;
    }

    public Header getHeader() {
        return header;
    }

    public Body getBody() {
        return body;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void removeUrl() {
        url = null;
    }

    public void removeHeader() {
        header = null;
    }

    public void removeBody() {
        body = null;
    }

    //----------------------------------------------------------------------------------------------
    public static class Url implements Parcelable {

        private String baseUrl;
        private String scheme;
        private String authority;

        public Url(String scheme, String authority) {
            if (isEmpty(scheme) || isEmpty(authority)) {
                String errorInfo = isEmpty(scheme) ? "scheme can't be null or empty" : "authority can't be null or empty";
                throw new IllegalArgumentException(errorInfo);
            }
            this.scheme = scheme;
            this.authority = authority;
            this.baseUrl = scheme + TAG_SCHEME_SUFFIX + authority;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public String getScheme() {
            return scheme;
        }

        public String getAuthority() {
            return authority;
        }

        @Override
        public String toString() {
            return baseUrl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.baseUrl);
            dest.writeString(this.scheme);
            dest.writeString(this.authority);
        }

        protected Url(Parcel in) {
            this.baseUrl = in.readString();
            this.scheme = in.readString();
            this.authority = in.readString();
        }

        public static final Creator<Url> CREATOR = new Creator<Url>() {
            @Override
            public Url createFromParcel(Parcel source) {
                return new Url(source);
            }

            @Override
            public Url[] newArray(int size) {
                return new Url[size];
            }
        };
    }

    //----------------------------------------------------------------------------------------------
    public static class Header implements Parcelable {
        private Map<String, String> headers;

        public Header(Map<String, String> headers) {
            this.headers = headers;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        @Override
        public String toString() {
            return wrapString(headers);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.headers.size());
            for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeString(entry.getValue());
            }
        }

        protected Header(Parcel in) {
            int headersSize = in.readInt();
            this.headers = new HashMap<String, String>(headersSize);
            for (int i = 0; i < headersSize; i++) {
                String key = in.readString();
                String value = in.readString();
                this.headers.put(key, value);
            }
        }

        public static final Creator<Header> CREATOR = new Creator<Header>() {
            @Override
            public Header createFromParcel(Parcel source) {
                return new Header(source);
            }

            @Override
            public Header[] newArray(int size) {
                return new Header[size];
            }
        };
    }

    //----------------------------------------------------------------------------------------------

    public static class Body implements Parcelable {
        private Map<String, String> datas;

        public Body(Map<String, String> datas) {
            this.datas = datas;
        }

        public Map<String, String> getDatas() {
            return datas;
        }

        @Override
        public String toString() {
            return wrapString(datas);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.datas.size());
            for (Map.Entry<String, String> entry : this.datas.entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeString(entry.getValue());
            }
        }

        protected Body(Parcel in) {
            int datasSize = in.readInt();
            this.datas = new HashMap<String, String>(datasSize);
            for (int i = 0; i < datasSize; i++) {
                String key = in.readString();
                String value = in.readString();
                this.datas.put(key, value);
            }
        }

        public static final Creator<Body> CREATOR = new Creator<Body>() {
            @Override
            public Body createFromParcel(Parcel source) {
                return new Body(source);
            }

            @Override
            public Body[] newArray(int size) {
                return new Body[size];
            }
        };
    }

    //---------------------------------------------------------------------------------------------

    private static String getString(String value) {
        return value == null ? "" : value;
    }

    private static boolean isEmpty(String value){
        return value == null || value.isEmpty();
    }

    private static String wrapString(Map<String, String> datas) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> entrySet = datas.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            sb.append(entry.getKey())
                    .append(TAG_PARAMS_OPERATOR)
                    .append(entry.getValue())
                    .append(TAG_PARAMS_DIVIDER);
        }
        return sb.toString();
    }

    //    /**
//     * String转URL
//     * @param urlStr
//     * @return
//     */
//    public static Message parse(String urlStr) {
//        Message.Builder urlBuilder = new Message.Builder();
//
//        //寻找address的结尾地址
//        int addressEndPos = 0;
//        int paramsStartPos = 0;
//        if (!urlStr.contains(TAG_ADDRESS_SUFFIX)) {
//            if (urlStr.contains(TAG_PARAMS_OPERATOR)){
//                addressEndPos = 0;
//                paramsStartPos = 0;
//            }else {
//                addressEndPos = urlStr.length();
//                paramsStartPos = addressEndPos;
//            }
//        }else {
//            addressEndPos = urlStr.indexOf(TAG_ADDRESS_SUFFIX);
//            paramsStartPos = addressEndPos+TAG_ADDRESS_SUFFIX.length();
//        }
//
//        //寻找scheme的结束地址 与 authority的起始地址
//        int schemeEndPos = urlStr.indexOf(TAG_SCHEME_SUFFIX);
//        int authorityStartPos = schemeEndPos + TAG_SCHEME_SUFFIX.length();
//        if (!urlStr.contains(TAG_SCHEME_SUFFIX)) {
//            schemeEndPos = 0;
//            authorityStartPos = 0;
//        }
//
//        String scheme = urlStr.substring(0, schemeEndPos);
//        String authority = urlStr.substring(authorityStartPos, addressEndPos);
//        String params = urlStr.substring(paramsStartPos, urlStr.length());
//
//        urlBuilder.setScheme(scheme);
//        urlBuilder.setAuthority(authority);
//
//        if (params != null && !params.isEmpty()) {
//            String[] paramsEntries = params.split(TAG_PARAMS_DIVIDER);
//            for (String entry : paramsEntries) {
//                if (entry.contains(TAG_PARAMS_OPERATOR)) {
//                    int operatorPos = entry.indexOf(TAG_PARAMS_OPERATOR);
//                    urlBuilder.addParam(entry.substring(0,operatorPos), entry.substring(operatorPos+1,entry.length()));
//                } else {
//                    throw new IllegalArgumentException("body format error");
//                }
//            }
//        }
//
//        return urlBuilder.build();
//    }

    @Override
    public String toString() {
        return url.toString() + TAG_ADDRESS_SUFFIX + header.toString() + TAG_ADDRESS_SUFFIX + body.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.url, flags);
        dest.writeParcelable(this.header, flags);
        dest.writeParcelable(this.body, flags);
    }

    protected Message(Parcel in) {
        this.url = in.readParcelable(Url.class.getClassLoader());
        this.header = in.readParcelable(Header.class.getClassLoader());
        this.body = in.readParcelable(Body.class.getClassLoader());
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
