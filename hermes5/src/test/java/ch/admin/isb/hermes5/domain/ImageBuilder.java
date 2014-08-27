/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import static ch.admin.isb.hermes5.domain.Status.*;

public class ImageBuilder {

    public static Image imageUnvollstaendig(int id, String urlDe) {
        return image(id, urlDe, UNVOLLSTAENDIG, UNVOLLSTAENDIG, UNVOLLSTAENDIG);
    }

    public static Image image(int id, String urlDe, Status statusFr, Status statusIt, Status statusEn) {
        Image image = new Image();
        image.setId((long)id);
        image.setUrlDe(urlDe);
        image.setStatusFr(statusFr);
        image.setStatusIt(statusIt);
        image.setStatusEn(statusEn);
        return image;
    }


}
