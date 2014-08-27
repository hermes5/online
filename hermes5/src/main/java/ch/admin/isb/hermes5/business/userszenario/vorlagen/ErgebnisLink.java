/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.vorlagen;

public class ErgebnisLink {

    public enum Type {
        TEMPLATE, URL;
    }
    
    
    private final String name;
    private final String url;
    private final Type type;

    public ErgebnisLink(String name, String url, Type type) {
        this.name = name;
        this.url = url;
        this.type = type;
    }

    
    @Override
    public String toString() {
        return "ErgebnisLink [name=" + name + ", url=" + url + ", type=" + type + "]";
    }


    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ErgebnisLink other = (ErgebnisLink) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type != other.type)
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

    

}