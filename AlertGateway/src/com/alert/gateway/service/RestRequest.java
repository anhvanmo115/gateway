/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alert.gateway.service;

import com.alert.gateway.utils.SafeConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.BaseEncoding;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
//import java.util.Base64;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author v3-os02
 */
public class RestRequest {

    protected static final Logger LOG = Logger.getLogger(RestRequest.class);

    public static JSONObject getJSONObject(String sUrl) {
        JSONObject jsResult = null;

        try {
            URL url = new URL(sUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            //hoahv5: 18092017
//            String encoded = Base64.getEncoder().encodeToString(("admin:Pccc@114").getBytes(StandardCharsets.UTF_8));  //Java 8
            //Java 7
//            String encoded = BaseEncoding.base64().encode(("admin:Pccc@114").getBytes(StandardCharsets.UTF_8));
            String encoded = BaseEncoding.base64().encode((SafeConfig.getInstance().getServiceAuthorization()).getBytes(StandardCharsets.UTF_8));
            conn.setRequestProperty("APP_CODE", SafeConfig.getInstance().getAppCode());
            conn.setRequestProperty("Authorization", "Basic " + encoded);

            if (conn.getResponseCode() != 200) {
                LOG.error(String.format("An error happen when calling getJSONObject with url=%s (HTTP error code=%d)", sUrl, conn.getResponseCode()));
                return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder result = new StringBuilder();
            String output;

            while ((output = br.readLine()) != null) {
                result.append(output);
            }

            conn.disconnect();

            jsResult = new JSONObject(result.toString());

        } catch (MalformedURLException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException | JSONException e) {
            LOG.error(e.getMessage(), e);
        }

        return jsResult;
    }

    public static Object getObject(String sUrl, Class clazz) {
        Object obj = null;

        try {
            URL url = new URL(sUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            //hoahv5: 18092017
//            String encoded = Base64.getEncoder().encodeToString(("admin:Pccc@114").getBytes(StandardCharsets.UTF_8));  //Java 8
//            String encoded = BaseEncoding.base64().encode(("admin:Pccc@114").getBytes(StandardCharsets.UTF_8));
            String encoded = BaseEncoding.base64().encode((SafeConfig.getInstance().getServiceAuthorization()).getBytes(StandardCharsets.UTF_8));
            conn.setRequestProperty("APP_CODE", SafeConfig.getInstance().getAppCode());
            conn.setRequestProperty("Authorization", "Basic " + encoded);

            if (conn.getResponseCode() != 200) {
                LOG.error(String.format("An error happen when calling getObject with url=%s (HTTP error code=%d)", sUrl, conn.getResponseCode()));
                return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder result = new StringBuilder();
            String output;

            while ((output = br.readLine()) != null) {
                result.append(output);
            }

            conn.disconnect();

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            obj = mapper.readValue(result.toString(), clazz);

        } catch (MalformedURLException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        return obj;
    }

    public static List getObjectArray(String sUrl, Class clazz) {
        List objs = null;

        try {
            URL url = new URL(sUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            //hoahv5: 18092017
//            String encoded = Base64.getEncoder().encodeToString(("admin:Pccc@114").getBytes(StandardCharsets.UTF_8));  //Java 8
            //            String encoded = BaseEncoding.base64().encode(("admin:Pccc@114").getBytes(StandardCharsets.UTF_8));
            String encoded = BaseEncoding.base64().encode((SafeConfig.getInstance().getServiceAuthorization()).getBytes(StandardCharsets.UTF_8));
            conn.setRequestProperty("APP_CODE", SafeConfig.getInstance().getAppCode());
            conn.setRequestProperty("Authorization", "Basic " + encoded);

            if (conn.getResponseCode() != 200) {
                LOG.error(String.format("An error happen when calling getObjectArray with url=%s (HTTP error code=%d)", sUrl, conn.getResponseCode()));
                return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder result = new StringBuilder();
            String output;

            while ((output = br.readLine()) != null) {
                result.append(output);
            }

            conn.disconnect();

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //objs = mapper.readValue(result.toString(), new TypeReference<List<clazz>>(){});
            objs = mapper.readValue(result.toString(), mapper.getTypeFactory().constructCollectionType(List.class, clazz));

        } catch (MalformedURLException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        return objs;
    }

    public static String postAndReturnString(String sUrl, Object obj) {

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(sUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            //hoahv5: 18092017
//            String encoded = Base64.getEncoder().encodeToString(("admin:Pccc@114").getBytes(StandardCharsets.UTF_8));  //Java 8
//            String encoded = BaseEncoding.base64().encode(("admin:Pccc@114").getBytes(StandardCharsets.UTF_8));
            String encoded = BaseEncoding.base64().encode((SafeConfig.getInstance().getServiceAuthorization()).getBytes(StandardCharsets.UTF_8));

            conn.setRequestProperty("APP_CODE", SafeConfig.getInstance().getAppCode());
            conn.setRequestProperty("Authorization", "Basic " + encoded);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            try (OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream())) {
                JSONObject jsObj = new JSONObject(obj);
                LOG.info("Post Json Object: " + jsObj.toString());
                //out.write(jsObj.toString());
                out.write(jsObj.toString());
            }

            if (conn.getResponseCode() != 200) {
                LOG.error(String.format("An error happen when calling postAndReturnString with url=%s (HTTP error code=%d)", sUrl, conn.getResponseCode()));
                return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;

            while ((output = br.readLine()) != null) {
                result.append(output);
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        return result.toString();
    }

    public static Object postAndReturnObject(String sUrl, Object paramObject, Class resultClass) {
        String result = postAndReturnString(sUrl, paramObject);

        if (result == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Object resultObject = new Object();
        try {
            resultObject = mapper.readValue(result, resultClass);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        return resultObject;
    }

    public static List postAndReturnObjectArray(String sUrl, Object obj, Class clazz) {
        String result = postAndReturnString(sUrl, obj);

        if (result == null) {
            return null;
        }

        List objs = null;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            objs = mapper.readValue(result, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        return objs;
    }

}
