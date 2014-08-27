/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.printxml;

import java.util.List;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.postprocessor.InternalLinkPostProcessor;
import ch.admin.isb.hermes5.print.api.BookType;
import ch.admin.isb.hermes5.print.api.Chapter;
import ch.admin.isb.hermes5.print.api.Section;
import ch.admin.isb.hermes5.print.api.Subsection;
import ch.admin.isb.hermes5.print.api.Subsubsection;
import ch.admin.isb.hermes5.util.ReflectionUtil;
import ch.admin.isb.hermes5.util.StringUtil;

public class PrintXmlRendererUtil {

    @Inject
    InternalLinkPostProcessor internalLinkPostProcessor;

    @SuppressWarnings("rawtypes")
    public List getSubList(Object target) {
        if (target instanceof BookType) {
            return ((BookType) target).getChapter();
        }
        if (target instanceof Chapter) {
            return ((Chapter) target).getSection();
        }
        if (target instanceof Section) {
            return ((Section) target).getSubsection();
        }
        if (target instanceof Subsection) {
            return ((Subsection) target).getSubsubsection();
        }
        throw new IllegalStateException("unknown sublistfor " + target);
    }

    public Object getInstanceOfSub(Object target) {
        if (target instanceof BookType) {
            return new Chapter();
        }
        if (target instanceof Chapter) {
            return new Section();
        }
        if (target instanceof Section) {
            return new Subsection();
        }
        if (target instanceof Subsection) {
            return new Subsubsection();
        }
        throw new IllegalStateException("unknown subelement for " + target);
    }

    public void updateNameAndContent(Object target, String name, String content) {
        updateName(target, name);
        if (StringUtil.isNotBlank(content)) {
            ReflectionUtil.updateField(target, "content", content);
        }
    }

    public void updateName(Object target, String name) {
        if (StringUtil.isNotBlank(name)) {
            ReflectionUtil.updateField(target, "name", name);
        }
    }

}
