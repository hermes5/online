/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.validation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class UserInputValidatorTest {

    private UserInputValidator userInputValidator;

    @Before
    public void setUp() throws Exception {
        userInputValidator = new UserInputValidator();
    }


    @Test
    public void testWithValidInput() {
        initDefault();
        assertTrue(userInputValidator.isValid("abc", null));
    }

    @Test
    public void testWithValidInputVerticalBar() {
        initDefault();
        assertTrue(userInputValidator.isValid("asfdsadf|dsafa", null));
    }

    @Test
    public void testWithValidInputSlash() {
        initDefault();
        assertTrue(userInputValidator.isValid("df/adf", null));
    }

    @Test
    public void testWithScriptInput() {
        initDefault();
        assertFalse(userInputValidator.isValid("<script>abc</script>", null));
    }

    @Test
    public void testWithInvalidSignsBackslash() {
        initDefault();
        assertFalse(userInputValidator.isValid("aa\\abb", null));
    }
   
    @Test
    public void testWithInvalidSignsCurlyOpen() {
        initDefault();
        assertFalse(userInputValidator.isValid("df{adf", null));
    }

    @Test
    public void testWithInvalidSignsCurlyClose() {
        initDefault();
        assertFalse(userInputValidator.isValid("df}adf", null));
    }

    @Test
    public void testWithInvalidSignsSquareOpen() {
        initDefault();
        assertFalse(userInputValidator.isValid("df[adf", null));
    }

    @Test
    public void testWithInvalidSignsSquareClose() {
        initDefault();
        assertFalse(userInputValidator.isValid("df]adf", null));
    }

    @Test
    public void testWithInvalidSignsLT() {
        assertFalse(userInputValidator.isValid("df<adf", null));
    }

    @Test
    public void testWithInvalidSignsGT() {
        initDefault();
        assertFalse(userInputValidator.isValid("df>adf", null));
    }

    @Test
    public void testWithInvalidSignsEQ() {
        initDefault();
        assertFalse(userInputValidator.isValid("df=adf", null));
    }

    @Test
    public void testWithBlankInput() {
        initDefault();
        assertTrue(userInputValidator.isValid("", null));
    }

    @Test
    public void testWithNullNull() {
        initDefault();
        assertTrue(userInputValidator.isValid(null, null));
    }

    @Test
    public void testWithWhitespaceInput() {
        initDefault();
        assertTrue(userInputValidator.isValid(" \n ", null));
    }

    @Test
    public void testWithInvalidInputTooLong() {
        initMaxLength();
        assertFalse(userInputValidator.isValid("12345", null));
    }

    @Test
    public void testWithInvalidInputTooShort() {
        initMinLength();
        assertFalse(userInputValidator.isValid("12", null));
    }

    @Test
    public void testWithCharsNumbersSpacesPatternSmallLetters() {
        initCharsNumbersSpaces();
        assertTrue(userInputValidator.isValid("abcdefghijklmnopqrstuvwxyz", null));
    }

    @Test
    public void testWithCharsNumbersSpacesPatternBigLetters() {
        initCharsNumbersSpaces();
        assertTrue(userInputValidator.isValid("ABCDEFGHIJKLMNOPQRSTUVWXYZ", null));
    }

    @Test
    public void testWithCharsNumbersSpacesPatternDigits() {
        initCharsNumbersSpaces();
        assertTrue(userInputValidator.isValid("1234567890", null));
    }

    @Test
    public void testWithCharsNumbersSpacesPatternAllowedSpecialChars() {
        initCharsNumbersSpaces();
        assertTrue(userInputValidator.isValid("._-/:", null));
    }

    @Test
    public void testWithCharsNumbersSpacesPatternUmlaute() {
        initCharsNumbersSpaces();
        assertFalse(userInputValidator.isValid("äöü", null));
    }
    
    @Test
    public void testWithHtmlValidTags() {
        initHtml();
        assertTrue(userInputValidator.isValid("<div><p><i>Test</i><br /><b>Test2</b></p></div>", null));
    }

    @Test
    public void testWithHtmlPlainText() {
        initHtml();
        assertTrue(userInputValidator.isValid("Test", null));
    }

    @Test
    public void testWithHtmlScriptTag() {
        initHtml();
        assertFalse(userInputValidator.isValid("<script>", null));
        assertFalse(userInputValidator.isValid("<script type=\"text/javascript\">", null));
        assertFalse(userInputValidator.isValid("</script>", null));
    }

    @Test
    public void testWithHtmlHtmlTag() {
        initHtml();
        assertFalse(userInputValidator.isValid("<html>", null));
        assertFalse(userInputValidator.isValid("<HTML>", null));
        assertFalse(userInputValidator.isValid("<hTmL>", null));
        assertFalse(userInputValidator.isValid("</html>", null));
    }

    @Test
    public void testWithHtmlHeadTag() {
        initHtml();
        assertFalse(userInputValidator.isValid("<head>", null));
        assertFalse(userInputValidator.isValid("</head>", null));
    }

    @Test
    public void testWithHtmlBodyTag() {
        initHtml();
        assertFalse(userInputValidator.isValid("<body>", null));
        assertFalse(userInputValidator.isValid("</body>", null));
    }


    private void initDefault() {
        UserInput mock = mock(UserInput.class);
        when(mock.min()).thenReturn(0);
        when(mock.max()).thenReturn(255);
        when(mock.pattern()).thenReturn(UserInput.DEFAULT_PATTERN);
        userInputValidator.initialize(mock);
    }

    private void initMaxLength() {
        UserInput mock = mock(UserInput.class);
        when(mock.min()).thenReturn(0);
        when(mock.max()).thenReturn(3);
        when(mock.pattern()).thenReturn(UserInput.DEFAULT_PATTERN);
        userInputValidator.initialize(mock);
    }

    private void initMinLength() {
        UserInput mock = mock(UserInput.class);
        when(mock.min()).thenReturn(3);
        when(mock.max()).thenReturn(255);
        when(mock.pattern()).thenReturn(UserInput.DEFAULT_PATTERN);
        userInputValidator.initialize(mock);
    }

    private void initCharsNumbersSpaces() {
        UserInput mock = mock(UserInput.class);
        when(mock.min()).thenReturn(0);
        when(mock.max()).thenReturn(255);
        when(mock.pattern()).thenReturn(UserInput.CHARS_NUMBERS_SPACES_PATTERN);
        userInputValidator.initialize(mock);
    }

    private void initHtml() {
        UserInput mock = mock(UserInput.class);
        when(mock.min()).thenReturn(0);
        when(mock.max()).thenReturn(Integer.MAX_VALUE);
        when(mock.pattern()).thenReturn(UserInput.HTML_PATTERN);
        userInputValidator.initialize(mock);
    }
}
