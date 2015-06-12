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

package com.jana.android.connection.status;

import android.os.Parcel;

import com.jana.android.io.IJsonNode;
import com.jana.android.model.Model;

/**
 * @author islam
 */
public class ActionStatus<R, M> implements IJsonNode, Model {

    protected static final String UNAUTHORIZED_ACCESS = "unauthorized_user";

    private boolean mStatus;

    private R mResults;

    private M mMessage;

    public ActionStatus() {
    }

    /**
     * @return the mStatus
     */
    public boolean getStatus() {
        return mStatus;
    }

    /**
     * @param status the mStatus to set
     */
    public void setStatus(boolean status) {
        mStatus = status;
    }

    /**
     * @return the mResults
     */
    public R getResults() {
        return mResults;
    }

    /**
     * @param results the mResults to set
     */
    public void setResults(R results) {
        mResults = results;
    }

    /**
     * @return the mMessage
     */
    public M getMessage() {
        return mMessage;
    }

    /**
     * @param message the mMessage to set
     */
    public void setMessage(M message) {
        mMessage = message;
    }

    /**
     * Check the authority of the given access token
     *
     * @return
     */
    public boolean validate() {

        if (mMessage instanceof String) {

            return !(!mStatus
                    && UNAUTHORIZED_ACCESS.equalsIgnoreCase((String) mMessage));

        }

        return mStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public void readFromParcel(Parcel in) {
    }

}
