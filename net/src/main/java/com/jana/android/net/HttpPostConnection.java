/*
 * Copyright (C) 2013 OneTeam (IslamSamak : islamsamak01@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jana.android.net;

import android.text.TextUtils;

import com.jana.android.utils.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author islam
 */
public class HttpPostConnection extends AbstractHttpConnection {

    protected Map<String, NameValuePair> mParamsMap;

    protected Map<String, String> mHeadersMap;

    private String paramsFromJson;

    /**
     * Establish a HTTP connection to the given url with the specified params
     */
    public HttpPostConnection() {
        super();

        mHeadersMap = new HashMap<String, String>(5);
        mParamsMap = new HashMap<String, NameValuePair>(5);
    }

    public boolean setParamsFromJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        paramsFromJson = json;
        return true;
    }

    public boolean addParam(String key, String value) {

        if (mParamsMap == null) {
            return false;
        }

        NameValuePair pair = new BasicNameValuePair(key, value);

        mParamsMap.put(key, pair);

        return true;
    }

    public boolean removeParam(String key) {

        if (mParamsMap != null && mParamsMap.containsKey(key)) {

            mParamsMap.remove(key);

            return true;
        }

        return false;
    }

    public boolean addHeader(String name, String value) {

        if (mHeadersMap == null) {
            return false;
        }

        mHeadersMap.put(name, value);

        return true;
    }

    public boolean removeHeader(String name) {

        if (mHeadersMap != null && mHeadersMap.containsKey(name)) {

            mHeadersMap.remove(name);

            return true;
        }

        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.jana.android.net.Connection#connect(java.lang.String,
     * java.lang.String[])
     */
    @Override
    public void connect() {

        HttpPost httpPost = new HttpPost(url);

        updateHeaders(httpPost);

        if (!TextUtils.isEmpty(paramsFromJson)) {
            updateParamsFromJson(httpPost);
        } else {
            updateParams(httpPost);
        }

        super.connect(httpPost);
    }

    private void updateHeaders(HttpPost httpPost) {

        if (mHeadersMap != null && mHeadersMap.size() > 0) {

            for (Iterator<String> iterator = mHeadersMap.keySet().iterator(); iterator
                    .hasNext(); ) {

                String key = iterator.next();

                String value = mHeadersMap.get(key);

                httpPost.setHeader(key, value);
            }
        }
    }

    private void updateParamsFromJson(HttpPost httpPost) {
        try {

            StringEntity entity = new StringEntity(paramsFromJson, HTTP.UTF_8);
//			entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
//					"application/json"));
            httpPost.setEntity(entity);

        } catch (UnsupportedEncodingException e) {

            Logger.w("Failure while establishing url connection as unsupported encoding error > "
                    + e.getMessage());

            e.printStackTrace();
        }
    }

    private void updateParams(HttpPost httpPost) {

        if (mParamsMap != null && mParamsMap.size() > 0) {

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>(
                    mParamsMap.values());

            try {

                httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

            } catch (UnsupportedEncodingException e) {

                Logger.w("Failure while establishing url connection as unsupported encoding error > "
                        + e.getMessage());

                e.printStackTrace();
            }
        }
    }
}
