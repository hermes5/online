/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

public interface TranslationEntity {

    public enum TranslationEntityType {
        IMAGE("Bild", "Bilder"), DOCUMENT("Vorlage", "Vorlagen"), TEXT_ELEMENT("Textelement", "Textelemente");

        private String nameDe;
        private String nameDePlural;
        
        private TranslationEntityType(String nameDe, String nameDePlural) {
            this.nameDe = nameDe;
            this.nameDePlural = nameDePlural;
        }

        public String nameDe() {
            return nameDe;
        }

        public String nameDePlural() {
            return nameDePlural;
        }
    }
    
    Status getStatusFr();

    Status getStatusIt();

    Status getStatusEn();

    String getName();

    TranslationEntityType getType();

    String getTypeName();

}
