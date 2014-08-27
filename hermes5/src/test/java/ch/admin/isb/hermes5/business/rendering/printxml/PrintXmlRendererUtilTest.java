/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.printxml;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.print.api.BookType;
import ch.admin.isb.hermes5.print.api.Chapter;
import ch.admin.isb.hermes5.print.api.Section;
import ch.admin.isb.hermes5.print.api.Subsection;
import ch.admin.isb.hermes5.print.api.Subsubsection;

public class PrintXmlRendererUtilTest {

    private BookType book;
    private Section section;
    private Subsection subsection;
    private Subsubsection subsubsection;
    private Chapter chapter;
    private PrintXmlRendererUtil util;

    @Before
    public void setUp() throws Exception {
        util = new PrintXmlRendererUtil();
        book = new BookType();
        chapter = new Chapter();
        section = new Section();
        subsection = new Subsection();
        subsubsection = new Subsubsection();
        book.getChapter().add(chapter);
        chapter.getSection().add(section);
        section.getSubsection().add(subsection);
        subsection.getSubsubsection().add(subsubsection);
    }

    @Test
    public void testGetSubListBook() {
        assertEquals(Arrays.asList(chapter), util.getSubList(book));
    }

    @Test
    public void testGetSubListChapter() {
        assertEquals(Arrays.asList(section), util.getSubList(chapter));
    }

    @Test
    public void testGetSubListSection() {
        assertEquals(Arrays.asList(subsection), util.getSubList(section));
    }

    @Test
    public void testGetSubListSubSection() {
        assertEquals(Arrays.asList(subsubsection), util.getSubList(subsection));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetSubListSubSubSectionShouldThrowIllegalState() {
        util.getSubList(subsubsection);
    }

    @Test
    public void testGetInstanceOfSubBook() {
        assertEquals(Chapter.class, util.getInstanceOfSub(book).getClass());
    }

    @Test
    public void testGetInstanceOfSubChapter() {
        assertEquals(Section.class, util.getInstanceOfSub(chapter).getClass());
    }

    @Test
    public void testGetInstanceOfSubSection() {
        assertEquals(Subsection.class, util.getInstanceOfSub(section).getClass());
    }

    @Test
    public void testGetInstanceOfSubSubsection() {
        assertEquals(Subsubsection.class, util.getInstanceOfSub(subsection).getClass());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetInstanceOfSubSubsubsectionShouldThrowIllegalState() {
        util.getInstanceOfSub(subsubsection);
    }
    
    @Test
    public void updateName() {
        util.updateName(chapter, "kapitel5");
        assertEquals("kapitel5", chapter.getName());
    }
    @Test
    public void updateNameAndContent() {
        util.updateNameAndContent(chapter, "kapitel5", "inhalt10");
        assertEquals("kapitel5", chapter.getName());
        assertEquals("inhalt10", chapter.getContent());
    }
}
