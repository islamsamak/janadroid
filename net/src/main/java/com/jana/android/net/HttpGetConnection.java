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

import org.apache.http.client.methods.HttpGet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author islam
 */
public class HttpGetConnection extends AbstractHttpConnection {

    protected Map<String, String> mParamsMap;

    protected Map<String, String> mHeadersMap;

    /**
     * Establish a HTTP connection
     */
    public HttpGetConnection() {
        super();

        mHeadersMap = new HashMap<String, String>(5);
        mParamsMap = new HashMap<String, String>();
    }

    public boolean addParam(String key, String value) {

        if (mParamsMap == null) {
            return false;
        }

        mParamsMap.put(key, value);

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

        StringBuilder params = new StringBuilder(100);

        updateParams(params);

        HttpGet httpGet = new HttpGet(url + params.toString());

        updateHeaders(httpGet);

        super.connect(httpGet);
    }

    private void updateHeaders(HttpGet httpGet) {

        if (mHeadersMap != null && mHeadersMap.size() > 0) {

            for (Iterator<String> iterator = mHeadersMap.keySet().iterator(); iterator
                    .hasNext(); ) {

                String key = iterator.next();

                String value = mHeadersMap.get(key);

                httpGet.setHeader(key, value);
            }
        }
    }

    private void updateParams(StringBuilder params) {
        if (mParamsMap != null && mParamsMap.size() > 0) {

            params.append("?");

            Set<Entry<String, String>> entries = mParamsMap.entrySet();

            int index = 0;

            int size = entries.size();

            for (Entry<String, String> entry : entries) {

                String key = entry.getKey();

                String value = entry.getValue();

                params.append(key).append("=").append(value);

                if (index < size - 1) {
                    params.append("&");
                }
            }
        }
    }
}
