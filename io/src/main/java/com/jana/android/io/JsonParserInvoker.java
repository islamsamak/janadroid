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

package com.jana.android.io;

import android.util.JsonReader;

import com.jana.android.utils.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JsonParserInvoker<T> {

    public List<T> parseNodes(InputStream in, Parser<T> parser)
            throws IOException {

        if (in == null) {

            Logger.w("Invalid input stream");

            return null;
        }

        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

        try {

            return parseNodes(reader, parser);

        } finally {

            reader.close();
        }
    }

    protected List<T> parseNodes(JsonReader reader, Parser<T> parser)
            throws IOException {

        List<T> nodes = parser.execute(reader);

        return nodes;
    }

    // XXX
    // Remove the following code

    // protected List<IJsonNode> parseNodes(JsonReader reader, Parser parser)
    // throws IOException {
    //
    // List<IJsonNode> nodes = null;
    //
    // JsonToken token = reader.peek();
    //
    // if (token == JsonToken.BEGIN_ARRAY) {
    //
    // nodes = parseArray(reader, parser);
    //
    // } else if (token == JsonToken.BEGIN_OBJECT) {
    //
    // nodes = new ArrayList<IJsonNode>();
    //
    // IJsonNode node = parseObject(reader, parser);
    //
    // nodes.add(node);
    // } else {
    // reader.skipValue();
    // }
    //
    // return nodes;
    // }
    //
    // /**
    // * @param reader
    // * @param parser
    // * @throws IOException
    // */
    // protected IJsonNode parseObject(JsonReader reader, Parser parser)
    // throws IOException {
    //
    // IJsonNode node = null;
    //
    // reader.beginObject();
    //
    // while (reader.hasNext()) {
    //
    // node = parser.execute(reader);
    // }
    //
    // reader.endArray();
    //
    // return node;
    // }
    //
    // /**
    // * @param reader
    // * @param parser
    // * @throws IOException
    // */
    // protected List<IJsonNode> parseArray(JsonReader reader, Parser parser)
    // throws IOException {
    //
    // List<IJsonNode> nodes = new ArrayList<IJsonNode>();
    //
    // reader.beginArray();
    //
    // while (reader.hasNext()) {
    //
    // IJsonNode node = null;
    //
    // node = parser.execute(reader);
    //
    // nodes.add(node);
    // }
    //
    // reader.endArray();
    //
    // return nodes;
    // }

}
