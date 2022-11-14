package ru.testwork.incode.filter.impl;

import org.junit.jupiter.api.Test;
import ru.testwork.incode.filter.Filter;

import static org.junit.jupiter.api.Assertions.*;

public class RegexReplaceFilterTest {

    private final static String REGEX_DIGIT = "\\d+";
    private final RegexReplaceFilter filter = new RegexReplaceFilter();

    @Test
    public void testNullSafety() {
        assertNull(filter.apply(null));
    }

    @Test
    public void testNoParametersProvided() {
        assertThrows(Filter.NotEnoughParamsProvidedException.class, () -> filter.apply(""));
    }

    @Test
    public void testNullParametersProvided() {
        assertThrows(Filter.IncorrectParamException.class, () -> filter.apply("", null, null));
    }

    @Test
    public void testNotRegexParameterProvided() {
        assertThrows(Filter.IncorrectParamException.class, () -> filter.apply("", "[]", "X"));
    }

    @Test
    public void testEmptyValue() {
        assertEquals("", filter.apply("", REGEX_DIGIT, "X"));
    }

    @Test
    public void testReplaceDigits() {
        assertEquals("abcXdefX", filter.apply("abc123def567", REGEX_DIGIT, "X"));
    }
}
