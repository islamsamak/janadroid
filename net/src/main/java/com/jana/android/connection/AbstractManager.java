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

package com.jana.android.connection;

import com.jana.android.connection.status.ActionStatus;
import com.jana.android.connection.status.LoginStatus;
import com.jana.android.model.Session;
import com.jana.android.net.Connection;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;

import java.io.InputStream;

/**
 * @author islam
 */
public abstract class AbstractManager<T extends ActionStatus<?, ?>> {

    protected Session mSession;

    protected T mActionStatus;

    protected int mAuthorized = -1;

    protected int mBadRequest = -1;

    protected boolean executeConnection(Connection connection) {

        if (connection == null) {
            return false;
        }

        connection.connect();

        connection.hasResponse();

        StatusLine statusLine = connection.getStatus();

        int statusCode = -1;

        if (statusLine != null) {
            statusCode = statusLine.getStatusCode();
        }

        switch (statusCode) {

            case HttpStatus.SC_OK:

                setAuthorized(1);

                setHasBadRequest(-1);

                break;

            case HttpStatus.SC_BAD_REQUEST:

                setAuthorized(-1);

                setHasBadRequest(1);

                break;

            case HttpStatus.SC_UNAUTHORIZED:

                setAuthorized(0);

                setHasBadRequest(-1);

                break;

            default:
                return false;
        }

        // Parse the responded JSON, and check the validity of the
        // access token

        InputStream in = connection.getContent();

        T actionStatus = readResponse(in);

        if (actionStatus == null) {
            return false;
        }

        setActionStatus(actionStatus);

        if (actionStatus instanceof LoginStatus) {

            if (actionStatus.getStatus()) {

                setActiveSession(new Session((String) actionStatus.getResults()));

                return true;
            }

            return false;
        }

        handleActionStatus(actionStatus);

        return actionStatus.getStatus();
    }

    public boolean hasBadRequest() {
        return (mBadRequest == 1 ? true : false);
    }

    public boolean hasNoBadRequest() {
        return (mBadRequest == 0 ? true : false);
    }

    /**
     * @param badRequest the mBadRequest to set
     */
    public void setHasBadRequest(int badRequest) {
        this.mBadRequest = badRequest;
    }

    public boolean isAuthorized() {
        return (mAuthorized == 1 ? true : false);
    }

    /**
     * @param authorized the mIsAuthorized to set
     */
    public void setAuthorized(int authorized) {
        this.mAuthorized = authorized;
    }

    public boolean isNotAuthorized() {
        return (mAuthorized == 0 ? true : false);
    }

    protected abstract void handleActionStatus(T actionStatus);

    /**
     * @param in
     */
    protected abstract T readResponse(InputStream in);

    public Session getActiveSession() {
        return mSession;
    }

    protected void setActiveSession(Session session) {
        mSession = session;
    }

    public T getActionStatus() {
        return mActionStatus;
    }

    protected void setActionStatus(T status) {
        mActionStatus = status;
    }

}
