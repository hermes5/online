/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translation;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.util.StringUtil;


public class LocalizationEngineBuilder {
    public static LocalizationEngine getLocalizationEngineDe() {
        return getLocalizationEngine("de");
    }

    public static LocalizationEngine getLocalizationEngine(String lang) {
        TranslationRepository translationRepository = mock(TranslationRepository.class);
        when(translationRepository.getLocalizedText(anyString(), anyString(), anyString(), anyString())).thenAnswer(
                new Answer<String>() {

                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        return StringUtil.join(Arrays.asList(invocation.getArguments()), "/");
                    }
                });
        when(translationRepository.getDocumentUrl(anyString(), anyString(), anyString()))
        .thenAnswer(new Answer<String>() {

            public String answer(InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0] + "/" + invocation.getArguments()[2] + "/"
                        + invocation.getArguments()[1];
            }
        });
        return new LocalizationEngine(translationRepository, "model", lang);
    }
}
