/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.anwenderloesung;

import ch.admin.isb.hermes5.domain.AbstractMethodenElement;

public class RenderedElement {

    private String path;
    private String html;
    private AbstractMethodenElement element;

    public RenderedElement() {
    }

    public RenderedElement(String html, AbstractMethodenElement element) {
        this.html = html;
        this.element = element;
    }

    public RenderedElement(String path, String html, AbstractMethodenElement element) {
        this.path = path;
        this.html = html;
        this.element = element;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public AbstractMethodenElement getElement() {
        return element;
    }

    public void setElement(AbstractMethodenElement element) {
        this.element = element;
    }
}
