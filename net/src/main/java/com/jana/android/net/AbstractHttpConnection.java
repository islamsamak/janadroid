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

import com.jana.android.app.AbstractApplication;
import com.jana.android.utils.Logger;
import com.jana.android.utils.UserAgentUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;

/**
 * @author islam
 */
public abstract class AbstractHttpConnection implements Connection {

    private static final int DEFAULT_MAX_RETRIES = 5;
    private static final int DEFAULT_RETRY_SLEEP_TIME_MILLIS = 1500;

    protected static DefaultHttpClient sHttpClient;

    protected String url;

    protected StatusLine statusLine;

    protected CookieManager cookieManager;

    protected HttpContext httpContext;

    protected HttpResponse httpResponse;

    public AbstractHttpConnection() {
        if (sHttpClient == null) {
            String userAgent = UserAgentUtils
                    .getDefaultUserAgent(AbstractApplication.getApplication());
            sHttpClient = DefaultHttpClient.newInstance(userAgent);
        }
    }

    protected void init() {
        setupHttp();
    }

    /**
     * Establish a HTTP connection to the given url with the specified params
     *
     * @param url the connection url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jana.android.net.Connection#hasResponse()
     */
    @Override
    public boolean hasResponse() {

        if (!hasConnection()) {
            return false;
        }

        StatusLine statusLine = httpResponse.getStatusLine();

        if (statusLine == null
                || HttpStatus.SC_OK != statusLine.getStatusCode()) {

            Logger.d("Invalid response for this url ' " + this.url + " '");

            return false;
        }

        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jana.android.net.Connection#getStatus()
     */
    @Override
    public StatusLine getStatus() {

        if (!hasConnection()) {
            return null;
        }

        StatusLine statusLine = httpResponse.getStatusLine();

        return statusLine;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jana.android.net.Connection#getContent()
     */
    @Override
    public InputStream getContent() {

        if (!hasConnection()) {
            return null;
        }

        HttpEntity httpEntity = httpResponse.getEntity();

        try {

            InputStream in = httpEntity.getContent();

            return in;

        } catch (IllegalStateException e) {

            Logger.w("No stream content as it has already been obtained, Error > "
                    + e.getMessage());

            e.printStackTrace();

            return null;
        } catch (IOException e) {

            Logger.w("No stream content as stream could not be created, Error > "
                    + e.getMessage());

            e.printStackTrace();

            return null;
        }
    }

    protected boolean hasConnection() {

        if (httpResponse == null) {
            Logger.w("No connection is established for this url ' " + this.url
                    + " '");

            return false;
        }

        return true;
    }

    protected void connect(HttpRequestBase httpMethod) {

        try {

            httpResponse = sHttpClient.execute(httpMethod, httpContext);

        } catch (IllegalStateException e) {

            Logger.w("Invalid url, Error > " + e.getMessage());

            e.printStackTrace();

        } catch (ClientProtocolException e) {

            Logger.w("HTTP Protocol Error > " + e.getMessage());

            e.printStackTrace();

        } catch (IOException e) {

            Logger.w("IO Error > " + e.getMessage());

            e.printStackTrace();
        }
    }

    private void setupHttp() {

        httpContext = new SyncBasicHttpContext(new BasicHttpContext());

        sHttpClient.setHttpRequestRetryHandler(new RetryHandler(
                DEFAULT_MAX_RETRIES, DEFAULT_RETRY_SLEEP_TIME_MILLIS));
    }

    public HttpContext getHttpContext() {
        return httpContext;
    }

    public void setCookieStore(CookieStore cookieStore) {
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
    }

}
