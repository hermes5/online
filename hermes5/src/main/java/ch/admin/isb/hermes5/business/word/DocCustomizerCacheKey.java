/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.word;

import java.util.Arrays;

import ch.admin.isb.hermes5.domain.SzenarioUserData;

public class DocCustomizerCacheKey {

    private final String url;
    private final byte[] wordFile;
    private final String auftraggeber;
    private final String firma1;
    private final String firma2;
    private final byte[] logo;
    private String projektleiter;
    private String projektname;
    private String projektnummer;

    public DocCustomizerCacheKey(String url, byte[] wordFile, SzenarioUserData szenarioUserData) {
        this.url = url;
        this.wordFile = wordFile;
        auftraggeber = szenarioUserData.getAuftraggeber();
        firma1 = szenarioUserData.getFirma1();
        firma2 = szenarioUserData.getFirma2();
        logo = szenarioUserData.getLogo();
        projektleiter = szenarioUserData.getProjektleiter();
        projektname = szenarioUserData.getProjektname();
        projektnummer = szenarioUserData.getProjektnummer();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((auftraggeber == null) ? 0 : auftraggeber.hashCode());
        result = prime * result + ((firma1 == null) ? 0 : firma1.hashCode());
        result = prime * result + ((firma2 == null) ? 0 : firma2.hashCode());
        result = prime * result + Arrays.hashCode(logo);
        result = prime * result + ((projektleiter == null) ? 0 : projektleiter.hashCode());
        result = prime * result + ((projektname == null) ? 0 : projektname.hashCode());
        result = prime * result + ((projektnummer == null) ? 0 : projektnummer.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + Arrays.hashCode(wordFile);
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
        DocCustomizerCacheKey other = (DocCustomizerCacheKey) obj;
        if (auftraggeber == null) {
            if (other.auftraggeber != null)
                return false;
        } else if (!auftraggeber.equals(other.auftraggeber))
            return false;
        if (firma1 == null) {
            if (other.firma1 != null)
                return false;
        } else if (!firma1.equals(other.firma1))
            return false;
        if (firma2 == null) {
            if (other.firma2 != null)
                return false;
        } else if (!firma2.equals(other.firma2))
            return false;
        if (!Arrays.equals(logo, other.logo))
            return false;
        if (projektleiter == null) {
            if (other.projektleiter != null)
                return false;
        } else if (!projektleiter.equals(other.projektleiter))
            return false;
        if (projektname == null) {
            if (other.projektname != null)
                return false;
        } else if (!projektname.equals(other.projektname))
            return false;
        if (projektnummer == null) {
            if (other.projektnummer != null)
                return false;
        } else if (!projektnummer.equals(other.projektnummer))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        if (!Arrays.equals(wordFile, other.wordFile))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DocCustomizerCacheKey [url=" + url + ", hashcode=" + hashCode() + "]";
    }
}
