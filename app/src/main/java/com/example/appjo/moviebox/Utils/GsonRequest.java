package com.example.appjo.moviebox.Utils;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GsonRequest<T> extends JsonRequest<T> {

    private final Gson gson = new Gson();
    private final Class<T> classType;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private String mRequestBody;
    private static final String PROTOCOL_CHARSET="utf-8";
    private Map<String, String> mParams;
    private static final String TAG="ObjectRequest";


    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param classType Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     * @param jsonRequest JSON object containing POST request body data
     */
    public GsonRequest(int method, String url, Class<T> classType, Map<String, String> headers, JSONObject jsonRequest,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener, errorListener);
        this.classType = classType;
        this.headers = headers;
        this.listener = listener;
        if(jsonRequest != null){
            mRequestBody = gson.toJson(jsonRequest);
        }

    }

    public GsonRequest(int method, String url, Class<T> classType, Map<String, String> headers, Map<String, String> params, JSONObject jsonRequest,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener, errorListener);
        this.classType = classType;
        this.headers = headers;
        this.listener = listener;
        if (params != null){
            this.mParams = params;
        }
        if(jsonRequest != null){
            mRequestBody = gson.toJson(jsonRequest);
        }

    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams(){
        if (mParams != null){
            return mParams;
        }else {
            return null;
        }
    }

    @Override
    public byte[] getBody()  {
        try {
            return mRequestBody == null? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        }catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Unsupported Encoding while trying to get the bytes of " + mRequestBody +
                    "using " + PROTOCOL_CHARSET);
            return null;
        }
    }

    @Override
    public String getCacheKey() {
        String temp = super.getCacheKey();
        temp += mRequestBody;
        return temp;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
            if(shouldCache()) {
                if (cacheEntry == null) {
                    cacheEntry = new Cache.Entry();
                }
                final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                long now = System.currentTimeMillis();
                final long softExpire = now + cacheHitButRefreshed;
                final long ttl = now + cacheExpired;
                cacheEntry.data = response.data;
                cacheEntry.softTtl = softExpire;
                cacheEntry.ttl = ttl;
                String headerValue;
                headerValue = response.headers.get("Date");
                if (headerValue != null) {
                    cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                }
                headerValue = response.headers.get("Last-Modified");
                if (headerValue != null) {
                    cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                }
                cacheEntry.responseHeaders = response.headers;
            }
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, classType), cacheEntry);
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
