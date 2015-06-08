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

package com.jana.android.core.task;

import android.os.AsyncTask;

/**
 * @author islam
 */
public class DefaultAsyncTask<P, G, R> extends AsyncTask<P, G, R> {

    protected TaskCallback<R> mCallback;

    public void setTaskCallback(TaskCallback<R> callback) {
        mCallback = callback;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onProgressUpdate(Progress[])
     */
    @Override
    protected void onProgressUpdate(G... values) {
        super.onProgressUpdate(values);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected R doInBackground(P... params) {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(R result) {
        super.onPostExecute(result);

        if (mCallback != null) {
            mCallback.onDone(result);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onCancelled(java.lang.Object)
     */
    @Override
    protected void onCancelled(R result) {
        super.onCancelled(result);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onCancelled()
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

}
