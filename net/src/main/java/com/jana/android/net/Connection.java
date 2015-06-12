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

import org.apache.http.StatusLine;

import java.io.InputStream;

/**
 * @author islam
 */
public interface Connection {

    /**
     * Establish a HTTP connection
     */
    void connect();

    /**
     * Check if the connection is established and has a response, and the
     * response is valid "200"
     *
     * @return true if the connection response is valid, else returns false
     */
    boolean hasResponse();

    /**
     * If there is a established connection, it will return the status of it
     *
     * @return null if no connection is done, or the status
     */
    StatusLine getStatus();

    /**
     * Returns the content of the url is the there is a valid response
     *
     * @return Inputstream with the content, or null
     */
    InputStream getContent();

}
