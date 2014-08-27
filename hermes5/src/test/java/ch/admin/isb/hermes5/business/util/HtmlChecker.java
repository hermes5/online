/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.util;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.w3c.tidy.Tidy;

public class HtmlChecker {

    public void checkHtmlString(String x) {
        Tidy tidy = new Tidy();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter out2 = new PrintWriter(out);
        tidy.setErrout(out2);
        try {
            tidy.parse(new ByteArrayInputStream(x.getBytes("UTF8")), new ByteArrayOutputStream());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
        out2.flush();
        assertEquals(x+"\n ERROR COMMENTS:\n"+ new String(out.toByteArray()), 0, tidy.getParseErrors());
        assertEquals(x+ "\n WARNING COMMENTS:\n"+new String(out.toByteArray()), 0, tidy.getParseWarnings());
    }
    
    public void checkPartHtmlString(String x) {
        Tidy tidy = new Tidy();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter out2 = new PrintWriter(out);
        tidy.setErrout(out2);
        try {
            tidy.parse(new ByteArrayInputStream(x.getBytes("UTF8")), new ByteArrayOutputStream());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
        out2.flush();
        assertEquals(x+"\n ERROR COMMENTS:\n"+ new String(out.toByteArray()), 0, tidy.getParseErrors());
        assertEquals(x+ "\n WARNING COMMENTS:\n"+new String(out.toByteArray()), 2, tidy.getParseWarnings());
    }
}
